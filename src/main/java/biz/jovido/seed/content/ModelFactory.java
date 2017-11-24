package biz.jovido.seed.content;

import org.springframework.ui.Model;

/**
 * @author Stephan Grundner
 */
public interface ModelFactory {

    boolean supports(Structure structure);

    void apply(Item item, Model model);
}
