package biz.jovido.seed.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(name = "authority")
public class Role implements GrantedAuthority {

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

    @Override
    public String getAuthority() {
        return name;
    }
}
