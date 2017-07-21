package biz.jovido.seed.content;

import biz.jovido.seed.ListUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Property {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    Item item;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @OrderBy("ordinal")
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

    public Item getItem() {
        return item;
    }

    public List<Element> getElements() {
        return elements;
    }

    public Element getOrCreateElement(int index) {
        Element element = ListUtils.getOrNull(elements, index);
        if (element == null) {
            element = new Element();
            element.property = this;
            element.setOrdinal(index);
        }

        return element;
    }
}
