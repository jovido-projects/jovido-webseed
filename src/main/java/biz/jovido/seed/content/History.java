package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
public class History {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "language_tag")
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @OneToOne
    private Item published;

    @OneToOne(optional = false)
    private Item current;

    @OneToMany(mappedBy = "history", fetch = FetchType.EAGER)
    private final List<Item> all = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Item getPublished() {
        return published;
    }

    public void setPublished(Item published) {
        this.published = published;
    }

    public Item getCurrent() {
        return current;
    }

    public void setCurrent(Item current) {
        this.current = current;
    }

    public List<Item> getAll() {
        return all;
    }
}
