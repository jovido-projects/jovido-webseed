package biz.jovido.seed.util.content.node

import biz.jovido.seed.model.content.node.field.Constraint
import biz.jovido.seed.model.content.node.Field
import biz.jovido.seed.model.content.node.Type
import biz.jovido.seed.model.content.node.field.constraint.AlphanumericConstraint
import biz.jovido.seed.model.content.node.field.constraint.NumericConstraint
import biz.jovido.seed.model.content.node.field.constraint.ReferenceConstraint
import groovy.transform.CompileStatic

/**
 *
 * @author Stephan Grundner
 */
@CompileStatic
class TypeBuilder<B extends TypeBuilder> {

    static abstract class FieldBuilder<B extends FieldBuilder> extends TypeBuilder<B> {

        private final TypeBuilder parent

        protected abstract Constraint getConstraint()

        protected FieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field)
            this.parent = parent
        }

        B setMaximumNumberOfItems(int maximumNumberOfItems) {
            constraint.maximumNumberOfPayloads = maximumNumberOfItems

            this as B
        }

        B setMinimumNumberOfItems(int minimumNumberOfItems) {
            constraint.minimumNumberOfPayloads = minimumNumberOfItems

            this as B
        }

        B setNullable(boolean nullable) {
            constraint.nullable = nullable

            this as B
        }
    }

    static class AlphanumericFieldBuilder extends FieldBuilder<AlphanumericFieldBuilder> {

        private AlphanumericConstraint constraint

        @Override
        protected Constraint getConstraint() {
            constraint
        }

        AlphanumericFieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field, parent)

            constraint = new AlphanumericConstraint()

            field.constraint = constraint
        }

        AlphanumericFieldBuilder setMultiline(boolean multiline) {
            constraint.multiline = multiline

            this
        }

        AlphanumericFieldBuilder setHtml(boolean html) {
            constraint.html = html

            this
        }
    }

    static class NumericFieldBuilder extends FieldBuilder<NumericFieldBuilder> {

        private NumericConstraint constraint

        @Override
        protected Constraint getConstraint() {
            constraint
        }

        NumericFieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field, parent)

            constraint = new NumericConstraint()

            field.constraint = constraint
        }
    }

    static class NodeFieldBuilder extends FieldBuilder<NodeFieldBuilder> {

        private ReferenceConstraint constraint

        @Override
        protected Constraint getConstraint() {
            constraint
        }

        NodeFieldBuilder(Type type, Field field, TypeBuilder parent) {
            super(type, field, parent)

            constraint = new ReferenceConstraint()

            field.constraint = constraint
        }

        @Deprecated
        NodeFieldBuilder withStructureName(String structureName) {
            constraint.addAllowedStructureName(structureName)

            this
        }

        NodeFieldBuilder withoutStructureName(String structureName) {
            constraint.removeAllowedStructureName(structureName)

            this
        }
    }

    private Type type
    private Field field

    TypeBuilder(Type type, Field field) {
        this.type = type
        this.field = field
    }

    TypeBuilder() {
        type = new Type()
    }

    B setName(String name) {
        type.name = name

        this as B
    }

    private Field createField(String fieldName) {
        def field = type.getField(fieldName)
        if (field != null) {
            throw new IllegalStateException("Field [$fieldName] has already been added")
        }

        field = new Field()
        field.name = fieldName
        field.type = type

        def previous = type.addField(field)
        assert previous == null, "Field $fieldName was there"

        field
    }

    AlphanumericFieldBuilder addAlphanumericField(String fieldName) {
        field = createField(fieldName)

        new AlphanumericFieldBuilder(type, field, this)
    }

    NumericFieldBuilder addNumericField(String fieldName) {
        field = createField(fieldName)

        new NumericFieldBuilder(type, field, this)
    }

    NodeFieldBuilder addNodeField(String fieldName) {
        field = createField(fieldName)

        new NodeFieldBuilder(type, field, this)
    }

    Type getType() {
        type
    }
}
