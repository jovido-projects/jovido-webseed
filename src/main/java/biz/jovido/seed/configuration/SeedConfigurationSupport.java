package biz.jovido.seed.configuration;

import biz.jovido.seed.content.DeprecatedModelFactory;
import biz.jovido.seed.content.ItemRequestMapping;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.ModelFactoryProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@ComponentScan("biz.jovido.seed")
@EntityScan("biz.jovido.seed")
@EnableJpaRepositories("biz.jovido.seed")
@EnableJpaAuditing
@EnableSpringHttpSession
@EnableWebSecurity
@EnableConfigurationProperties
public class SeedConfigurationSupport implements ApplicationContextAware, BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

//    @Bean
//    public Object xyz(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
//        ConfigurableWebBindingInitializer webBindingInitializer = (ConfigurableWebBindingInitializer)
//                requestMappingHandlerAdapter.getWebBindingInitializer();
//
//        webBindingInitializer.setAutoGrowNestedPaths(false);
//
//        return "OK";
//    }

    @Bean
    public ItemRequestMapping itemRequestMapping() {
        ItemRequestMapping requestMapping = new ItemRequestMapping();
        requestMapping.setOrder(0);
        return requestMapping;
    }

    @Bean
    public ModelFactoryProvider modelFactoryProvider(ItemService itemService) {
        ModelFactoryProvider modelFactoryProvider = new ModelFactoryProvider();
        modelFactoryProvider.setDefaultModelFactory(new DeprecatedModelFactory(itemService));

        return modelFactoryProvider;
    }

    @Bean
    @ConfigurationProperties(prefix = "seed.ui")
    public UIProperties uiProperties() {
        return new UIProperties();
    }

    @Bean
    public SessionRepository<?> sessionRepository() {
        return new MapSessionRepository();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        LocaleResolver localeResolver = beanFactory.getBean(LocaleResolver.class);
        if (localeResolver instanceof AbstractLocaleResolver) {
//            TODO Make this configurable:
            ((AbstractLocaleResolver) localeResolver).setDefaultLocale(Locale.ENGLISH);
        }
    }
}
