package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Action {

    private Text text;
    private String url;
    public boolean disabled;
    public boolean visible;

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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
