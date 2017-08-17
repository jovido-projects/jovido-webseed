package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.*;
import biz.jovido.seed.util.ListObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class RelationsField extends Field {

    private final List<Structure> allowedStructures = new ArrayList<>();
    private final List<ItemEditor> relations = new ArrayList<>();

    public List<Structure> getAllowedStructures() {
        return allowedStructures;
    }

    public List<ItemEditor> getRelations() {
        return relations;
    }

    private void addRelation(ItemEditor editor) {
        relations.add(editor);
    }

    @Override
    void setPayload(Payload payload) {
        super.setPayload(payload);
        RelationPayload relationPayload = (RelationPayload) payload;
        Relation relation = relationPayload.getValue();
        relation.getTargets().addObserver(new ListObserver<Item>() {
            @Override
            public void addedAll(int index, Collection<? extends Item> c) {}

            @Override
            public void set(int index, Item replaced) {}

            @Override
            public void added(int index, Item item) {}

            @Override
            public void removed(int index, Item item) {}

            @Override
            public void added(Item item) {
                ItemEditor editor = new ItemEditor(getEditor().getItemService());
                editor.setItem(item);
                relations.add(editor);
            }

            @Override
            public void addedAll(Collection<? extends Item> c) {}

            @Override
            public void removed(Object o) {}

            @Override
            public void removedAll(Collection<?> c) {}

            @Override
            public void cleared() {}
        });
    }
}
