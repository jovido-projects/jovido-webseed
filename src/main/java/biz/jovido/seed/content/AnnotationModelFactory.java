package biz.jovido.seed.content;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;

/**
 * @author Stephan Grundner
 */
public class AnnotationModelFactory implements ModelFactory {

    private final ApplicationContext applicationContext;

    @Override
    public boolean supports(Structure structure) {
        return true;
    }

    @Override
    public void apply(Item item, Model model) {

    }

    public AnnotationModelFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
