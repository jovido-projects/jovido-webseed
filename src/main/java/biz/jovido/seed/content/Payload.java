package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
@Entity
public class Payload extends AbstractUnique {

    @ManyToOne(targetEntity = Payload.class, optional = false)
    private PayloadList list;

    private int ordinal;

    @Column(name = "text_value")
    private String text;
    private Date date;

    @ManyToOne
    private Fragment fragment;

    @ManyToOne
    private Image image;

    public PayloadList getList() {
        return list;
    }

    protected void setList(PayloadList list) {
        this.list = list;
    }

    public int getOrdinal() {
        return ordinal;
    }

    protected void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
