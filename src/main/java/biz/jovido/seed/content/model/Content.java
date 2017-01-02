package biz.jovido.seed.content.model;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Content {

//    Class<?> value() default Void.class;
//
//    AttributeType attribute() default AttributeType.AUTO;
}
