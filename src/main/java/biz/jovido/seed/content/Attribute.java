package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Attribute {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Fragment fragment;

    private String name;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordinal")
    private final List<Payload<?>> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payload<?>> getPayloads() {
        return payloads;
    }

//    public Field getField() {
//        if (fragment != null) {
//            Structure structure = fragment.getStructure();
//            if (structure != null) {
//                return structure.getField(name);
//            }
//        }
//
//        return null;
//    }
}
