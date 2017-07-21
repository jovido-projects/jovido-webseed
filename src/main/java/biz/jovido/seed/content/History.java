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
    private Item current;

    @OneToOne
    private Item draft;

    @OneToMany(mappedBy = "history")
    @OrderBy("created DESC")
    private final List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getCurrent() {
        return current;
    }

    public void setCurrent(Item current) {
        this.current = current;
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
