package biz.jovido.seed.net;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Stephan Grundner
 */
@Configuration
public class VisitorConfiguration extends WebMvcConfigurerAdapter implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        VisitorService visitorService = beanFactory.getBean(VisitorService.class);
        HostService hostService = beanFactory.getBean(HostService.class);
        registry.addInterceptor(new VisitorRequestInterceptor(visitorService, hostService));
    }
}
