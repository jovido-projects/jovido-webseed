package biz.jovido.seed.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class OneToManyField extends RelationField {

    private final List<ItemEditor> editors = new ArrayList<>();

    public List<ItemEditor> getEditors() {
        return Collections.unmodifiableList(editors);
    }
}
