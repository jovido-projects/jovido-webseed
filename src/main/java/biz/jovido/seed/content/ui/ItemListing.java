package biz.jovido.seed.content.ui;

import biz.jovido.seed.MessageSourceProvider;
import biz.jovido.seed.content.Item;
import biz.jovido.seed.ui.*;
import biz.jovido.seed.ui.source.BeanSource;
import biz.jovido.seed.ui.source.BeanSourcesContainer;
import org.springframework.context.MessageSource;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ItemListing extends Listing implements MessageSourceProvider {

    public static class ActionsCell extends Grid.Cell {

        private Actions actions = new Actions();

        public Actions getActions() {
            return actions;
        }

        public ActionsCell(Grid.Row row, Grid.Column column) {
            super(row, column);
        }
    }

    private MessageSource messageSource;
    private Grid grid = new Grid();

    private List<Item> items;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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
        grid.addColumn("structureName");
        grid.addColumn("createdAt");
        grid.addColumn("lastModifiedAt");

        Grid.Column actionsColumn = grid.addColumn("actions");
        actionsColumn.setCellGenerator(new Grid.CellGenerator() {
            @Override
            public Grid.Cell generateCell(Grid.Row row, Grid.Column column) {
                Item item = (Item) BeanSource.getBean(row.getSource());
                ActionsCell cell = new ActionsCell(row, column);
                cell.setTemplate("admin/item/grid/actions-cell");
                Actions actions = cell.getActions();
                Action edit = new Action();
                edit.setUrl("/admin/item/edit?id=" + item.getId());
                actions.setDefaultAction(edit);
                return cell;
            }
        });
        ResolvableText actionsColumnText = new ResolvableText(this,
                "itemlisting.actions", "Action");
        actionsColumn.setText(actionsColumnText);

        BeanSourcesContainer<Item> container = new BeanSourcesContainer<>();
        container.addBeans(items);
        grid.setContainer(container);
    }
}
