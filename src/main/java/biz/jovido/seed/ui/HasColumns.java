package biz.jovido.seed.ui;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class HasColumns {

    public interface ValueResolver {

        Object resolveValue(Column column, Object source);
    }

    private static class BeanPropertyValueResolver implements ValueResolver {

        @Override
        public Object resolveValue(Column column, Object source) {
            BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
            return sourceWrapper.getPropertyValue(column.getName());
        }
    }

    public static class Column extends Text {

        private final String name;
        private ValueResolver valueResolver;
        private String valueTemplate;

        public String getName() {
            return name;
        }

        public ValueResolver getValueResolver() {
            if (valueResolver == null) {
                valueResolver = new BeanPropertyValueResolver();
            }

            return valueResolver;
        }

        public void setValueResolver(ValueResolver valueResolver) {
            this.valueResolver = valueResolver;
        }

        public String getValueTemplate() {
            return valueTemplate;
        }

        public void setValueTemplate(String valueTemplate) {
            this.valueTemplate = valueTemplate;
        }

        public Column(String name) {
            this.name = name;
        }
    }

    private final Map<String, Column> columns = new LinkedHashMap<>();

    public Set<String> getColumnNames() {
        return Collections.unmodifiableSet(columns.keySet());
    }

    public Collection<Column> getColumns() {
        return Collections.unmodifiableCollection(columns.values());
    }

    public Column addColumn(String name) {
        if (columns.containsKey(name)) {
            throw new RuntimeException(String.format("Column {} already exists", name));
        }

        Column column = new Column(name);
        Object result = columns.put(name, column);
        Assert.isNull(result);

        return column;
    }

    public Column addColumn(String name, String messageCode, Object[] messageArguments, String defaultMessage) {
        Column column = addColumn(name);
        column.setMessageCode(messageCode);
        column.setMessageArguments(messageArguments);
        column.setDefaultMessage(defaultMessage);

        return column;
    }

    public Column addColumn(String name, String messageCode) {
        return addColumn(name, messageCode, null, null);
    }

    public void addColumns(String... names) {
        for (String name : names) {
            addColumn(name);
        }
    }
}
