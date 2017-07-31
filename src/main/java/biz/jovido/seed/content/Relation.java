package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 *
 * one to one	hasOne	A user has one profile.
 * one to many	hasMany	A user can have multiple recipes.
 * many to one	belongsTo	Many recipes belong to a user.
 * many to many	hasAndBelongsToMany	Recipes have, and belong to, many ingredients.
 *
 */
@Entity
public class Relation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "relation")
    RelationPayload source;

    @ManyToMany
    @JoinTable(name = "relation_target",
            joinColumns = @JoinColumn(name = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private final List<Item> targets = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public RelationPayload getSource() {
        return source;
    }

    public List<Item> getTargets() {
        return targets;
    }
}
