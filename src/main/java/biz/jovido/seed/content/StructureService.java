package biz.jovido.seed.content;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    private final Map<String, Structure> structureByName = new HashMap<>();

    public Structure getStructure(String name) {
        return structureByName.get(name);
    }

    private Structure getOrCreateStructure(String name) {
        Structure structure = structureByName.get(name);
        if (structure == null) {
            structure = new Structure(name);
            structureByName.put(name, structure);
        }

        return structure;
    }

    public StructureConfiguration configureStructure(String name) {
        Structure structure = getOrCreateStructure(name);
        return new StructureConfigurer(this, structure);
    }

    public void validateStructure(Structure structure) {
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);

            Assert.isTrue(attribute.getRequired() <= attribute.getCapacity(),
                    "[required] must not be greater than [capacity]");
        }
    }
}
