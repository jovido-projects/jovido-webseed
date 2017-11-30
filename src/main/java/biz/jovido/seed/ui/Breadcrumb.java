package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Breadcrumb {

    private Text text;
    private String url;
    private boolean active;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Breadcrumb(Text text, String url, boolean active) {
        this.text = text;
        this.url = url;
        this.active = active;
    }

    public Breadcrumb(Text text, String url) {
        this(text, url, false);
    }

    public Breadcrumb(Text text) {
        this(text, null, true);
    }
}
