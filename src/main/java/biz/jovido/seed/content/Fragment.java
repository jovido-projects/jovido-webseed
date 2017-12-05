package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class Fragment extends AbstractUnique {

    @ManyToOne(cascade = CascadeType.PERSIST)
    private FragmentHistory history;

    private String structureName;
    private String relativeUrl;

    @OneToMany(mappedBy = "fragment", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "attribute_name")
    private final Map<String, PayloadList<? extends Payload<?>>> payloadListByAttributeName = new HashMap<>();

    @Transient
    private final Set<FragmentChangeListener> changeListeners = new LinkedHashSet<>();

    public FragmentHistory getHistory() {
        return history;
    }

    public void setHistory(FragmentHistory history) {
        this.history = history;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public Set<String> getAttributeNames() {
        return Collections.unmodifiableSet(payloadListByAttributeName.keySet());
    }

    public PayloadList<? extends Payload<?>> getPayloadList(String attributeName) {
        return payloadListByAttributeName.get(attributeName);
    }

    public PayloadList<? extends Payload<?>> setPayloadList(String attributeName, PayloadList<? extends Payload<?>> payloadList) {
        if (payloadList != null) {
            payloadList.setFragment(this);
            payloadList.setAttributeName(attributeName);
        }

        PayloadList<? extends Payload<?>> replacedPayloadList = payloadListByAttributeName.put(attributeName, payloadList);

        if (replacedPayloadList != null) {
            replacedPayloadList.setFragment(null);
            replacedPayloadList.setAttributeName(null);
        }

        return replacedPayloadList;
    }

    public boolean addChangeListener(FragmentChangeListener changeListener) {
        return changeListeners.add(changeListener);
    }

    public boolean removeChangeListener(FragmentChangeListener changeListener) {
        return changeListeners.remove(changeListener);
    }

    protected void notifyFragmentChanged(FragmentChange change) {
        for (FragmentChangeListener changeListener : changeListeners) {
            changeListener.fragmentChanged(change);
        }
    }
}
