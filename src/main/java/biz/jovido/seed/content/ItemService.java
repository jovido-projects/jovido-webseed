package biz.jovido.seed.content;

import biz.jovido.seed.UsedInTemplates;
import biz.jovido.seed.net.HostService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StructureService structureService;

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private HostService hostService;

    @Autowired
    private HtmlService htmlService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelFactoryProvider modelFactoryProvider;

    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }

    public Item findPublished(Long historyId) {
        return itemRepository.findPublished(historyId);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAllCurrent();
    }

    public List<Item> findAllPublishedItemsByPath(String path) {
        return itemRepository.findAllPublishedByPath(path);
    }

    public Structure getStructure(Item item) {
        if (item != null) {
            String structureName = item.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    @Deprecated
    public String getLabelText(Item item) {
        Structure structure = getStructure(item);
        if (structure != null) {
            String attributeName = structure.getLabelAttributeName();
            if (StringUtils.hasText(attributeName)) {
                TextPayload label = (TextPayload) getPayload(item, attributeName, 0);
                if (label != null) {
                    return label.getText();
                }
            }

            return null;
        }

        return null;
    }

    private void applyPayloads(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
            if (payloadGroup == null) {
                payloadGroup = new PayloadGroup();
                item.setPayloadGroup(attributeName, payloadGroup);
            }

            List<Payload> payloads = payloadGroup.getPayloads();
            if (!(attribute instanceof ItemAttribute)) {
                int remaining = attribute.getRequired() - payloads.size();
                while (remaining-- > 0) {
                    Payload payload = attribute.createPayload();
                    payloadGroup.addPayload(payload);
                }
            }
        }
    }

    public void refrehItem(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
            if (payloadGroup == null) {
                payloadGroup = new PayloadGroup();
                item.setPayloadGroup(attributeName, payloadGroup);
            }

            List<Payload> payloads = payloadGroup.getPayloads();
            Attribute attribute = structure.getAttribute(attributeName);
            if (!(attribute instanceof ItemAttribute)) {
                int remaining = attribute.getRequired() - payloads.size();
                while (remaining-- > 0) {
                    Payload payload = attribute.createPayload();
                    payloadGroup.addPayload(payload);
                }
            }

            for (Payload payload : payloads) {
                if (payload instanceof ItemPayload) {
                    Item nestedItem = ((ItemPayload) payload).getItem();
                    if (nestedItem != null) {
                        refrehItem(nestedItem);
                    }
                }
            }
        }
    }

    private Item createItem(Structure structure) {
        ItemHistory history = new ItemHistory();
        return createItem(structure, history);
    }

    public Item createItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure);
    }

    private Item createItem(Structure structure, ItemHistory history) {
        Item item = new Item();
        item.setHistory(history);
        item.setStructureName(structure.getName());
        item.setLocale(LocaleContextHolder.getLocale());
        if (history != null) {
            history.setCurrent(item);
        }

        applyPayloads(item);

        return item;
    }

    public Item createEmbeddedItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure, null);
    }

    public boolean isPublished(Item item) {
        if (item != null) {
            ItemHistory history = item.getHistory();
            if (history != null) {
                return new EqualsBuilder()
                        .append(history.getPublished(), item)
                        .isEquals();
            }
        }

        return false;
    }

    public Mode getMode(Item item) {
        return isPublished(item) ? Mode.LIVE : Mode.PREVIEW;
    }

    @UsedInTemplates
    @Deprecated
    public String getPath(Object object) {
        Item item = (Item) object;
        if (item != null) {
            String path = item.getPath();
            if (StringUtils.isEmpty(path)) {
                ItemHistory history = item.getHistory();
                return history != null
                        ? String.format("/item?history=%s", history.getId())
                        : null;
            }

            if (!path.startsWith("/")) {
                return String.format("/%s", path);
            }

            return path;
        }

        return null;
    }

    public String getUrl(Item item) {
        return getPath(item);
    }

    @Transactional
    public Item saveItem(Item item) {
        if (item.getId() == null) {
            auditingHandler.markCreated(item);
        }
        auditingHandler.markModified(item);
        return entityManager.merge(item);
    }

    @Transactional
    public Item publishItem(final Item item) {
        Item current = item.copy();
        current = entityManager.merge(current);
        ItemHistory history = current.getHistory();
        history.setPublished(item);
        history.setCurrent(current);
        entityManager.merge(history);

        return current;
    }

    public List<Payload> getPayloads(Item item, String attributeName) {
        if (item != null) {
            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
            if (payloadGroup != null) {
                return payloadGroup.getPayloads();
            }
        }

        return null;
    }

    public Payload getPayload(Item item, String attributeName, int index) {
        List<Payload> payloads = getPayloads(item, attributeName);
        if (payloads != null && payloads.size() > index) {
            return payloads.get(index);
        }

        return null;
    }

    public Payload getPayload(Item item, String attributeName) {
        return getPayload(item, attributeName, 0);
    }

    public Item getNestedItem(Item item, String attributeName, int index) {
        ItemPayload payload = (ItemPayload) getPayload(item, attributeName, index);
        if (payload != null) {
            return payload.getItem();
        }

        return null;
    }

    public List<Item> getNestedItems(Item item, String attributeName) {
        PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
        if (payloadGroup != null) {
            return payloadGroup.getPayloads().stream()
                    .map(ItemPayload.class::cast)
                    .map(ItemPayload::getItem)
                    .collect(Collectors.toList());
        }

        return null;
    }

    public OriginalImage getImage(Item item, String attributeName, int index) {
        ImagePayload payload = (ImagePayload) getPayload(item, attributeName, index);
        if (payload != null) {
            return payload.getImage();
        }

        return null;
    }

    public Attribute getAttribute(Item item, String attributeName) {
        Structure structure = getStructure(item);
        return structure.getAttribute(attributeName);
    }

    public Attribute getAttribute(Payload payload) {
        PayloadGroup payloadGroup = payload.getGroup();
        Item item = payloadGroup.getItem();
        String attributeName = payloadGroup.getAttributeName();
        return getAttribute(item, attributeName);
    }

    public String getDescription(Attribute attribute, Object[] messageArgs, Locale locale) {
        Structure structure = attribute.getStructure();
        String messageCode = String.format("seed.structure.%s.%s.description",
                structure.getName(),
                attribute.getName());
        try {
            return messageSource.getMessage(messageCode, messageArgs, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    public String getDescription(Payload payload, Object... messageArgs) {
        Assert.notNull(payload, "[payload] must not be null");
        Attribute attribute = getAttribute(payload);
        PayloadGroup payloadGroup = payload.getGroup();
        Item item = payloadGroup.getItem();

        return getDescription(attribute, messageArgs, item.getLocale());
    }

    public ItemVisitResult walkPayload(Payload payload, ItemVisitor visitor) {
        ItemVisitResult result = visitor.visitPayload(payload);
        if (result == ItemVisitResult.CONTINUE) {
            Attribute attribute = getAttribute(payload);
            if (attribute instanceof ItemAttribute) {
                Item item = ((ItemPayload) payload).getItem();
                result = walkItem(item, visitor);
            }
        }

        return result;
    }

    public ItemVisitResult walkItem(Item item, ItemVisitor visitor) {
        ItemVisitResult result = visitor.visitItem(item);
        if (result == ItemVisitResult.CONTINUE) {
            for (String attributeName : item.getAttributeNames()) {
                PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
                for (Payload payload : payloadGroup.getPayloads()) {
                    result = walkPayload(payload, visitor);
                    if (result != ItemVisitResult.CONTINUE) {
                        return result;
                    }
                }
            }
        }

        return ItemVisitResult.CONTINUE;
    }

    public Payload findPayload(Item item, UUID payloadUuid) {
        final AtomicReference<Payload> found = new AtomicReference<>();
        walkItem(item, new SimpleItemVisitor() {
            @Override
            public ItemVisitResult visitPayload(Payload payload) {
                if (payload.getUuid().equals(payloadUuid)) {
                    found.set(payload);
                    return ItemVisitResult.TERMINATE;
                }

                return ItemVisitResult.CONTINUE;
            }
        });

        return found.get();
    }

    public Item findItem(Item item, UUID itemUuid) {
        final AtomicReference<Item> found = new AtomicReference<>();
        walkItem(item, new SimpleItemVisitor() {
            @Override
            public ItemVisitResult visitItem(Item item) {
                if (item.getUuid().equals(itemUuid)) {
                    found.set(item);
                    return ItemVisitResult.TERMINATE;
                }

                return ItemVisitResult.CONTINUE;
            }
        });

        return found.get();
    }

    public void itemToModel(Item item, Model model) {
        Structure structure = getStructure(item);
        ModelFactory modelFactory = modelFactoryProvider.getModelFactory(structure);
        modelFactory.apply(item, model);
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public List<Locale> getAllSupportedLocales() {
        return Stream.of("de", "en", "it", "es")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }
}
