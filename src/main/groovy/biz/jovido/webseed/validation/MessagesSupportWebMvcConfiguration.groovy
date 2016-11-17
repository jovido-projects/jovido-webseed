package biz.jovido.webseed.validation

import biz.jovido.webseed.validation.BeanPropertyBindingMessages
import biz.jovido.webseed.web.content.FragmentFormArgumentResolver
import groovy.transform.InheritConstructors
import org.springframework.beans.ConfigurablePropertyAccessor
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.context.annotation.Configuration
import org.springframework.validation.AbstractPropertyBindingResult
import org.springframework.web.bind.ServletRequestDataBinder
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.annotation.InitBinderDataBinderFactory
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.InvocableHandlerMethod
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory

/**
 *
 * @author Stephan Grundner
 */
@Configuration
class MessagesSupportWebMvcConfiguration extends DelegatingWebMvcConfiguration implements BeanFactoryAware {

    BeanFactory beanFactory

    @InheritConstructors
    static class MyDataBinder extends ExtendedServletRequestDataBinder {

        @Override
        protected AbstractPropertyBindingResult createBeanPropertyBindingResult() {
            def beanPropertyBindingResult = new BeanPropertyBindingMessages(target, objectName,
                    autoGrowNestedPaths, autoGrowCollectionLimit)

            if (conversionService != null) {
                beanPropertyBindingResult.initConversion(conversionService)
            }

            beanPropertyBindingResult
        }

        @Override
        protected ConfigurablePropertyAccessor getPropertyAccessor() {
            super.propertyAccessor
        }
    }

    @InheritConstructors
    static class MyDataBinderFactory extends ServletRequestDataBinderFactory {

        @Override
        protected ServletRequestDataBinder createBinderInstance(Object target, String objectName, NativeWebRequest request) {
            new MyDataBinder(target, objectName)
        }
    }

    static class MyRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

        @Override
        protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> binderMethods) throws Exception {
            new MyDataBinderFactory(binderMethods, webBindingInitializer)
        }
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        def fragmentFormArgumentResolver = beanFactory.getBean(FragmentFormArgumentResolver)
        argumentResolvers.add(fragmentFormArgumentResolver)
    }

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        new MyRequestMappingHandlerAdapter()
    }

//    @Bean
//    @Override
//    RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
//        super.requestMappingHandlerAdapter()
//    }
}
