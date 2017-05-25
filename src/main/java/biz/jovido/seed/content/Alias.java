package biz.jovido.seed.content;

import biz.jovido.seed.hostname.Domain;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(name = "saila", uniqueConstraints = @UniqueConstraint(columnNames = {"domain_id", "path"}))
public class Alias {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    private String path;

    @OneToOne
    private Fragment fragment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Fragment getFragment() {
        return fragment;
    }

    protected void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
