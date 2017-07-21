package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class History {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Item active;

    @OneToOne(optional = false)
    private Item draft;

    @OneToMany(mappedBy = "history")
    private final List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Item getActive() {
        return active;
    }

    public void setActive(Item active) {
        this.active = active;
    }

    public Item getDraft() {
        return draft;
    }

    public void setDraft(Item draft) {
        this.draft = draft;
    }

    public List<Item> getItems() {
        return items;
    }
}
