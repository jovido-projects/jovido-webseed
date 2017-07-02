package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class DefaultViewNameResolver implements ViewNameResolver {

    @Override
    public String resolveViewName(Item item) {
        Bundle bundle = item.getBundle();
        Structure structure = bundle.getStructure();

        return String.format("%s-%d", structure.getName(), structure.getRevision());
    }
}
