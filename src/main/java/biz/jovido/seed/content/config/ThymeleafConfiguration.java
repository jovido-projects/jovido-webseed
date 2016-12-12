package biz.jovido.seed.content.config;

import biz.jovido.seed.content.web.NodeFormArgumentResolver;
import biz.jovido.spring.web.ui.component.tyhmeleaf.ComponentDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Configuration
public class ThymeleafConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private NodeFormArgumentResolver formArgumentResolver;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        assert formArgumentResolver != null;
        argumentResolvers.add(formArgumentResolver);
    }

    public NodeFormArgumentResolver getFormArgumentResolver() {
        return formArgumentResolver;
    }

    public void setFormArgumentResolver(NodeFormArgumentResolver formArgumentResolver) {
        this.formArgumentResolver = formArgumentResolver;
    }

    @PostConstruct
    void registerAdditionalDialects() {
        templateEngine.addDialect(new ComponentDialect());
    }
}
