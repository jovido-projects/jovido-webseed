package biz.jovido.seed.content.converter;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

/**
 * @author Stephan Grundner
 */
@Component
public class StringToNodeConverter implements Converter<String, Node> {

    @Autowired
    private NodeService nodeService;

    @Override
    public Node convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        Long id = NumberUtils.parseNumber(source, Long.class);
        return nodeService.getNode(id);
    }
}
