package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class AssetPayload<T extends Asset> extends Payload {

    public abstract T getAsset();
    public abstract void setAsset(T asset);
}
