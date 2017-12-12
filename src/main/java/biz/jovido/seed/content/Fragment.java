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
    private String url;

    @OneToMany(mappedBy = "fragment", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "attribute_name")
    private final Map<String, PayloadSequence> sequenceByAttributeName = new HashMap<>();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getAttributeNames() {
        return Collections.unmodifiableSet(sequenceByAttributeName.keySet());
    }

    public PayloadSequence getSequence(String attributeName) {
        return sequenceByAttributeName.get(attributeName);
    }

    public PayloadSequence setSequence(String attributeName, PayloadSequence sequence) {
        if (sequence != null) {
            sequence.setFragment(this);
            sequence.setAttributeName(attributeName);
        }

        PayloadSequence replaced = sequenceByAttributeName.put(attributeName, sequence);

        if (replaced != null) {
            replaced.setFragment(null);
            replaced.setAttributeName(null);
        }

        return replaced;
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
