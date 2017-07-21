package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Chunk current;

    @OneToOne
    private Chunk draft;

    @OneToMany(mappedBy = "item")
    private final List<Chunk> history = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chunk getCurrent() {
        return current;
    }

    public void setCurrent(Chunk current) {
        this.current = current;
    }

    public Chunk getDraft() {
        return draft;
    }

    public void setDraft(Chunk draft) {
        this.draft = draft;
    }

    public List<Chunk> getHistory() {
        return history;
    }
}
