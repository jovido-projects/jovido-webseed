package biz.jovido.seed;

import org.springframework.util.StringUtils;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Stephan Grundner
 */
public class Slugifier {

//    public static final Pattern DEFAULT_DELIMITER_PATTERN = Pattern.compile("[\\W\\s+]+");
    public static final Pattern DEFAULT_DELIMITER_PATTERN = Pattern.compile("[^A-Za-zäöü0-9_-]+");

    private final Pattern delimiterPattern;

    public String slugify(String text, char separator) {
        if (!StringUtils.hasLength(text)) {
            return text;
        }

        StringBuilder slugified = new StringBuilder();

        Scanner scanner = new Scanner(text);
        scanner.useDelimiter(delimiterPattern);
        while (scanner.hasNext()) {
            if (slugified.length() > 0) {
                slugified.append(separator);
            }
            String token = scanner.next();
            token = token.replaceAll("[äÄ]", "ae");
            token = token.replaceAll("ö", "oe");
            token = token.replaceAll("ü", "ue");
            slugified.append(token.toLowerCase());
        }

        return slugified.toString();
    }

    public Slugifier(Pattern delimiterPattern) {
        this.delimiterPattern = delimiterPattern;
    }

    public Slugifier() {
        this(DEFAULT_DELIMITER_PATTERN);
    }
}
