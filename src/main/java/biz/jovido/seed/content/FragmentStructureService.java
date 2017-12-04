package biz.jovido.seed.content;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Service
public class FragmentStructureService {

    private final Map<String, FragmentStructure> structureByName = new HashMap<>();

    public FragmentStructure getStructure(String name) {
        return structureByName.get(name);
    }

    private FragmentStructure getOrCreateStructure(String name) {
        FragmentStructure structure = structureByName.get(name);
        if (structure == null) {
            structure = new FragmentStructure(name);
            structureByName.put(name, structure);
        }

        return structure;
    }

    public FragmentStructureConfiguration configureStructure(String name) {
        FragmentStructure structure = getOrCreateStructure(name);
        return new FragmentStructureConfigurer(this, structure);
    }

    public void validateStructure(FragmentStructure structure) {
        for (String attributeName : structure.getAttributeNames()) {
            PayloadAttribute<?> attribute = structure.getAttribute(attributeName);

            Assert.isTrue(attribute.getRequired() <= attribute.getCapacity(),
                    "[required] must not be greater than [capacity]");
        }
    }
}
