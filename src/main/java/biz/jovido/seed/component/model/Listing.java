package biz.jovido.seed.component.model;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
public class Listing<T> {

    private String titleCode;
    private Page<T> page;
    private Map<String, Column> columnMapping = new LinkedHashMap<>();
    private List<T> items;

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Set<String> getColumnNames() {
        return columnMapping.keySet();
    }

    public Collection<Column> getColumns() {
        return Collections.unmodifiableCollection(columnMapping.values());
    }

    public Column addColumn(String name) {
        Column column = new Column(this, name);
        Column previous = columnMapping.put(name, column);
        Assert.isNull(previous);

        return column;
    }

    public void addColumns(String... names) {
        Stream.of(names).forEach(this::addColumn);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public interface Cell extends Component {

    }

    public interface CellGenerator<T> {

        Cell generateCell(Listing<T> listing, Column column, int row);
    }

    public static class DefaultCell implements Cell {

        private String template = "component/listing/cell";
        public BeanWrapper bean;

        @Override
        public String getTemplate() {
            return template;
        }
    }

    public static class DefaultCellGenerator<T> implements CellGenerator<T> {

        @Override
        public Cell generateCell(Listing<T> listing, Column column, int row) {
            T item = listing.getPage().getContent().get(row);
            DefaultCell cell = new DefaultCell();
            cell.bean = new BeanWrapperImpl(item);

            return cell;
        }
    }

    /**
     * @author Stephan Grundner
     */
    public static class Column {

        private final Listing listing;
        private final String name;
        private CellGenerator<?> cellGenerator = new DefaultCellGenerator<>();

        public Listing getListing() {
            return listing;
        }

        public String getName() {
            return name;
        }

        public CellGenerator<?> getCellGenerator() {
            return cellGenerator;
        }

        public void setCellGenerator(CellGenerator<?> cellGenerator) {
            this.cellGenerator = cellGenerator;
        }

        public Column(Listing listing, String name) {
            this.listing = listing;
            this.name = name;
        }
    }
}
