package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Field;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.Type;
import biz.jovido.seed.content.model.node.field.Constraint;
import biz.jovido.seed.content.model.node.field.constraint.ReferenceConstraint;
import biz.jovido.seed.content.model.node.fragment.Payload;
import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.model.node.fragment.payload.NodePayload;
import biz.jovido.seed.content.model.node.fragment.payload.TextPayload;
import biz.jovido.seed.content.repository.NodeRepository;
import biz.jovido.seed.content.repository.node.NodeTypeRepository;
import groovy.transform.CompileStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
    private NodeTypeRepository nodeTypeRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private EntityManager entityManager;


    public Type getType(String name) {
        return nodeTypeRepository.findByName(name);
    }

    public Type createType(String name) {
        assert !StringUtils.isEmpty(name);

        Type type = new Type();
        type.setName(name);

        return type;
    }

    public Field addField(String fieldName, Type type) {
        assert !StringUtils.isEmpty(fieldName);
        assert type != null;

        return type.addField(fieldName);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void removeExistingFields(final Type type, Set<String> fieldNames) {
        LOG.info("Removing fields " + String.valueOf(fieldNames) + " from type [" + type.getName() + "]");

        fieldNames.forEach(fieldName -> {
            Field field = type.removeField(fieldName);
            int n = nodeRepository.deletePropertiesByField(field);

            LOG.info("Removed [" + String.valueOf(n) + "] properties for field [" + fieldName + "]");
        });
    }

    public void addNewFields(final Type type, final Type existingType, Set<String> fieldNames) {
        LOG.info("Adding new fields " + String.valueOf(fieldNames) + " to type [" + type.getName() + "]");

        fieldNames.forEach(fieldName -> {
            Field field = type.getField(fieldName);
            Field newlyAddedField = existingType.addField(fieldName);
            newlyAddedField.setConstraint(field.getConstraint());
        });
    }

    public Type saveType(Type type) {
        Type existingType = nodeTypeRepository.findByName(type.getName());
        if (existingType != null) {

            HashSet<String> newFieldNames = new HashSet<>();
            HashSet<String> remainingFieldNames = new HashSet<>(existingType.getFieldNames());

            type.getFieldNames().forEach(fieldName -> {
                if (!remainingFieldNames.remove(fieldName)) {
                    newFieldNames.add(fieldName);
                }
            });

            removeExistingFields(existingType, remainingFieldNames);
            addNewFields(type, existingType, newFieldNames);

            return nodeTypeRepository.saveAndFlush(existingType);
        }

        return nodeTypeRepository.save(type);
    }

    public Node createNode(Type type) {
        assert type != null;

        Node fragment = new Node();
        fragment.setType(type);

        return fragment;
    }

    public Node createNode(String typeName) {
        Type type = getType(typeName);

        return createNode(type);
    }

    public Node getNode(Long id) {
//        return nodeRepository.findOne(id);
        return nodeRepository.findById(id);
    }

    public List<Node> getAllNodes(Type type) {
        return nodeRepository.findAllByType(type);
    }

    public Node saveNode(Node node) {

        Long nodeId = node.getId();
        if (nodeId != null) {
            Node persistentNode = nodeRepository.getOne(nodeId);

            copyNode(node, persistentNode);
            return nodeRepository.save(persistentNode);
        }

        return nodeRepository.save(node);
    }

    public Fragment createFragment(Node node, Locale locale) {
        final Fragment fragment = new Fragment();
        fragment.setNode(node);
        fragment.setLocale(locale);

        node.setFragment(locale, fragment);

        Type type = node.getType();
        type.getFields().forEach(field -> {
            Property property = new Property();
            property.setField(field);
            property.setFragment(fragment);

            Property previous = fragment.addProperty(field, property);
            Assert.isNull(previous);
        });

        return fragment;
    }

    public void applyDefaults(Fragment fragment) {
        fragment.getProperties().forEach(property -> {
            Field field = property.getField();
            Constraint constraint = field.getConstraint();
            int min = constraint.getMinimumNumberOfPayloads();
            property.setSize(min);
        });
    }

    protected Field getField(String fieldName, Fragment fragment) {
        Node node = fragment.getNode();
        Type type = node.getType();

        return type.getField(fieldName);
    }

    public Payload getPayload(Field field, int index, Fragment fragment) {
        Property property = fragment.getProperty(field);
        return property.getPayload(index);
    }

    public void setPayload(Field field, int index, Fragment fragment, Payload payload) {
        Property property = fragment.getProperty(field);
        property.setPayload(index, payload);
    }

    public final Payload getPayload(String fieldName, int index, Fragment fragment) {
        Field field = getField(fieldName, fragment);

        return getPayload(field, index, fragment);
    }

    public final void setPayload(String fieldName, int index, Fragment fragment, Payload payload) {
        Field field = getField(fieldName, fragment);
        setPayload(field, index, fragment, payload);
    }

    public Object getValue(Field field, int index, Fragment fragment) {
        Payload payload = getPayload(field, index, fragment);

        return (payload == null ? null : payload.getValue());
    }

    public <T> void setValue(Field field, int index, Fragment fragment, Class<Payload<T>> payloadClazz, T value) {
        Payload<T> payload = BeanUtils.instantiateClass(payloadClazz);
        payload.setValue(value);
        setPayload(field, index, fragment, payload);
    }

    public Object getValue(String fieldName, int index, Fragment fragment) {
        Field field = getField(fieldName, fragment);
        Payload payload = getPayload(field, index, fragment);
        return payload.getValue();
    }

    public <T> void setValue(String fieldName, int index, Fragment fragment, Class<Payload<T>> payloadClazz, T value) {
        Field field = getField(fieldName, fragment);
        setValue(field, index, fragment, payloadClazz, value);
    }

    protected Class<? extends Payload<?>> getDefaultPayloadClazz(Field field) {
//        TODO Complete liste of types!!!
        Constraint constraint = field.getConstraint();
        if (constraint instanceof ReferenceConstraint) {
            return NodePayload.class;
        } else {
            return TextPayload.class;
        }
    }

    public <T> void setValue(String fieldName, int index, Fragment fragment, T value) {
        Field field = getField(fieldName, fragment);
        @SuppressWarnings("unchecked")
        Class<Payload<T>> payloadClazz = (Class<Payload<T>>)getDefaultPayloadClazz(field);
        setValue(field, index, fragment, payloadClazz, value);
    }

    @SuppressWarnings("unchecked")
    void copyProperty(Property sourceProperty, Property targetProperty) {
        for (int i = 0; i < sourceProperty.size(); i++) {
            Payload sourcePayload = sourceProperty.getPayload(i);
            Payload targetPayload = targetProperty.getPayload(i);
            if (targetPayload == null) {
                targetPayload = BeanUtils.instantiateClass(sourcePayload.getClass());
                targetProperty.setPayload(i, targetPayload);
            }
            targetPayload.setValue(sourcePayload.getValue());
        }
        targetProperty.setSize(sourceProperty.size());
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
        sourceNode.getFragmentLocales().forEach(locale -> {
            Fragment sourceFragment = sourceNode.getFragment(locale);
            Fragment targetFragment = targetNode.getFragment(locale);
            if (targetFragment == null) {
                targetFragment = createFragment(targetNode, locale);
            }
            copyFragment(sourceFragment, targetFragment);
        });
    }
}
