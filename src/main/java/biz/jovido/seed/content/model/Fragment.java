package biz.jovido.seed.content.model;

import biz.jovido.seed.content.converter.LocaleToLanguageTagConverter;
import biz.jovido.seed.content.metamodel.FragmentType;
import biz.jovido.seed.content.util.FragmentUtils;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Fragment {

    FragmentType getType() {
        return FragmentUtils.fragmentType(this.getClass());
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
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
