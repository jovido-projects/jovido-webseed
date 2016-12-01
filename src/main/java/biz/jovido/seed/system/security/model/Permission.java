package biz.jovido.seed.system.security.model;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "permission")
@Entity
public class Permission {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
