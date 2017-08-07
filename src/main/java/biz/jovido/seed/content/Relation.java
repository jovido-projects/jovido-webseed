package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "relation")
    RelationPayload property;

    @OneToMany
    @JoinTable(name = "relation_target",
            joinColumns = @JoinColumn(name = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "target_id"))
    private final List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public RelationPayload getProperty() {
        return property;
    }

    public List<Item> getItems() {
        return items;
    }
}
