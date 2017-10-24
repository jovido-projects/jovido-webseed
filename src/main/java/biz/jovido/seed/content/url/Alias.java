package biz.jovido.seed.content.url;

import biz.jovido.seed.content.ItemHistory;

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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "host_id")
    private Host host;

    private String path;

    @ManyToOne
    private ItemHistory history;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ItemHistory getHistory() {
        return history;
    }

    public void setHistory(ItemHistory history) {
        this.history = history;
    }
}
