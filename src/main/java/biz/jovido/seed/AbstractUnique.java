package biz.jovido.seed;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@MappedSuperclass
public class AbstractUnique implements Unique {

    @Access(AccessType.PROPERTY)
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    /*public*/ void setId(Long id) {
        this.id = id;
    }
}
