package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payload {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Translation translation;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    Element element;

//    @Formula("select p.name from Element e join e.property p where e = element")
//    private String propertyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Translation getTranslation() {
        return translation;
    }

    public Element getElement() {
        return element;
    }

    public abstract Object getValue();
}
