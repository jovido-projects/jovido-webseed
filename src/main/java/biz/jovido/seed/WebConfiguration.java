package biz.jovido.seed;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

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

//    @Autowired
//    private SpringTemplateEngine templateEngine;

    @PostConstruct
    void init() {
//        templateEngine.addDialect(new biz.grundner.spring.web.component.tyhmeleaf.ComponentDialect());
    }

//    @Bean
//    public AliasRequestMapping aliasRequestMapping() {
//        AliasRequestMapping requestMapping = new AliasRequestMapping();
//        requestMapping.setOrder(0);
//
//
//        return requestMapping;
//    }

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
