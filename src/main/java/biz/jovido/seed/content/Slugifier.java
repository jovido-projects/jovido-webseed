package biz.jovido.seed.content;

import org.springframework.util.StringUtils;

import java.text.Normalizer;

/**
 * @author Stephan Grundner
 */
public class Slugifier {

    public String slugify(String text, String separator) {
        if (!StringUtils.hasLength(text)) {
            return text;
        }
        text = text.toLowerCase();
        text = text.replaceAll("ä", "ae");
        text = text.replaceAll("ö", "oe");
        text = text.replaceAll("ü", "ue");
        return Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\p{Alnum}]+", separator);
    }
}
