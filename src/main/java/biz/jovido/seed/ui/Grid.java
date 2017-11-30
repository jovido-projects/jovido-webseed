package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.ui.source.Source;
import biz.jovido.seed.ui.source.SourcesContainer;
import biz.jovido.seed.ui.source.SourcesListContainer;
import biz.jovido.seed.util.MapUtils;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class Grid implements HasTemplate {

    public interface CellGenerator {

        Cell generateCell(Column column, int rowIndex, Source source);
    }

    public static class Column {

        private final Grid grid;
        private final String name;

        @Deprecated
        private String cellTemplate;
        private CellGenerator cellGenerator;

        public Grid getGrid() {
            return grid;
        }

        public String getName() {
            return name;
        }

        @Deprecated
        public String getCellTemplate() {
            return cellTemplate;
        }

        @Deprecated
        public void setCellTemplate(String cellTemplate) {
            this.cellTemplate = cellTemplate;
        }

        public CellGenerator getCellGenerator() {
            return cellGenerator;
        }

        public void setCellGenerator(CellGenerator cellGenerator) {
            this.cellGenerator = cellGenerator;
        }

        public Column(Grid grid, String name) {
            this.grid = grid;
            this.name = name;
        }
    }

    public static class Cell implements HasTemplate {

        private final Column column;
        private final int rowIndex;
        private String template;

        public Column getColumn() {
            return column;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        @Override
        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public Cell(Column column, int rowIndex) {
            this.column = column;
            this.rowIndex = rowIndex;
        }
    }

    public static class Row {

    }

    private String template = "ui/grid";

    private final Map<String, Column> columnByName = new LinkedHashMap<>();
    private final List<Row> rows = new ArrayList<>();

    private SourcesListContainer container;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Collection<Column> getColumns() {
        return Collections.unmodifiableCollection(columnByName.values());
    }

    public Column getColumn(String name) {
        return columnByName.get(name);
    }

    public Column addColumn(String name) {
        if (columnByName.containsKey(name)) {
            throw new IllegalArgumentException(String.format("Column [%s] already exists", name));
        }

        Column column = new Column(this, name);
        MapUtils.putOnce(columnByName, name, column);

        return column;
    }

    public void removeAllColumns() {
        columnByName.clear();
    }

    public SourcesContainer getContainer() {
        return container;
    }

    public void setContainer(SourcesListContainer container) {
        this.container = container;
    }
}
