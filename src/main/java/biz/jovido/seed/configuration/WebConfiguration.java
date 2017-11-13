package biz.jovido.seed.configuration;

import biz.jovido.seed.TemplateResolver;
import biz.jovido.seed.component.ClassNameTemplateResolver;
import biz.jovido.seed.thymeleaf.ComponentDialect;
import biz.jovido.seed.thymeleaf.ContentDialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Configuration
@Deprecated
public class WebConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("admin/login");
        registry.addRedirectViewController("/admin", "/admin/");
        registry.addRedirectViewController("/admin/items", "/admin/items/");
        registry.addRedirectViewController("/admin/item", "/admin/item/");
        registry.addRedirectViewController("/admin/hosts", "/admin/hosts/");
        registry.addRedirectViewController("/admin/host", "/admin/host/");
        registry.addRedirectViewController("/admin/users", "/admin/users/");
        registry.addRedirectViewController("/admin/user", "/admin/user/");
    }

    @Bean
    public ClassNameTemplateResolver classNameTemplateResolver() {
        return new ClassNameTemplateResolver();
    }

    @Bean
    public ContentDialect contentDialect() {
        ContentDialect dialect = new ContentDialect();

        return dialect;
    }

    @Bean
    public ComponentDialect componentDialect(Set<TemplateResolver> templateResolvers) {
        ComponentDialect dialect = new ComponentDialect();
        dialect.setTemplateResolvers(templateResolvers);

        return dialect;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleParamDetector localeParamDetector = applicationContext.getBean(LocaleParamDetector.class);
//        registry.addInterceptor(localeParamDetector);

//        OrganizationDetector organizationDetector = applicationContext.getBean(OrganizationDetector.class);
//        registry.addInterceptor(organizationDetector);
//        registry.addInterceptor(new HttpSessionAliasPublisher());
    }

//    @Bean
//    public EmbeddedServletContainerCustomizer customizer() {
//        return container -> {
//            if (container instanceof TomcatEmbeddedServletContainerFactory) {
//                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
//                tomcat.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
//            }
//        };
//    }
//
//    @Bean
//    TemplateEngine templateEngine(SpringTemplateEngine templateEngine) {
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
}
