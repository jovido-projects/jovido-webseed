package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class RelationPropertyField extends PropertyField {

    private final List<Structure> allowedStructures = new ArrayList<>();

    public List<Structure> getAllowedStructures() {
        return allowedStructures;
    }
}
