package biz.jovido.webseed.config

import biz.jovido.webseed.web.content.FragmentFormArgumentResolver
import groovy.transform.CompileStatic
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.TemplateEngine
import org.thymeleaf.messageresolver.IMessageResolver
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ITemplateResolver

import javax.annotation.PostConstruct

/**
 *
 * @author Stephan Grundner
 */
//@EnableSpringHttpSession
@CompileStatic
@Configuration
class ThymeleafDefaultConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }

    @Override
    void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        def fragmentFormArgumentResolver = applicationContext.getBean(FragmentFormArgumentResolver)
        argumentResolvers.add(fragmentFormArgumentResolver)
    }

    @Bean
    IMessageResolver messageResolver(MessageSource messageSource) {
        def messageResolver = new SpringMessageResolver()
        messageResolver.messageSource = messageSource

        messageResolver
    }

    @Bean
    TemplateEngine templateEngine(IMessageResolver messageResolver) {
        def engine = new SpringTemplateEngine()
        engine.setEnableSpringELCompiler(true)
        engine.setTemplateResolver(templateResolver())
        engine.addTemplateResolver(templateResolver2())

        engine.addMessageResolver(messageResolver)

        engine
    }

    @Bean
    ViewResolver viewResolver(TemplateEngine templateEngine) {
        def resolver = new ThymeleafViewResolver()
        resolver.setTemplateEngine(templateEngine)
        resolver.setCharacterEncoding("UTF-8")

        resolver
    }

    private ITemplateResolver templateResolver() {
        def resolver = new SpringResourceTemplateResolver()
        resolver.setApplicationContext(applicationContext)
        resolver.setPrefix("classpath:/templates/")
        resolver.setSuffix(".html")
        resolver.setTemplateMode(TemplateMode.HTML)

        resolver
    }

    private ITemplateResolver templateResolver2() {
        def resolver = new SpringResourceTemplateResolver()
        resolver.setApplicationContext(applicationContext)
        resolver.setPrefix("resources/templates/")
        resolver.setSuffix(".html")
        resolver.setTemplateMode(TemplateMode.HTML)

        resolver
    }

    @Autowired
    SpringTemplateEngine templateEngine

    @PostConstruct
    void config() {
//        templateEngine.addDialect(new ComponentDialect())
    }
}
