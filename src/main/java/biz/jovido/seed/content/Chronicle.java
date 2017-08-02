package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bundle_id", "locale"}))
public class Chronicle {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Bundle bundle;

    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @OneToOne(mappedBy = "chronicle")
    private Item current;

    @OneToOne
    private Item active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Item getCurrent() {
        return current;
    }

    public void setCurrent(Item current) {
        this.current = current;
    }

    public Item getActive() {
        return active;
    }

    public void setActive(Item active) {
        this.active = active;
    }
}
