package biz.jovido.seed.content;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
public class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    private String structureName;

    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL)
    @MapKey(name = "locale")
    private final Map<Locale, Item> items = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Map<Locale, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public Item putItem(Item item) {
        Assert.notNull(item);
        Item replaced = items.put(item.getLocale(), item);

        if (replaced != null) {
            replaced.setBundle(null);
        }

        item.setBundle(this);

        return replaced;
    }
}
