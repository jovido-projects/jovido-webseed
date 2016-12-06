package biz.jovido.seed.content.model.node.fragment;

import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.structure.Field;

import javax.persistence.*;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Table(name = "property", uniqueConstraints = @UniqueConstraint(columnNames = {"field_id", "fragment_id"}))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
public abstract class Property<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "fragment_id")
    private Fragment fragment;

//    TODO Keep in sync with #getValues().size()
//    private int numberOfValues;

    @Deprecated
    public abstract List<T> getValues();

    @Transient
    private final Class<T> valueClazz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Transient
    public String getName() {
        return field.getName();
    }

    public Class<T> getValueClazz() {
        return valueClazz;
    }

    protected Property(Class<T> valueClazz) {
        this.valueClazz = valueClazz;
    }
}
