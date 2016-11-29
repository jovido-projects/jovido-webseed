package biz.jovido.seed.config

import biz.jovido.seed.web.content.NodeFormArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 *
 * @author Stephan Grundner
 */
@Configuration
class ThymeleafConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    NodeFormArgumentResolver formArgumentResolver

    @Override
    void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        assert formArgumentResolver != null
        argumentResolvers.add(formArgumentResolver)
    }
}
