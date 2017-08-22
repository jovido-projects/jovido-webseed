package biz.jovido.seed.content.model;

import biz.jovido.seed.security.model.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
public final class Bundle {

    @Id
    @GeneratedValue
    private Long id;

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
