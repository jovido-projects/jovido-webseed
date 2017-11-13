package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Breadcrumb {

    private Text text;
    private String path;
    private boolean active;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
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

    public Breadcrumb(Text text, String path, boolean active) {
        this.text = text;
        this.path = path;
        this.active = active;
    }

    public Breadcrumb(Text text, String path) {
        this(text, path, false);
    }

    public Breadcrumb(Text text) {
        this(text, null, true);
    }

    public Breadcrumb(String messageCode, String path, boolean active) {
        this(new Text(messageCode), path, active);
    }

    public Breadcrumb(String messageCode, String path) {
        this(messageCode, path, false);
    }

    public Breadcrumb(String messageCode) {
        this(messageCode, null, true);
    }
}
