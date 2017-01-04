package biz.jovido.seed.content.service;

import biz.jovido.seed.content.converter.StringToFragmentConverterFactory;
import biz.jovido.seed.content.domain.Fragment;
import biz.jovido.seed.content.repository.FragmentRepository;
import biz.jovido.seed.content.web.FragmentController;
import biz.jovido.seed.content.web.FragmentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class FragmentService {

    private final ApplicationContext applicationContext;
    private final FragmentRepository fragmentRepository;

    @Autowired
    private EntityManager entityManager;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public EntityType<? extends Fragment> getFragmentEntityType(String fragmentEntityName) {
        Metamodel metamodel = entityManager.getMetamodel();
        return (EntityType<? extends Fragment>) metamodel.getEntities().stream()
                .filter(t -> t.getName().equals(fragmentEntityName))
                .findFirst()
                .orElse(null);
    }

    public String getFragmentEntityName(Class<? extends Fragment> fragmentClass) {
        Metamodel metamodel = entityManager.getMetamodel();
        return metamodel.entity(fragmentClass).getName();
    }

    public Fragment createFragment(Class<? extends Fragment> fragmentClass) {
        return BeanUtils.instantiateClass(fragmentClass, Fragment.class);
    }

    public Fragment createFragment(EntityType<? extends Fragment> fragmentEntityType) {
        return createFragment(fragmentEntityType.getJavaType());
    }

    public Fragment createFragmentForEntityName(String fragmentEntityName) {
        EntityType<? extends Fragment> fragmentEntityType = getFragmentEntityType(fragmentEntityName);
        return createFragment(fragmentEntityType);
    }

    public Fragment createFragment(String className) {
        ClassLoader classLoader = applicationContext.getClassLoader();
        @SuppressWarnings("unchecked")
        Class<? extends Fragment> fragmentClass = (Class<? extends Fragment>)
                ClassUtils.resolveClassName(className, classLoader);
        return createFragment(fragmentClass);
    }

    /**
     * Return the {@link Fragment} class mapped to a controller class.
     *
     * @param fragmentControllerClass
     * @throws IllegalArgumentException if no {@link Fragment} class was found for the given controller class
     * @return The {@link Fragment} class mapped to the given controller class
     */
    public <T extends Fragment> Class<T> getFragmentClass(Class<? extends FragmentController<T>> fragmentControllerClass) {
        FragmentType fragmentTypeAnnotation = AnnotationUtils.findAnnotation(fragmentControllerClass, FragmentType.class);
        if (fragmentTypeAnnotation != null) {

            @SuppressWarnings("unchecked")
            Class<T> fragmentClass = (Class<T>) fragmentTypeAnnotation.value();
            if (fragmentClass != null) {
                return fragmentClass;
            }
        }

        throw new IllegalArgumentException("No fragment type specified " +
                "for controller class [" + fragmentControllerClass + "]");
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> Class<? extends FragmentController<T>> getFragmentControllerClass(Class<T> fragmentClass) {
        RequestMappingHandlerMapping handlerMapping = applicationContext
                .getBean(RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//            TODO Clean up:
            RequestMappingInfo mapping = entry.getKey();
            HandlerMethod handler = entry.getValue();

            Class<?> fragmentControllerClass = handler.getBeanType();
            if (FragmentController.class.isAssignableFrom(fragmentControllerClass)) {
                if (fragmentClass == getFragmentClass((Class<? extends FragmentController<Fragment>>) fragmentControllerClass)) {
                    return (Class<? extends FragmentController<T>>) fragmentControllerClass;

                }
            }
        }

        return null;
    }

    public String getFragmentControllerPath(Class<? extends FragmentController<? extends Fragment>> fragmentControllerClass) {
        return MvcUriComponentsBuilder.fromController(fragmentControllerClass).build().getPath();
    }

    public Fragment getFragment(Long id) {
        return fragmentRepository.findOne(id);
    }

    public Fragment saveFragment(Fragment fragment) {
        return fragmentRepository.save(fragment);
    }

    public StringToFragmentConverterFactory getStringToFragmentConverterFactory() {
        return new StringToFragmentConverterFactory(fragmentRepository);
    }

    public FragmentService(ApplicationContext applicationContext, FragmentRepository fragmentRepository) {
        this.applicationContext = applicationContext;
        this.fragmentRepository = fragmentRepository;
    }
}
