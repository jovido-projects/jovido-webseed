package biz.jovido.seed.admin;

import biz.jovido.seed.content.ItemListing;
import biz.jovido.seed.ui.Navigation;

/**
 * @author Stephan Grundner
 */
public class Administration {

    private Navigation navigation;

    private ItemListing itemListing;

    public Navigation getNavigation() {
        if (navigation == null) {
            navigation = new Navigation();
        }

        return navigation;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    public ItemListing getItemListing() {
        return itemListing;
    }

    public void setItemListing(ItemListing itemListing) {
        this.itemListing = itemListing;
    }
}
