package biz.jovido.seed.ui;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public abstract class Field<G extends FieldGroup<?, G>> implements HasId {

    private String id;
    private G group;
//    private String bindingPath;

    @Override
    public String getId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public G getGroup() {
        return group;
    }

    public void setGroup(G group) {
        this.group = group;
    }

    public abstract String getBindingPath();

//    public void setBindingPath(String bindingPath) {
//        this.bindingPath = bindingPath;
//    }
}
