package biz.jovido.seed.content;

import biz.jovido.seed.ListUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "name"}))
public class Property {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    private String name;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @OrderBy("index")
    private final List<Element> elements = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    /* public */ void setItem(Item item) {
        this.item = item;
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

    public Element getOrCreateElementAt(int index) {
        Element element = ListUtils.getOrNull(elements, index);
        if (element == null) {
            element = new Element();
            element.setProperty(this);
            element.setIndex(index);
            elements.add(index, element);
        }

//        while(--index > 0) {
//            getOrCreateElementAt(index);
//        }

        return element;
    }

    public Element appendElement() {
        int index = elements.size();
        return getOrCreateElementAt(index);
    }

    public Payload getPayload(int index, Locale locale) {
        Element element = getOrCreateElementAt(index);
        return element.getPayload(locale);
    }

    public void setPayload(int index, Payload payload) {
        Element element = getOrCreateElementAt(index);
        element.setPayload(payload.getLocale(), payload);
    }

    public Object getValue(int index, Locale locale) {
        Element element = getOrCreateElementAt(index);
        return element.getValue(locale);
    }

    public void setValue(int index, Locale locale, Object value) {
        Element element = getOrCreateElementAt(index);
        element.setValue(locale, value);
    }
}
