package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"fragment_id"})})
public class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type_name", updatable = false)
    private String typeName;

    @Convert(converter = LocaleConverter.class)
    @Column(updatable = false)
    private Locale locale;

//    @OneToOne
//    @JoinColumn(name = "published_fragment_id")
//    private Fragment published;

    @OneToOne
    @JoinColumn(name = "fragment_id")
    private Fragment fragment;

//    private Map<Locale, Fragment> fragments;

    public Long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
