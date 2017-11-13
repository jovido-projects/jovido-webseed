package biz.jovido.seed;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface UsedInTemplates {
}
