package biz.jovido.seed.content.model;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Content
@Documented
public @interface TextContent {

    TextType value() default TextType.PLAIN;
}
