package biz.jovido.seed.content;

import biz.jovido.seed.content.field.AssetField;
import biz.jovido.seed.content.field.DateField;
import biz.jovido.seed.content.field.FragmentField;
import biz.jovido.seed.content.field.TextField;
import biz.jovido.seed.content.payload.AssetPayload;
import biz.jovido.seed.content.payload.DatePayload;
import biz.jovido.seed.content.payload.FragmentPayload;
import biz.jovido.seed.content.payload.TextPayload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Service
public class FragmentService {

    @Autowired
    private StructureService structureService;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private FragmentRepository fragmentRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    Bundle createBundle(String structureName) {
        Assert.notNull(structureName, "[structureName] must not be null");

        Bundle bundle = new Bundle();
        bundle.setStructureName(structureName);
//        bundle.setStructureProvider(structureService);

        return bundle;
    }

    Bundle saveBundle(Bundle bundle) {
        return bundleRepository.saveAndFlush(bundle);
    }

    public void applyDefaults(Fragment fragment) {
        Bundle bundle = fragment.getBundle();

        String structureName = bundle.getStructureName();
        Structure structure = structureService.getStructure(structureName);
        structure.getFieldNames().forEach( name -> {
            Attribute attribute = fragment.getAttribute(name);
            if (attribute == null) {
                attribute = new Attribute();
                attribute.setFragment(fragment);
                attribute.setName(name);
                fragment.setAttribute(name, attribute);
            }

            Field field = structure.getField(name);
            List<Payload<?>> payloads = attribute.getPayloads();
            int requiredNumberOfValues = field.getMinimumNumberOfValues() - payloads.size();
            if (requiredNumberOfValues > 0) {
                for (int i = 0; i < requiredNumberOfValues; i++) {
                    Payload<?> payload = createPayload(fragment, name);
                    payload.setAttribute(attribute);
                    payloads.add(payload);
                }
            }
        });
    }

    public Class<? extends Payload<?>> getPayloadClass(Class<? extends Field> fieldClass) {
        if (fieldClass.isAssignableFrom(TextField.class)) {
            return TextPayload.class;
        } else if (fieldClass.isAssignableFrom(FragmentField.class)) {
            return FragmentPayload.class;
        } else if (fieldClass.isAssignableFrom(DateField.class)) {
            return DatePayload.class;
        } else if (fieldClass.isAssignableFrom(AssetField.class)) {
            return AssetPayload.class;
        }

        return null;
    }


    public Class<? extends Payload<?>> getPayloadClass(Field field) {
        return getPayloadClass(field.getClass());
    }

    public Payload<?> createPayload(Fragment fragment, String fieldName) {
        Bundle bundle = fragment.getBundle();
        String structureName = bundle.getStructureName();
        Structure structure = structureService.getStructure(structureName);
        Field field = structure.getField(fieldName);
        Class<? extends Payload<?>> payloadClass = getPayloadClass(field);
        return BeanUtils.instantiate(payloadClass);
    }

    Fragment createFragment(Bundle bundle, Locale locale) {
        Fragment fragment = new Fragment();
        fragment.setBundle(bundle);
        fragment.setLocale(locale);

        applyDefaults(fragment);

        return fragment;
    }

    Fragment createFragment(String structureName, Locale locale) {
        Bundle bundle = createBundle(structureName);
        return createFragment(bundle, locale);
    }

    public  Fragment getFragment(Long id) {
        Fragment fragment = fragmentRepository.findOne(id);
        if (fragment != null) {
            applyDefaults(fragment);
        }

        return fragment;
    }

    @Transactional
    public List<Fragment> findbyText(String text) {
        return fragmentRepository.findAll();
    }

    public Payload<?> getPayload(Long id) {
        return fragmentRepository.findPayloadById(id);
    }

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Fragment save(Fragment fragment) {
        Assert.notNull(fragment, "[fragment] must not be null");

        Bundle bundle = fragment.getBundle();
        if (bundle.getId() == null) {
            bundle = saveBundle(bundle);
            fragment.setBundle(bundle);
        }

        auditingHandler.markModified(fragment);

//        Fragment saved = fragmentRepository.saveAndFlush(fragment);
        Fragment saved = entityManager.merge(fragment);
        Assert.notNull(saved);

        return saved;
    }

    public Structure getStructure(Fragment fragment) {
        Bundle bundle = fragment.getBundle();
        if (bundle != null) {
            String structureName = bundle.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    @Transactional
    public Attribute getLabelAttribute(Fragment fragment) {
        Structure structure = getStructure(fragment);
        if (structure != null) {
            String label = structure.getLabel();
            return fragment.getAttribute(label);
        }

        return null;
    }

    public List<Locale> getAllSupportedLocales() {
        return Arrays.asList(Locale.ROOT, Locale.GERMANY, Locale.ITALY);
    }
}
