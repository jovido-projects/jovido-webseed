package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public abstract class Relation {

    public static enum Type {
        ONE_TO_MANY,
        MANY_TO_ONE,
        ONE_TO_ONE,
        MANY_TO_MANY
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private RelationPayload payload;

    @ManyToMany
    @JoinTable(name = "relation_item",
            joinColumns = @JoinColumn(name = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @OrderColumn(name = "ordinal")
    private final List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationPayload getPayload() {
        return payload;
    }

    public void setPayload(RelationPayload payload) {
        this.payload = payload;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
