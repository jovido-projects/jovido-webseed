package biz.jovido.seed.content.model;

import biz.jovido.seed.security.model.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
public final class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Bundle parent;

    private int ordinal;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("ordinal")
    private final List<Bundle> children = new ArrayList<>();

    @OneToMany(mappedBy = "bundle")
    @MapKey(name = "locale")
    private final Map<Locale, Chronicle> chronicles = new HashMap<>();

    @CreatedBy
    @ManyToOne(optional = false)
    private User createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bundle getParent() {
        return parent;
    }

    /*default*/ void setParent(Bundle parent) {
        this.parent = parent;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public List<Bundle> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public boolean addChild(Bundle child) {
        if (children.add(child)) {
            child.setParent(this);
            return true;
        }

        return false;
    }

    public Map<Locale, Chronicle> getChronicles() {
        return Collections.unmodifiableMap(chronicles);
    }

    public Chronicle getChronicle(Locale locale) {
        return chronicles.get(locale);
    }

    public void putChronicle(Chronicle chronicle) {
        Locale locale = chronicle.getLocale();
        Chronicle replaced = chronicles.put(locale, chronicle);
        if (replaced != null) {
            replaced.bundle = null;
        }

        chronicle.bundle = this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
