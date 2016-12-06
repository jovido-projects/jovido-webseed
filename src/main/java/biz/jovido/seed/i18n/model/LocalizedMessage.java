package biz.jovido.seed.i18n.model;

import javax.persistence.*;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Table(name = "message", uniqueConstraints =
    @UniqueConstraint(columnNames = {"code", "locale"}))
@Entity
public class LocalizedMessage {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String code;

    @Column
    private Locale locale;

    @Lob
    @Column
    private String text;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
