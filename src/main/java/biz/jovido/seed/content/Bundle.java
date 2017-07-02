package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
public class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Structure structure;

    @Column(name = "language_tag")
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Item draft;

    @OneToOne
    private Item published;

    public Long getId() {
        return id;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Item getDraft() {
        return draft;
    }

    public void setDraft(Item draft) {
        this.draft = draft;
    }

    public Item getPublished() {
        return published;
    }

    public void setPublished(Item published) {
        this.published = published;
    }
}
