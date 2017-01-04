package biz.jovido.seed.content.domain;

import biz.jovido.seed.content.converter.LocaleToLanguageTagConverter;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Fragment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne//(optional = false)
    private Node node;

    @Convert(converter = LocaleToLanguageTagConverter.class)
    @Column(nullable = false)
    private Locale locale = Locale.ROOT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
