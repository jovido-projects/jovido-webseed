package biz.jovido.seed.content.ui;

/**
 * @author Stephan Grundner
 */
public class TextField implements Field {

    private ItemMaintenance maintenance;
    private String attributeName;

    public ItemMaintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(ItemMaintenance maintenance) {
        this.maintenance = maintenance;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
