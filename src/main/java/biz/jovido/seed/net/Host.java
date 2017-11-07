package biz.jovido.seed.net;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class Host {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
