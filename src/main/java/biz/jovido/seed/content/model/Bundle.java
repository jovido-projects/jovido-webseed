package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
public final class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    private String structureName;

    @OneToMany(mappedBy = "bundle")
    @MapKey(name = "locale")
    private final Map<Locale, Chronicle> chronicles = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Map<Locale, Chronicle> getChronicles() {
        return Collections.unmodifiableMap(chronicles);
    }

    public void putChronicle(Chronicle chronicle) {
        Locale locale = chronicle.getLocale();
        Chronicle replaced = chronicles.put(locale, chronicle);
        if (replaced != null) {
            replaced.bundle = null;
        }

        chronicle.bundle = this;
    }
}
