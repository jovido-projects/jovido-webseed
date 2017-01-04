package biz.jovido.seed.content.web;

import biz.jovido.seed.content.domain.Fragment;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FragmentType {

    @AliasFor("clazz")
    Class<? extends Fragment> value() default Fragment.class;

    @AliasFor("value")
    Class<? extends Fragment> clazz() default Fragment.class;
}
