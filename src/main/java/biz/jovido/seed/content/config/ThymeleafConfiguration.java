package biz.jovido.seed.content.config;

import biz.jovido.seed.content.web.NodeFormArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Configuration
public class ThymeleafConfiguration extends WebMvcConfigurerAdapter {
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

    @Autowired
    private NodeFormArgumentResolver formArgumentResolver;
}
