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
    private Chunk chunk;

    private String name;

    @OneToMany(mappedBy = "property")
    private final List<Element> elements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chunk getChunk() {
        return chunk;
    }

    /* public */ void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public String getName() {
        return name;
    }

    /* public */ void setName(String name) {
        this.name = name;
    }

    public List<Element> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public boolean addElement(Element element) {
        if (elements.add(element)) {
            element.setProperty(this);
            return true;
        }

        return false;
    }

    public Element getElement(int index) {
        return elements.get(index);
    }

    public void setElement(int index, Element element) {
        Element replaced = elements.set(index, element);

        if (replaced != null) {
            replaced.setOrdinal(-1);
            replaced.setProperty(null);
        }

        if (element != null) {
            element.setOrdinal(index);
        }
    }
}
