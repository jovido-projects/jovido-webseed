package biz.jovido.seed.content;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "locale"}))
public class Translation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Item item;
    private Locale locale;

//    @OneToMany(mappedBy = "translation", cascade = CascadeType.ALL)
//    @MapKey(name = "element")
//    private Map<Element, Payload> payloads = new HashMap<>();
    @OneToMany(mappedBy = "translation", cascade = CascadeType.ALL)
    @MapKey(name = "element")
    private Map<Element, Payload> payloads = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

//    public Map<Element, Payload> getPayloads() {
//        return payloads;
//    }

    public Payload getPayload(String propertyName, int index) {
        Property property = item.getProperty(propertyName);
        Element element = property.getOrCreateElement(index);
        return payloads.get(element);
    }

    public Payload putPayload(String propertyName, int index, Payload payload) {
        Property property = item.getProperty(propertyName);
        Element element = property.getOrCreateElement(index);
        payload.element = element;
        payload.translation = this;
        return payloads.put(element, payload);
    }

//    public Payload getPayload(String propertyName, int index) {
//        Item item = getItem();
//        Property property = item.getProperty(propertyName);
//        Element element = (Element) ListUtils.lazyList(property.getElements(), new Factory() {
//            @Override
//            public Object create() {
//                Element x = new Element();
//                return x;
//            }
//        }).get(index);
//        return payloads.get(element);
//    }
}
