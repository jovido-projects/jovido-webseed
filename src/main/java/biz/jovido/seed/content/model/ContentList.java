package biz.jovido.seed.content.model;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Content
@Documented
public @interface ContentList {

    @AliasFor("elementType")
    Class<?> value() default Void.class;

    @AliasFor("value")
    Class<?> elementType() default Void.class;
}
