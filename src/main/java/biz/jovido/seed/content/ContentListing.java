package biz.jovido.seed.content;

import biz.jovido.seed.content.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ContentListing {

    private final List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }
}
