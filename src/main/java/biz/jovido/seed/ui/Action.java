package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Action {

    private String text;
    private String url;
    private boolean disabled;

    public String getText() {
        return text;
    }

    public void setText(String text) {
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
}
