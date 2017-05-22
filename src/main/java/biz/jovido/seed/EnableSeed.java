package biz.jovido.seed;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Stephan Grundner
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SeedConfigurationSupport.class)
public @interface EnableSeed {

}
