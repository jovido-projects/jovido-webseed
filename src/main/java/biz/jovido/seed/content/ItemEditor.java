package biz.jovido.seed.content;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    public interface SequenceTemplateProvider {

        String getTemplate(Sequence sequence);
    }

    public static class PayloadState {

        private boolean collapsed = true;

        public boolean isCollapsed() {
            return collapsed;
        }

        public void setCollapsed(boolean collapsed) {
            this.collapsed = collapsed;
        }
    }

    private final BeanWrapper wrapper = new BeanWrapperImpl(this);

    private Item item;
//    private UUID parentNodeUuid;
    private SequenceTemplateProvider sequenceTemplateProvider;

    private final Map<UUID, PayloadState> stateByUuid = new HashMap<>();

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//    public UUID getParentNodeUuid() {
//        return parentNodeUuid;
//    }
//
//    public void setParentNodeUuid(UUID parentNodeUuid) {
//        this.parentNodeUuid = parentNodeUuid;
//    }

    public SequenceTemplateProvider getSequenceTemplateProvider() {
        return sequenceTemplateProvider;
    }

    public void setSequenceTemplateProvider(SequenceTemplateProvider sequenceTemplateProvider) {
        this.sequenceTemplateProvider = sequenceTemplateProvider;
    }

    public Object getPropertyValue(String propertyPath) {
        return wrapper.getPropertyValue(propertyPath);
    }

    private PayloadState getState(UUID uuid) {
        PayloadState state = stateByUuid.get(uuid);
        if (state == null) {
            state = new PayloadState();
            stateByUuid.put(uuid, state);
        }

        return state;
    }

    public boolean isCollapsed(Payload payload) {
        if (payload != null) {
            UUID uuid = payload.getUuid();
            PayloadState state = getState(uuid);
            return state.isCollapsed();
        }

        return false;
    }

    public void setCollapsed(Payload payload, boolean collapsed) {
        UUID uuid = payload.getUuid();
        PayloadState state = getState(uuid);
        state.setCollapsed(collapsed);
    }
}
