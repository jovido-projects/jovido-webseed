package biz.jovido.seed.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Stephan Grundner
 */
@Entity
public class Link {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255 * 16)
    private String target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
