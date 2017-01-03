package biz.jovido.seed.content.config;

import biz.jovido.seed.content.service.FragmentService;
import biz.jovido.seed.content.web.FragmentFormArgumentResolver;
import biz.jovido.seed.DefaultFieldTemplateResolver;
import biz.jovido.seed.fields.thymeleaf.FieldsDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
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
    private SpringTemplateEngine templateEngine;

    @Autowired
    private FragmentService fragmentService;

//    @Autowired
//    private ListableBeanFactory beanFactory;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        assert formArgumentResolver != null;
//        argumentResolvers.add(formArgumentResolver);

        Assert.notNull(fragmentService);
        argumentResolvers.add(new FragmentFormArgumentResolver(fragmentService));
    }

    @PostConstruct
    void registerAdditionalDialects() {
        FieldsDialect fieldsDialect = new FieldsDialect();
        fieldsDialect.setFieldTemplateResolver(new DefaultFieldTemplateResolver());
        templateEngine.addDialect(fieldsDialect);
    }
}
