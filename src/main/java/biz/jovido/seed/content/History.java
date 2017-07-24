package biz.jovido.seed.content;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    @OneToOne
    private Item draft;
}
