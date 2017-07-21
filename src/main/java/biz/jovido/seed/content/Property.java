package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Property {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    private String name;

    @OneToMany(mappedBy = "property")
    private final List<Element> elements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Element> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public boolean addElement(Element element) {
        if (elements.add(element)) {
//            element.setProperty(this);
            return true;
        }

        return false;
    }

}
