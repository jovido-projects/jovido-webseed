package biz.jovido.seed.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class Listing extends HasColumns {

    public interface EntryFactory {

        Entry createRow(Listing listing, Object source);
    }

    private static class DefaultEntryFactory implements EntryFactory {

        @Override
        public Entry createRow(Listing listing, Object source) {
            Entry entry = new Entry(listing);
            entry.setSource(source);
            return entry;
        }
    }

    private final List<Entry> entries = new ArrayList<>();
    private EntryFactory entryFactory;

    private final ActionGroup actionGroup = new ActionGroup();

    public static class Entry {

        private final Listing listing;
        private Object source;
        private Action editAction;

        public Listing getListing() {
            return listing;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public Action getEditAction() {
            return editAction;
        }

        public void setEditAction(Action editAction) {
            this.editAction = editAction;
        }

        public Object getValue(Column column) {
            ValueResolver valueResolver = column.getValueResolver();
            return valueResolver.resolveValue(column, source);
        }

        public Entry(Listing listing) {
            this.listing = listing;
        }
    }

    public EntryFactory getEntryFactory() {
        if (entryFactory == null) {
            entryFactory = new DefaultEntryFactory();
        }

        return entryFactory;
    }

    public void setEntryFactory(EntryFactory entryFactory) {
        this.entryFactory = entryFactory;
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void addEntry(Object source) {
        EntryFactory entryFactory = getEntryFactory();
        entries.add(entryFactory.createRow(this, source));
    }

    public void addEntries(List sources) {
        for (Object source : sources) {
            addEntry(source);
        }
    }

    public ActionGroup getActionGroup() {
        return actionGroup;
    }

    public void clear() {
        entries.clear();
    }
}
