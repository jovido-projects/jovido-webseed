package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class AssetPayload<T extends Asset> extends Payload<T> {

    public abstract T getAsset();
    public abstract void setAsset(T asset);

    @Override
    public T getValue() {
        return getAsset();
    }

    @Override
    public void setValue(T value) {
        setAsset(value);
    }
}
