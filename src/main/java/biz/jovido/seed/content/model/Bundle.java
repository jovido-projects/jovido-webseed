package biz.jovido.seed.content.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
public final class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    private Locale locale;

    @ManyToOne(optional = false)
    private Type type;

    @OneToOne
    private Item published;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Item draft;

    public Long getId() {
        return id;
    }

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Item getPublished() {
        return published;
    }

    public void setPublished(Item published) {
        this.published = published;
    }

    public Item getDraft() {
        return draft;
    }

    public void setDraft(Item draft) {
        this.draft = draft;
    }
}
