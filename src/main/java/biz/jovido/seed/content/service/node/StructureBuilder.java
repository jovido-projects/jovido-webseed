package biz.jovido.seed.content.service.node;

import biz.jovido.seed.content.model.node.Structure;
import biz.jovido.seed.content.model.node.structure.Constraints;
import biz.jovido.seed.content.model.node.structure.Field;
import biz.jovido.seed.content.model.node.structure.constraint.DecimalConstraints;
import biz.jovido.seed.content.model.node.structure.constraint.NodeConstraints;
import biz.jovido.seed.content.model.node.structure.constraint.TextConstraints;
import groovy.transform.CompileStatic;

/**
 * @author Stephan Grundner
 */
@CompileStatic
public class StructureBuilder<B extends StructureBuilder> {

    public StructureBuilder(Structure structure, Field field) {
        this.structure = structure;
        this.field = field;
    }

    public StructureBuilder() {
        structure = new Structure();
    }

    public B setName(String name) {
        structure.setName(name);

        return (B) this;
    }

    private Field createField(String fieldName) {
        Field field = structure.getField(fieldName);
        assert field == null;

        field = structure.addField(fieldName);

        return ((Field) (field));
    }

    public AlphanumericFieldBuilder addAlphanumericField(String fieldName) {
        field = createField(fieldName);

        return new AlphanumericFieldBuilder(structure, field, this);
    }

    public NumericFieldBuilder addNumericField(String fieldName) {
        field = createField(fieldName);

        return new NumericFieldBuilder(structure, field, this);
    }

    public NodeFieldBuilder addNodeField(String fieldName) {
        field = createField(fieldName);

        return new NodeFieldBuilder(structure, field, this);
    }

    @SuppressWarnings("unchecked")
    public B setLabelField(String fieldName) {
        Field field = structure.getField(fieldName);
        structure.setLabelField(field);

        return (B) this;
    }

    public Structure getStructure() {
        return structure;
    }

    private Structure structure;
    private Field field;

    public static abstract class FieldBuilder<B extends StructureBuilder<? extends B>> extends StructureBuilder<B> {

        protected abstract Constraints getConstraints();

        protected FieldBuilder(Structure structure, Field field, StructureBuilder parent) {
            super(structure, field);
            this.parent = parent;
        }

        public B setMaximumNumberOfItems(int maximumNumberOfItems) {
            getConstraints().setMaximumNumberOfValues(maximumNumberOfItems);

            return (B) this;
        }

        public B setMinimumNumberOfItems(int minimumNumberOfItems) {
            getConstraints().setMinimumNumberOfValues(minimumNumberOfItems);

            return  (B) this;
        }

        public B setNullable(boolean nullable) {
            getConstraints().setNullable(nullable);

            return  (B) this;
        }

        private final StructureBuilder parent;
    }

    public static class AlphanumericFieldBuilder extends FieldBuilder<AlphanumericFieldBuilder> {

        protected Constraints getConstraints() {
            return constraints;
        }

        public AlphanumericFieldBuilder(Structure structure, Field field, StructureBuilder parent) {
            super(structure, field, parent);

            constraints = new TextConstraints();

            field.setConstraints(constraints);
        }

        public AlphanumericFieldBuilder setMultiline(boolean multiline) {
            constraints.setMultiline(multiline);

            return this;
        }

        public AlphanumericFieldBuilder setHtml(boolean html) {
            constraints.setHtml(html);

            return this;
        }

        private TextConstraints constraints;
    }

    public static class NumericFieldBuilder extends FieldBuilder<NumericFieldBuilder> {

        protected Constraints getConstraints() {
            return constraint;
        }

        public NumericFieldBuilder(Structure structure, Field field, StructureBuilder parent) {
            super(structure, field, parent);

            constraint = new DecimalConstraints();

            field.setConstraints(constraint);
        }

        private DecimalConstraints constraint;
    }

    public static class NodeFieldBuilder extends FieldBuilder<NodeFieldBuilder> {

        protected Constraints getConstraints() {
            return constraint;
        }

        public NodeFieldBuilder(Structure structure, Field field, StructureBuilder parent) {
            super(structure, field, parent);

            constraint = new NodeConstraints();

            field.setConstraints(constraint);
        }

        @Deprecated
        public NodeFieldBuilder withStructure(String structureName) {
            constraint.getAllowedStructures().add(structureName);

            return this;
        }

        public NodeFieldBuilder withoutStructure(String structureName) {
            constraint.getAllowedStructures().remove(structureName);

            return this;
        }

        private NodeConstraints constraint;
    }
}
