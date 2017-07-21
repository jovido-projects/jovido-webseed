package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"locale", "element_id"}))
public abstract class Payload {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @ManyToOne(optional = false)
    private Element element;

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

    public Element getElement() {
        return element;
    }

    /* public */ void setElement(Element element) {
        this.element = element;
    }

    public abstract Object getValue();
    public abstract void setValue(Object value);
}
