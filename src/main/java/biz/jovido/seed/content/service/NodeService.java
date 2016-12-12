package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.NodeBundle;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.Structure;
import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.model.node.structure.Constraints;
import biz.jovido.seed.content.model.node.structure.Field;
import biz.jovido.seed.content.repository.NodeRepository;
import biz.jovido.seed.content.repository.node.PropertyRepository;
import biz.jovido.seed.content.repository.node.StructureRepository;
import biz.jovido.seed.util.CollectionUtils;
import groovy.transform.CompileStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Service
@CompileStatic
public class NodeService {

    private static final Logger LOG = LoggerFactory.getLogger(NodeService.class);

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ConversionService conversionService;


    public Structure getStructure(String name) {
        return structureRepository.findByName(name);
    }

    public Field addField(String fieldName, Structure structure) {
        assert !StringUtils.isEmpty(fieldName);
        assert structure != null;

        return structure.addField(fieldName);
    }

    @Transactional
    public void removeExistingFields(final Structure structure, Set<String> fieldNames) {
        LOG.info("Removing fields " + String.valueOf(fieldNames) + " from structure [" + structure.getName() + "]");

        fieldNames.forEach(fieldName -> {
            Field field = structure.removeField(fieldName);
            List<Property> properties = propertyRepository.findAllByField(field);
//            int n = nodeRepository.deletePropertiesByField(field);
            int n = properties.size();
            properties.stream().forEach( property -> {

//                TODO Test removing properties!
                property.setField(null);
                propertyRepository.delete(property);
            });


            LOG.info("Removed [" + String.valueOf(n) + "] properties for field [" + fieldName + "]");
        });
    }

    public void addNewFields(final Structure structure, final Structure existingType, Set<String> fieldNames) {
        LOG.info("Adding new fields " + String.valueOf(fieldNames) + " to structure [" + structure.getName() + "]");

        fieldNames.forEach(fieldName -> {
            Field field = structure.getField(fieldName);
            Field newlyAddedField = existingType.addField(fieldName);
            newlyAddedField.setConstraints(field.getConstraints());
        });
    }

    public Structure saveStructure(Structure structure) {
        Structure existingType = structureRepository.findByName(structure.getName());
        if (existingType != null) {

            HashSet<String> newFieldNames = new HashSet<>();
            HashSet<String> remainingFieldNames = new HashSet<>(existingType.getFieldNames());

            structure.getFieldNames().forEach(fieldName -> {
                if (!remainingFieldNames.remove(fieldName)) {
                    newFieldNames.add(fieldName);
                }
            });

            removeExistingFields(existingType, remainingFieldNames);
            addNewFields(structure, existingType, newFieldNames);

            return structureRepository.saveAndFlush(existingType);
        }

        return structureRepository.save(structure);
    }

    public Node createNode(Structure structure) {
        assert structure != null;

        Node fragment = new Node();
        fragment.setStructure(structure);

        return fragment;
    }

    public Node createNode(String structureName) {
        Structure structure = getStructure(structureName);

        return createNode(structure);
    }

    public boolean isPersistent(Node node) {
        if (node == null) {
            return false;
        }

        return node.getId() != null;
    }

    public boolean isTransient(Node node) {
        return !isPersistent(node);
    }

    public Node getNode(Long id) {
//        return nodeRepository.findOne(id);
        return nodeRepository.findById(id);
    }

    public Page<Node> getAllNodes(Pageable pageable) {
        return nodeRepository.findAllNodes(pageable);
    }

    public Page<Node> getAllNodes(Structure structure, Pageable pageable) {
        return nodeRepository.findAllByStructure(structure, pageable);
    }

    @Transactional
    public Node saveNode(Node node) {

        Long nodeId = node.getId();
        if (nodeId != null) {
            Node persistentNode = nodeRepository.getOne(nodeId);

            copyNode(node, persistentNode);
            return nodeRepository.save(persistentNode);
        }

        if (node.getBundle() == null) {
            NodeBundle bundle = new NodeBundle();
            entityManager.persist(bundle);
            node.setBundle(bundle);
        }

        Node saved = nodeRepository.save(node);

        Assert.notNull(saved);

        NodeBundle bundle = saved.getBundle();
        bundle.setCurrent(saved);

        entityManager.persist(bundle);

        return saved;
    }

    public Fragment createFragment(Node node, Locale locale) {
        Assert.notNull(node, "[node] must not be null!");
        Assert.notNull(locale, "[locale] must not be null!");

        final Fragment fragment = new Fragment();
        fragment.setNode(node);
        fragment.setLocale(locale);

        node.setFragment(locale, fragment);

        Structure structure = node.getStructure();
        Assert.notNull(structure, "[node.structure] must not be null!");

        structure.getFields().forEach(field -> {

            Property property = fragment.addProperty(field);
            Assert.notNull(property);
        });

        return fragment;
    }

