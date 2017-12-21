package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Action {

    private String url;
    private Text text;
    private boolean disabled;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
