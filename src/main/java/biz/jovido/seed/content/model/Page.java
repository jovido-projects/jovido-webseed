package biz.jovido.seed.content.model;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class Page extends Fragment {

    @Content
    private String title;

    @Content
    private String subtitle;

    @Content
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
