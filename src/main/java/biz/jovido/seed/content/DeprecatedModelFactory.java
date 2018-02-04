package biz.jovido.seed.content;

import biz.jovido.seed.content.frontend.ItemValues;
import biz.jovido.seed.content.frontend.ValueMap;
import biz.jovido.seed.content.frontend.ValuesList;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Stephan Grundner
 */
@Deprecated
public class DeprecatedModelFactory implements ModelFactory {

    private final ItemService itemService;

    @Override
    public boolean supports(Structure structure) {
        return structure != null;
    }

    private ValuesList toList(ItemValues values, String attributeName) {
        Item item = values.getItem();
        ValuesList list = new ValuesList(attributeName);
        for (Payload payload : itemService.getPayloads(item, attributeName)) {
            Attribute attribute = itemService.getAttribute(payload);
            if (attribute instanceof ItemAttribute) {
                Item relatedItem = ((ItemPayload) payload).getItem();
                list.add(toValues(relatedItem, values));
            } else {

                ValueMap map = new ValueMap();

                if (attribute instanceof ImageAttribute) {
                    ImagePayload imagePayload = (ImagePayload) payload;
                    Image image = imagePayload.getImage();
                    map.put("fileName", Optional.ofNullable(image).map(Image::getFileName));
                    map.put("alt", Optional.ofNullable(image).map(Image::getAlt));
                    map.put("id", Optional.ofNullable(image).map(Image::getId));
                    String url = image != null ? String.format("/asset/files/%s/%s",
                            image.getUuid(),
                            image.getFileName()) : null;
                    map.put("url", url);
                } else if (attribute instanceof IconAttribute) {
                    IconPayload iconPayload = ((IconPayload) payload);
                    map.put("code", iconPayload.getCode());
                } else if (attribute instanceof LinkAttribute) {
                    LinkPayload linkPayload = ((LinkPayload) payload);
                    map.put("url", linkPayload.getUrl());
                    String value = linkPayload.getText();
                    map.put("value", value);
                } else if (attribute instanceof TextAttribute) {
                    TextPayload textPayload = ((TextPayload) payload);
                    String value = textPayload.getText();
                    value = value.replace("<p></p>", "");

                    map.put("value", value);
                } else if (attribute instanceof YesNoAttribute) {
                    YesNoPayload yesNo = ((YesNoPayload) payload);
                    map.put("value", yesNo.isYes());
                    map.put("yes", yesNo.isYes());
                    map.put("no", !yesNo.isYes());
                } else if (attribute instanceof SelectionAttribute) {
                    SelectionPayload selection = ((SelectionPayload) payload);
                    SelectionAttribute selectionAttribute = (SelectionAttribute) attribute;
                    List<String> selectionValues = selection.getValues();
                    for (String option : selectionAttribute.getOptions()) {
                        boolean selected = selectionValues.contains(option);
                        map.put(option, selected);
                    }
//                    map.put("selection", selection.getValues());
                    map.put("value", selectionValues.get(0));
                } else {
//                    throw new UnsupportedOperationException();
                }

                list.add(map);
            }
        }
        return list;
    }

    private ItemValues toValues(Item item, ItemValues parent) {
        ItemValues values = new ItemValues(item, parent);
        for (String attributeName : item.getAttributeNames()) {
            values.put(attributeName, toList(values, attributeName));
        }

        return values;
    }

    private ItemValues toValues(Item item) {
        return toValues(item, null);
    }

    public Map<String, Object> toMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        ItemValues values = toValues(item);
        map.putAll(values);
        map.put("self", values);
        map.put("label", itemService.getLabelText(item));
        map.put("url", itemService.getUrl(item));
        map.put("date", item.getCreatedAt());

        return map;
    }

    @Override
    public void apply(Item item, Model model) {
        model.addAllAttributes(toMap(item));
    }

    public DeprecatedModelFactory(ItemService itemService) {
        this.itemService = itemService;
    }
}
