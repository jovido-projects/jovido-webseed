package biz.jovido.seed.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Stephan Grundner
 */
@ComponentScan("biz.jovido.seed")
@EntityScan("biz.jovido.seed")
@EnableJpaRepositories("biz.jovido.seed")
public class SeedConfigurationSupport {
}
