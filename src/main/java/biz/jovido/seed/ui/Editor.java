package biz.jovido.seed.ui;

import biz.jovido.seed.MessageSourceProvider;
import org.springframework.context.MessageSource;

/**
 * @author Stephan Grundner
 */
public class Editor implements MessageSourceProvider {

    private MessageSource messageSource;
    private Breadcrumbs breadcrumbs;

    @Override
    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Breadcrumbs getBreadcrumbs() {
        if (breadcrumbs == null) {
            breadcrumbs = new Breadcrumbs();
        }

        return breadcrumbs;
    }

    public void setBreadcrumbs(Breadcrumbs breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
