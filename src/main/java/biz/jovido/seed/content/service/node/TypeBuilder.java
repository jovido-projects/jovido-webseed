package biz.jovido.seed.content.service.node;

import biz.jovido.seed.content.model.node.Field;
import biz.jovido.seed.content.model.node.Type;
import biz.jovido.seed.content.model.node.field.Constraint;
import biz.jovido.seed.content.model.node.field.constraint.AlphanumericConstraint;
import biz.jovido.seed.content.model.node.field.constraint.NumericConstraint;
import biz.jovido.seed.content.model.node.field.constraint.ReferenceConstraint;
import groovy.transform.CompileStatic;

/**
 * @author Stephan Grundner
 */
@CompileStatic
public class TypeBuilder<B extends TypeBuilder> {

    public TypeBuilder(Type type, Field field) {
        this.type = type;
        this.field = field;
    }

    public TypeBuilder() {
        type = new Type();
    }

    public B setName(String name) {
        type.setName(name);

        return (B) this;
    }

    private Field createField(String fieldName) {
        Field field = type.getField(fieldName);
        assert field == null;

        field = type.addField(fieldName);

        return ((Field) (field));
    }

    public AlphanumericFieldBuilder addAlphanumericField(String fieldName) {
        field = createField(fieldName);

        return new AlphanumericFieldBuilder(type, field, this);
    }

    public NumericFieldBuilder addNumericField(String fieldName) {
        field = createField(fieldName);

        return new NumericFieldBuilder(type, field, this);
    }

    public NodeFieldBuilder addNodeField(String fieldName) {
        field = createField(fieldName);

        return new NodeFieldBuilder(type, field, this);
    }

    public Type getType() {
        return type;
    }

    private Type type;
    private Field field;

    public static abstract class FieldBuilder<B extends TypeBuilder<? extends B>> extends TypeBuilder<B> {

        protected abstract Constraint getConstraint();

        protected FieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field);
            this.parent = parent;
        }

        public B setMaximumNumberOfItems(int maximumNumberOfItems) {
            getConstraint().setMaximumNumberOfPayloads(maximumNumberOfItems);

            return (B) this;
        }

        public B setMinimumNumberOfItems(int minimumNumberOfItems) {
            getConstraint().setMinimumNumberOfPayloads(minimumNumberOfItems);

            return  (B) this;
        }

        public B setNullable(boolean nullable) {
            getConstraint().setNullable(nullable);

            return  (B) this;
        }

        private final TypeBuilder parent;
    }

    public static class AlphanumericFieldBuilder extends FieldBuilder<AlphanumericFieldBuilder> {

        @Override
        protected Constraint getConstraint() {
            return constraint;
        }

        public AlphanumericFieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field, parent);

            constraint = new AlphanumericConstraint();

            field.setConstraint(constraint);
        }

        public AlphanumericFieldBuilder setMultiline(boolean multiline) {
            constraint.setMultiline(multiline);

            return this;
        }

        public AlphanumericFieldBuilder setHtml(boolean html) {
            constraint.setHtml(html);

            return this;
        }

        private AlphanumericConstraint constraint;
    }

    public static class NumericFieldBuilder extends FieldBuilder<NumericFieldBuilder> {

        @Override
        protected Constraint getConstraint() {
            return constraint;
        }

        public NumericFieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field, parent);

            constraint = new NumericConstraint();

            field.setConstraint(constraint);
        }

        private NumericConstraint constraint;
    }

    public static class NodeFieldBuilder extends FieldBuilder<NodeFieldBuilder> {

        @Override
        protected Constraint getConstraint() {
            return constraint;
        }

        public NodeFieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field, parent);

            constraint = new ReferenceConstraint();

            field.setConstraint(constraint);
        }

        @Deprecated
        public NodeFieldBuilder withStructureName(String structureName) {
            constraint.addAllowedStructureName(structureName);

            return this;
        }

        public NodeFieldBuilder withoutStructureName(String structureName) {
            constraint.removeAllowedStructureName(structureName);

            return this;
        }

        private ReferenceConstraint constraint;
    }
}
