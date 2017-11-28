package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class DateAttribute extends Attribute {

    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public Payload createPayload() {
        return new DatePayload();
    }
}
