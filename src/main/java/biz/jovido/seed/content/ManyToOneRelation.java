package biz.jovido.seed.content;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Stephan Grundner
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"payload_id", "target_id"}))
public class ManyToOneRelation extends ToOneRelation {

}
