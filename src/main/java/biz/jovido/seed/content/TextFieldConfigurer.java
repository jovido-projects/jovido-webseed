package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextFieldConfigurer extends FieldConfigurer<TextField, TextFieldConfigurer> {

    public TextFieldConfigurer setMultiline(boolean multiline) {
        field.setMultiline(multiline);

        return this;
    }
}
