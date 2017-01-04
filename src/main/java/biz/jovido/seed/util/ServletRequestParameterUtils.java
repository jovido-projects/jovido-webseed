package biz.jovido.seed.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Stephan Grundner
 */
@Deprecated
public class ServletRequestParameterUtils {

    public static class Parameter {

        private final Parameter parent;
        private final String name;
//        private final String[] values;
        private final Map<String, Parameter> children = new HashMap<>();

        public Parameter getParent() {
            return parent;
        }

        public String getName() {
            return name;
        }

        public Map<String, Parameter> getChildren() {
            return children;
        }

        public Parameter(Parameter parent, String name) {
            this.parent = parent;
            this.name = name;
        }
    }

    public static void parse(Parameter parent, String parameterPath) {

        String parameterName;
        String remainingPath;
        int indexOfDot = parameterPath.indexOf('.');
        if (indexOfDot != -1) {
            parameterName = parameterPath.substring(0, indexOfDot);
            remainingPath = parameterPath.substring(indexOfDot + 1);
        } else {
            parameterName = parameterPath;
            remainingPath = "";
        }

        if (!StringUtils.isEmpty(remainingPath)) {
            Parameter child = parent.children.get(parameterName);
            if (child == null) {
                child = new Parameter(parent, parameterName);
                parent.children.put(parameterName, child);
            }
            parse(child, remainingPath);
        }
    }

    public static List<Parameter> parse(HttpServletRequest request) {

        Map<String, Parameter> parameterByName = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> parameterNames = parameterMap.keySet();

        Parameter root = new Parameter(null, "");
        for (String parameterName : parameterNames) {

            parse(root, parameterName);

            continue;
//            String propertyName = parameterName.substring(0)

//            List<String> propertyPathList = Arrays.asList(parameterName.split("\\."));

        }



        return null;
    }
}