    public Fragment getDefaultFragment(Node node) {
        Fragment fragment = node.getFragment(Locale.ROOT);
        if (fragment == null) {
            fragment = node.getFragments()
                    .stream()
                    .findFirst()
                    .orElse(null);
        }

        return fragment;
    }

    public void setValue(Property property, int index, Object value) {
        @SuppressWarnings("unchecked")
        List<Object> values = property.getValues();
        if (index >= values.size()) {
            CollectionUtils.resize(values, index + 1);
        }

        Class<?> sourceType = null;
        if (value != null) {
            sourceType = value.getClass();
        }

        Class targetType = property.getValueClazz();

        if (conversionService.canConvert(sourceType, targetType)) {
            value = conversionService.convert(value, targetType);
        }

        values.set(index, value);
    }

    public <T> T getValue(Property<T> property, int index) {
        return property.getValues().get(index);
    }

    public <T> void setValue(Fragment fragment, Field field, int index, T value) {
        @SuppressWarnings("unchecked")
        Property<T> property = (Property<T>) fragment.getProperty(field);
        setValue(property, index, value);
    }

    public <T> T getValue(Fragment fragment, Field field, int index) {
        @SuppressWarnings("unchecked")
        Property<T> property = (Property<T>) fragment.getProperty(field);
        return getValue(property, index);
    }

    public void setValue(Fragment fragment, String fieldName, int index, Object value) {
        @SuppressWarnings("unchecked")
        Property property = (Property)fragment.getProperty(fieldName);
        setValue(property, index, value);
    }

    public <T> T getValue(Fragment fragment, String fieldName, int index) {
        @SuppressWarnings("unchecked")
        Property<T> property = (Property<T>) fragment.getProperty(fieldName);
        return getValue(property, index);
    }

    public <T> void setValue(Node node, Locale locale, Field field, int index, T value) {
        Fragment fragment = node.getFragment(locale);
        setValue(fragment, field, index, value);
    }

    public <T> T getValue(Node node, Locale locale, Field field, int index) {
        Fragment fragment = node.getFragment(locale);
        return getValue(fragment, field, index);
    }

    public <T> void setValue(Node node, Locale locale, String fieldName, int index, T value) {
        Fragment fragment = node.getFragment(locale);
        setValue(fragment, fieldName, index, value);
    }

    public <T> T getValue(Node node, Locale locale, String fieldName, int index) {
        Fragment fragment = node.getFragment(locale);
        return getValue(fragment, fieldName, index);
    }

    public Object getLabel(Fragment fragment, int index) {
        Property labelProperty = fragment.getLabelProperty();
        return getValue(labelProperty, index);
    }

    public Object getLabel(Node node, Locale locale, int index) {
        Fragment fragment = node.getFragment(locale);
        Property labelProperty = fragment.getLabelProperty();
        return getValue(labelProperty, index);
    }

    public Object getLabel(Node node, int index) {
        Fragment fragment = getDefaultFragment(node);
        Property labelProperty = fragment.getLabelProperty();
        return getValue(labelProperty, index);
    }

    public void applyDefaults(Fragment fragment) {
        fragment.getProperties().forEach(property -> {
            Field field = property.getField();
            Constraints constraints = field.getConstraints();
            int size = constraints.getMinimumNumberOfValues();
            CollectionUtils.resize(property.getValues(), size);
        });
    }

    <T> void copyProperty(Property<T> sourceProperty, Property<T> targetProperty) {
        List<T> values = sourceProperty.getValues();
        for (int i = 0; i < values.size(); i++) {
            T value = getValue(sourceProperty, i);
            setValue(targetProperty, i, value);
        }
    }

    void copyFragment(Fragment sourceFragment, Fragment targetFragment) {
        sourceFragment.getPropertyFields().forEach( field -> {
            Property sourceProperty = sourceFragment.getProperty(field);
            Property targetProperty = targetFragment.getProperty(field);
            if (targetProperty == null) {
                targetProperty = targetFragment.addProperty(field);
            }
            copyProperty(sourceProperty, targetProperty);
        });
    }

    void copyNode(Node sourceNode, Node targetNode) {
        sourceNode.getLocales().forEach(locale -> {
            Fragment sourceFragment = sourceNode.getFragment(locale);
            Fragment targetFragment = targetNode.getFragment(locale);
            if (targetFragment == null) {
                targetFragment = createFragment(targetNode, locale);
            }
            copyFragment(sourceFragment, targetFragment);
        });
    }


    @PostConstruct
    private void init() {

//        Object x = nodeRepository.findBlaBla("Bla");

        return;
    }
}
