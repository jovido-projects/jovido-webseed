package biz.jovido.seed.service.content

import biz.jovido.seed.model.content.Node
import biz.jovido.seed.model.content.node.Field
import biz.jovido.seed.model.content.node.Fragment
import biz.jovido.seed.model.content.node.Type
import biz.jovido.seed.model.content.node.fragment.Payload
import biz.jovido.seed.repository.content.NodeRepository
import biz.jovido.seed.repository.content.node.NodeTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

/**
 *
 * @author Stephan Grundner
 */
@Service
class NodeService {

    @Autowired
    NodeTypeRepository nodeTypeRepository

    @Autowired
    NodeRepository nodeRepository

    Type getType(String name) {
        nodeTypeRepository.findByName(name)
    }

    Type createType(String name) {
        assert !StringUtils.isEmpty(name)

        def type = new Type()
        type.name = name

        type
    }

    Field addField(String fieldName, Type type) {
        assert !StringUtils.isEmpty(fieldName)
        assert type != null

        def field = new Field()
        field.name = fieldName
        field.type = type
        field.ordinal = type.fields.size()

        type.addField(field)

        field
    }

    void removeAttributes(Type type, Set<String> fieldNames) {
//        throw new UnsupportedOperationException()
    }

    Type saveType(Type type) {
        def existingType = nodeTypeRepository.findByName(type?.name)
        if (existingType != null) {

            def newFieldNames = new HashSet()
            def remainingFieldNames = new HashSet(existingType.fieldNames)

            for (def fieldName : type.fieldNames) {
                if (!remainingFieldNames.remove(fieldName)) {
                    newFieldNames.add(fieldName)
                }
            }

            removeAttributes(existingType, remainingFieldNames)

            return nodeTypeRepository.saveAndFlush(existingType)
        }

        nodeTypeRepository.save(type)
    }

    Node createNode(Type type) {
        assert type != null

        def fragment = new Node()
        fragment.type = type

        fragment
    }

    Node createNode(String typeName) {
        def type = getType(typeName)

        createNode(type)
    }

    Node getNode(Long id) {
        nodeRepository.findOne(id)
    }

    List<Node> getAllNodes(Type type) {
        nodeRepository.findAllByType(type)
    }

    Node saveNode(Node node) {
        nodeRepository.save(node)
    }

    Fragment createFragment(Node node, Locale locale) {
        def fragment = node.getFragment(locale)
        if (fragment != null) {
            throw new FragmentAlreadyExistsException()
        }

        fragment = new Fragment()
        fragment.node = node
        fragment.locale = locale

        fragment
    }

    protected Field getField(String fieldName, Fragment fragment) {
        def node = fragment?.node
        def type = node?.type

        type?.getField(fieldName)
    }

    Payload getPayload(Field field, int index, Fragment fragment) {
        def property = fragment.getProperty(field)
        property.getPayload(index)
    }

    void setPayload(Field field, int index, Fragment fragment, Payload payload) {
        def property = fragment.getProperty(field)
        property.setPayload(index, payload)
    }

    final Payload getPayload(String fieldName, int index, Fragment fragment) {
        def field = getField(fieldName, fragment)

        getPayload(field, index, fragment)
    }

    final void setPayload(String fieldName, int index, Fragment fragment, Payload payload) {
        def field = getField(fieldName, fragment)
        setPayload(field, index, fragment, payload)
    }

    Object getValue(Field field, int index, Fragment fragment) {
        def payload = getPayload(field, index, fragment)

        payload?.value
    }

    def <T> void setValue(Field field, int index, Fragment fragment, Class<? extends Payload<T>> payloadClazz, T value) {
        def payload = payloadClazz.newInstance()
        payload.value = value
        setPayload(field, index, fragment, payload)
    }

    Object getValue(String fieldName, int index, Fragment fragment) {
        def field = getField(fieldName, fragment)
        def payload = getPayload(field, index, fragment)

        payload?.value
    }

    def <T> void setValue(String fieldName, int index, Fragment fragment, Class<? extends Payload<T>> payloadClazz, T value) {
        def field = getField(fieldName, fragment)
        setValue(field, index, fragment, payloadClazz, value)
    }
}
