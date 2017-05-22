package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class StructureBuilder {

    private String name;

    public StructureBuilder setName(String name) {
        this.name = name;

        return this;
    }

    public Structure build() {
        return new Structure(name);
    }
}
