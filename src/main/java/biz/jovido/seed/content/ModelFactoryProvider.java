package biz.jovido.seed.content;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public class ModelFactoryProvider {

    private ModelFactory defaultModelFactory;
    private Set<ModelFactory> modelFactories;

    public ModelFactory getDefaultModelFactory() {
        return defaultModelFactory;
    }

    public void setDefaultModelFactory(ModelFactory defaultModelFactory) {
        this.defaultModelFactory = defaultModelFactory;
    }

    public Set<ModelFactory> getModelFactories() {
        if (modelFactories == null) {
            modelFactories = new LinkedHashSet<>();
        }

        return modelFactories;
    }

    public void setModelFactories(Set<ModelFactory> modelFactories) {
        this.modelFactories = modelFactories;
    }

    public ModelFactory getModelFactory(Structure structure) {
        Set<ModelFactory> modelFactories = getModelFactories();
        for (ModelFactory modelFactory : modelFactories) {
            if (modelFactory.supports(structure)) {
                return modelFactory;
            }
        }

        return defaultModelFactory;
    }
}
