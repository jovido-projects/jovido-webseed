package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("link")
public class LinkPayload extends Payload {

    private String text;
    private String url;

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

    @Override
    public Payload copy() {
        LinkPayload copy = new LinkPayload();
        copy.setAttributeName(getAttributeName());
        copy.setText(getText());
        copy.setUrl(getUrl());

        return copy;
    }

    public LinkPayload() {
        super(PayloadType.LINK);
    }
}
