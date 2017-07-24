package biz.jovido.seed.content;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"payload_id", "target_id"}))
public class OneToManyRelation extends ToManyRelation {

}
