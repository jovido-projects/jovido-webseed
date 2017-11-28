package biz.jovido.seed.net;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@Entity
public class Visit {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Visitor visitor;

    @ManyToOne(optional = false)
    private Host host;

    @Column(length = 255 * 4)
    private String url;
    private Date pointInTime;
    private String ipAddress;

    @Column(length = 255 * 6)
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getPointInTime() {
        return pointInTime;
    }

    public void setPointInTime(Date pointInTime) {
        this.pointInTime = pointInTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
