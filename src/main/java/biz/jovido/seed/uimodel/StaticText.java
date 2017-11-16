package biz.jovido.seed.uimodel;

/**
 * @author Stephan Grundner
 */
public class StaticText implements Text {

    private String value;

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StaticText(String value) {
        this.value = value;
    }

    public StaticText() {}
}
