package biz.jovido.seed.content;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    public interface PayloadGroupTemplateProvider {

        String getTemplate(PayloadGroup payloadGroup);
    }

    public interface PayloadTemplateProvider {

        String getTemplate(Payload payload);
    }

    private final BeanWrapper wrapper = new BeanWrapperImpl(this);

    private Item item;

    private PayloadGroupTemplateProvider payloadGroupTemplateProvider;
    private PayloadTemplateProvider payloadTemplateProvider;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public PayloadGroupTemplateProvider getPayloadGroupTemplateProvider() {
        return payloadGroupTemplateProvider;
    }

    public void setPayloadGroupTemplateProvider(PayloadGroupTemplateProvider payloadGroupTemplateProvider) {
        this.payloadGroupTemplateProvider = payloadGroupTemplateProvider;
    }

    public String getPayloadGroupTemplate(PayloadGroup payloadGroup) {
        return getPayloadGroupTemplateProvider().getTemplate(payloadGroup);
    }

    public PayloadTemplateProvider getPayloadTemplateProvider() {
        return payloadTemplateProvider;
    }

    public void setPayloadTemplateProvider(PayloadTemplateProvider payloadTemplateProvider) {
        this.payloadTemplateProvider = payloadTemplateProvider;
    }

    public String getPayloadTemplate(Payload payload) {
        return getPayloadTemplateProvider().getTemplate(payload);
    }

    public Object getPropertyValue(String propertyPath) {
        return wrapper.getPropertyValue(propertyPath);
    }
}
