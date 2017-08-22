package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Item;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ItemListing {

    private Page<Item> page;

    public Page<Item> getPage() {
        return page;
    }

    public void setPage(Page<Item> page) {
        this.page = page;
    }

    public List<Item> getItems() {
        return page.getContent();
    }
}
