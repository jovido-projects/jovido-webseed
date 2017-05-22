package biz.jovido.seed;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice
public class LocaleArgumentSupport {

    private static final class Foobar extends PropertyEditorSupport {

        @Override
        public String getAsText() {
            Object value = getValue();
            if (value != null) {
                return ((Locale) value).toLanguageTag();
            }

            return null;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (!StringUtils.isEmpty(text)) {
                Locale locale = Locale.forLanguageTag(text);
                setValue(locale);
            }
        }
    }


    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Locale.class, new Foobar());
    }
}
