package biz.jovido.seed.content;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@MappedSuperclass
public abstract class ToManyRelation extends Relation {

    @OneToMany
    private List<Item> targets = new ArrayList<>();
}
