package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.ui.source.Source;
import biz.jovido.seed.ui.source.SourcesContainer;
import biz.jovido.seed.util.MapUtils;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class Grid implements HasTemplate {

    public interface CellGenerator {

        Cell generateCell(Row row, Column column);
    }

    public static class Column {

        private final Grid grid;
        private final String name;

        private CellGenerator cellGenerator;

        public Grid getGrid() {
            return grid;
        }

        public String getName() {
            return name;
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

        private final Row row;
        private final Column column;
        private String template = "ui/grid-cell";

        public Row getRow() {
            return row;
        }

        public Column getColumn() {
            return column;
        }

        @Override
        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public Cell(Row row, Column column) {
            this.row = row;
            this.column = column;
        }
    }

    public static class Row {

        private final Grid grid;
        private final Source source;

        private final Map<String, ? extends Cell> cellByColumnName = new HashMap<>();

        public Cell getCell(String columnName) {
            Cell cell = cellByColumnName.get(columnName);
            if (cell == null) {
                Column column = grid.getColumn(columnName);
                CellGenerator cellGenerator = column.getCellGenerator();
                if (cellGenerator != null) {
                    return cellGenerator.generateCell(Row.this, column);
                } else {
                    cell = new Cell(Row.this, column);
                }
            }

            return cell;
        }

        public Grid getGrid() {
            return grid;
        }

        public Source getSource() {
            return source;
        }

        public Row(Grid grid, Source source) {
            this.grid = grid;
            this.source = source;
        }
    }

    private String template = "ui/grid";

    private final Map<String, Column> columnByName = new LinkedHashMap<>();
    private final List<Row> rows = new ArrayList<>();

    private SourcesContainer<?, ?> container;

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

    public List<Row> getRows() {
        return Collections.unmodifiableList(rows);
    }

    private void addRows(Collection<? extends Source> sources) {
        for (Source source : sources) {
            Row row = new Row(this, source);
            rows.add(row);
        }
    }

    private void removeRows(Collection<? extends Source> sources) {
        for (Source source : sources) {
            rows.removeIf(it -> it.source == source);
        }
    }

    private void containerChanged(SourcesContainer.ChangeEvent<? extends Source, ? extends SourcesContainer> event) {
        if (event.added()) {
            addRows(event.getSources());
        } else if (event.removed()) {
            removeRows(event.getSources());
        }
    }

    public SourcesContainer<?, ?> getContainer() {
        return container;
    }

    public void setContainer(SourcesContainer<?, ?> container) {
        this.container = container;

        rows.clear();
        addRows(container.getSources());
        container.addChangeListener(Grid.this::containerChanged);
    }
}
