package biz.jovido.seed;

import biz.jovido.seed.content.Bundle;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"host_id", "path"}))
public class Alias {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Bundle bundle;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private Host host;

    @Column(updatable = false)
    private String path;

    public Long getId() {
        return id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
