package biz.jovido.webseed.service

import biz.jovido.webseed.model.*
import biz.jovido.webseed.model.constraint.AlphanumericConstraint
import biz.jovido.webseed.model.value.LocalizablePayload
import biz.jovido.webseed.model.value.Payload
import biz.jovido.webseed.model.value.TextPayload
import biz.jovido.webseed.repository.FragmentHistoryRepository
import biz.jovido.webseed.repository.FragmentRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

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

    FragmentService(FragmentHistoryRepository fragmentHistoryRepository,
                    FragmentRepository fragmentRepository,
                    FragmentIdGenerator fragmentIdGenerator) {
        this.fragmentHistoryRepository = fragmentHistoryRepository
        this.fragmentRepository = fragmentRepository
        this.fragmentIdGenerator = fragmentIdGenerator
    }

    Fragment createFragment(FragmentType type, FragmentHistory history) {
        if (history == null) {
            history = new FragmentHistory()
//            bundle.structureBundle = structure.bundle

            history = fragmentHistoryRepository.save(history)

            assert history != null
        }

        def fragment = new Fragment()
        fragment.id = fragmentIdGenerator.nextId
        fragment.type = type
        fragment.history = history

        if (history.revisions.empty) {
            history.current = fragment
        }

        fragment
    }

    Fragment createFragment(FragmentType structure) {
        createFragment(structure, null)
    }

//    Data createFragment(StructureBundle structureBundle) {
//        createFragment(structureBundle.current)
//    }

    Fragment saveFragment(Fragment fragment) {
        fragmentRepository.save(fragment)
    }

    Attribute getAttribute(Fragment fragment, String fieldName) {
        def type = fragment.type
        def field = type.getField(fieldName)
        if (field == null) {
            throw new RuntimeException("Unexpected field [$fieldName]")
        }
        def attribute = fragment.getAttribute(field)
        if (attribute == null) {
            attribute = new Attribute()
            attribute.fragment = fragment
            attribute.field = field
            fragment.putAttribute(attribute)
        }

        attribute
    }

    Object getValue(Attribute attribute, int index, Locale locale) {
        def payload = attribute.getPayload(index)
        if (payload instanceof LocalizablePayload) {
            return payload.getValue(locale)
        } else {
            return payload.value
        }
    }

    Object getValue(Attribute attribute, Locale locale) {
        getValue(attribute, 0, locale)
    }

    Object getValue(Fragment fragment, String fieldName, int index, Locale locale) {
        def attribute = getAttribute(fragment, fieldName)
        getValue(attribute, index, locale)
    }

    Object getValue(Fragment fragment, String fieldName, Locale locale) {
        getValue(fragment, fieldName, 0, locale)
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

    void setValue(Attribute attribute, Locale locale, Object value) {
        setValue(attribute, 0, locale, value)
    }

    void setValue(Fragment fragment, String fieldName, int index, Locale locale, Object value) {
        def attribute = getAttribute(fragment, fieldName)
        setValue(attribute, index, locale, value)
    }

    void setValue(Fragment fragment, String fieldName, Locale locale, Object value) {
        setValue(fragment, fieldName, 0, locale, value)
    }
}
