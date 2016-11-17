package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.*
import biz.jovido.webseed.model.content.constraint.AlphanumericConstraint
import biz.jovido.webseed.model.content.payload.LocalizablePayload
import biz.jovido.webseed.model.content.payload.TextPayload
import biz.jovido.webseed.repository.content.FragmentHistoryRepository
import biz.jovido.webseed.repository.content.FragmentRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 * @author Stephan Grundner
 */
@Service
class FragmentService {

    private static final Logger LOG = LoggerFactory.getLogger(FragmentService)

    protected final FragmentHistoryRepository fragmentHistoryRepository
    protected final FragmentRepository fragmentRepository
    protected final FragmentIdGenerator fragmentIdGenerator

    @Autowired
    IdentifierService identifierService

    @Autowired
    StructureService structureService

    FragmentService(FragmentHistoryRepository fragmentHistoryRepository,
                    FragmentRepository fragmentRepository,
                    FragmentIdGenerator fragmentIdGenerator) {
        this.fragmentHistoryRepository = fragmentHistoryRepository
        this.fragmentRepository = fragmentRepository
        this.fragmentIdGenerator = fragmentIdGenerator
    }

    Fragment createFragment(FragmentType type, FragmentHistory history) {
        assert type != null

        if (history == null) {
            history = new FragmentHistory()
//            history = fragmentHistoryRepository.save(history)
        }

        assert history != null

        def fragment = new Fragment()
        fragment.type = type
        fragment.history = history

        if (history.revisions.empty) {
            history.current = fragment
        }

        fragment
    }

    Fragment createFragment(String fragmentTypeName) {
        def fragmentType = structureService.getFragmentType(fragmentTypeName)
        createFragment(fragmentType, null)
    }

    Fragment createFragment(FragmentType fragmentType) {
        createFragment(fragmentType, null)
    }

    Fragment createFragment(Long fragmentTypeId) {
        def fragmentType = structureService.getFragmentType(fragmentTypeId)
        createFragment(fragmentType, null)
    }

    Fragment getFragment(String id, String revisionId) {
        def fragmentKey = new Fragment.FragmentKey()
        fragmentKey.id = id
        fragmentKey.revisionId = revisionId

        fragmentRepository.findOne(fragmentKey)
    }

    @Transactional
    Fragment saveFragment(Fragment fragment) {
        if (fragment.id == null) {
            fragment.id = identifierService.getNextIdValue('fragment')
        }
        fragmentRepository.save(fragment)
    }

    Attribute getAttribute(Fragment fragment, Field field) {
        def attribute = fragment.getAttribute(field)
        if (attribute == null) {
            attribute = new Attribute()
            attribute.fragment = fragment
            attribute.field = field
            fragment.putAttribute(attribute)
        }

        attribute
    }

    Attribute getAttribute(Fragment fragment, String fieldName) {
        def type = fragment.type
        def field = type.getField(fieldName)
        if (field == null) {
            throw new RuntimeException("Unexpected field [$fieldName]")
        }

        getAttribute(fragment, field)
    }

    Object getValue(Payload<?> payload, Locale locale) {
        if (payload instanceof LocalizablePayload) {
            return payload.getValue(locale)
        } else {
            return payload.value
        }
    }

    Object getValue(Payload<?> payload) {
        getValue(payload, Locale.default)
    }

    Object getValue(Attribute attribute, int index, Locale locale) {
        def payload = attribute.getPayload(index)
        getValue(payload, locale)
    }

    Object getValue(Attribute attribute, int index) {
        getValue(attribute, index, Locale.default)
    }

    Object getValue(Attribute attribute, Locale locale) {
        getValue(attribute, 0, locale)
    }

    Object getValue(Attribute attribute) {
        getValue(attribute, Locale.default)
    }

    Object getValue(Fragment fragment, String fieldName, int index, Locale locale) {
        def attribute = getAttribute(fragment, fieldName)
        getValue(attribute, index, locale)
    }

    Object getValue(Fragment fragment, String fieldName, int index) {
        getValue(fragment, fieldName, index, Locale.default)
    }

    Object getValue(Fragment fragment, String fieldName, Locale locale) {
        getValue(fragment, fieldName, 0, locale)
    }

    Object getValue(Fragment fragment, String fieldName) {
        getValue(fragment, fieldName, Locale.default)
    }

    protected Payload<?> createPayload(Constraint constraint) {
        Payload<?> payload

        switch (constraint.class) {
            case AlphanumericConstraint:
                payload = new TextPayload()
                break
            default:
                throw new IllegalArgumentException()
        }

        payload
    }

    @Deprecated
    protected Payload<?> getPayload(Attribute attribute, int index) {
        def payload = attribute.getPayload(index)
        if (payload == null) {
            def field = attribute.field
            def constraint = field.constraint
            payload = createPayload(constraint)
            attribute.setPayload(index, payload)
        }

        payload
    }

    void setValue(Attribute attribute, int index, Locale locale, Object value) {
        def payload = getPayload(attribute, index) as Payload<Object>
        if (payload instanceof LocalizablePayload) {
            payload.setValue(locale, value)
        } else {
            payload.value = value
        }
    }

    void setValue(Attribute attribute, int index, Object value) {
        setValue(attribute, index, Locale.default, value)
    }

    void setValue(Attribute attribute, Locale locale, Object value) {
        setValue(attribute, 0, locale, value)
    }

    void setValue(Attribute attribute, Object value) {
        setValue(attribute, Locale.default, value)
    }

    void setValue(Fragment fragment, String fieldName, int index, Locale locale, Object value) {
        def attribute = getAttribute(fragment, fieldName)
        setValue(attribute, index, locale, value)
    }

    void setValue(Fragment fragment, String fieldName, int index, Object value) {
        setValue(fragment, fieldName, index, Locale.default, value)
    }

    void setValue(Fragment fragment, String fieldName, Locale locale, Object value) {
        setValue(fragment, fieldName, 0, locale, value)
    }

    void setValue(Fragment fragment, String fieldName, Object value) {
        setValue(fragment, fieldName, Locale.default, value)
    }
}
