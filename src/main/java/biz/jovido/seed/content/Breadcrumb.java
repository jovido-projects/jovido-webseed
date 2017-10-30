package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class Breadcrumb {

    private String text;
    private String path;
    private boolean active;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Breadcrumb(String text, String path, boolean active) {
        this.text = text;
        this.path = path;
        this.active = active;
    }

    public Breadcrumb(String text, String path) {
        this(text, path, false);
    }

    public Breadcrumb(String text) {
        this(text, null, true);
    }
}
