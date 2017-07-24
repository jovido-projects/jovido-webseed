package biz.jovido.seed.content;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Stephan Grundner
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"target_id"}))
public class OneToOneRelation extends ToOneRelation {

}
