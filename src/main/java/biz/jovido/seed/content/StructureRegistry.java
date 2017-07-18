package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public interface StructureRegistry {

    public Structure registerStructure(Structure structure);
    Structure getStructure(String name);
}
