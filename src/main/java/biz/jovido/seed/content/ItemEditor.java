package biz.jovido.seed.content;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    private final BeanWrapper wrapper = new BeanWrapperImpl(this);

    private Item item;

    private UUID parentNodeUuid;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UUID getParentNodeUuid() {
        return parentNodeUuid;
    }

    public void setParentNodeUuid(UUID parentNodeUuid) {
        this.parentNodeUuid = parentNodeUuid;
    }

    public Object getPropertyValue(String propertyPath) {
        return wrapper.getPropertyValue(propertyPath);
    }
}
