package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Stephan Grundner
 */
public interface FieldGroup<F extends Field<G>, G extends FieldGroup<F, G>> extends HasId, HasTemplate {

    Collection<F> getFields();
    Collection<G> getNestedFieldGroups();

    default F findField(Predicate<F> predicate) {

        for (G child : getNestedFieldGroups()) {
            for (F field : child.getFields()) {
                if (predicate.test(field)) {
                    return field;
                }
            }
        }

        return null;
    }
}
