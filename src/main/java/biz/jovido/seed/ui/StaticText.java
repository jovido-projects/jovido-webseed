package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class StaticText extends AbstractText {

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
