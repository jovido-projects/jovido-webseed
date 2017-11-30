package biz.jovido.seed.content;

import biz.jovido.seed.admin.Listing;
import biz.jovido.seed.ui.Grid;
import biz.jovido.seed.ui.source.BeanSource;
import biz.jovido.seed.ui.source.BeanSourcesContainer;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ItemListing extends Listing {

    private Grid grid = new Grid();

    private List<Item> items;

    public Grid getGrid() {
        return grid;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;

        grid.removeAllColumns();
        grid.addColumn("id");
        Grid.Column structureNameColumn = grid.addColumn("structureName");
        grid.addColumn("createdAt");
        structureNameColumn.setCellTemplate("admin/item/grid/structure-name-cell");

        grid.addColumn("lastModifiedAt");

//        Grid.Column actionsColumn = grid.addColumn("actions");
//        actionsColumn.setCellTemplate("admin/item/grid/actions-cell");

        BeanSourcesContainer<Item> container = new BeanSourcesContainer<>();
        container.addBeans(items);
        grid.setContainer(container);
    }
}
