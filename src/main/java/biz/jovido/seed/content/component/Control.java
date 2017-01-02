package biz.jovido.seed.content.component;

import org.springframework.util.StringUtils;

/**
 * @author Stephan Grundner
 */
public abstract class Control {

    private final String nestedPath;
    private String propertyName;
    private Integer index;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getNestedPath() {
        return nestedPath;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getPropertyPath() {
        if (StringUtils.isEmpty(nestedPath)) {
            return propertyName;
        }

        String propertyPath = nestedPath;
        if (!propertyPath.endsWith(".")) {
            propertyPath += ".";
        }

        propertyPath += propertyName;

        return propertyPath;
    }

    public Control(String nestedPath) {
        this.nestedPath = nestedPath;
    }
}
