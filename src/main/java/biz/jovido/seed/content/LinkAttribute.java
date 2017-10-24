package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
//@Entity
public class LinkAttribute extends Attribute {

    @Override
    public Payload createPayload() {
        return new LinkPayload();
    }
}
