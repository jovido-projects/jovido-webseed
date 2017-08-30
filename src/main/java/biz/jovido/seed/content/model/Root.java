package biz.jovido.seed.content.model;

import biz.jovido.seed.conversion.LocaleConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
public class Root {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Hierarchy hierarchy;

    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @OneToMany(mappedBy = "root",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @OrderBy("ordinal")
    private final List<Node> nodes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    /*default*/ void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Locale getLocale() {
        return locale;
    }

    /*default*/ void setLocale(Locale locale) {
        this.locale = locale;
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
}
