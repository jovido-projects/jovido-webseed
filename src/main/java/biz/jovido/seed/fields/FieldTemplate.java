package biz.jovido.seed.fields;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldTemplate {

    @AliasFor("template")
    String value() default "";

    @AliasFor("value")
    String template() default "";
}
