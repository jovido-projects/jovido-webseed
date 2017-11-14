package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorColumn(name = "type")
public abstract class Payload {

    @Column(insertable = false, updatable = false)
    private String type;

    @Id
    @GeneratedValue
    private Long id;

//    @Column(unique = true)
//    @Convert(converter = UUIDConverter.class)
//    private UUID uuid;

    @ManyToOne(targetEntity = PayloadGroup.class)
    private PayloadGroup group;

    private int ordinal = -1;

    @Lob
    private String text;

    @Transient
    private boolean compressed = false;

    public String getType() {
        return type;
    }

    /*public*/ void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    /*public*/ void setId(Long id) {
        this.id = id;
    }

//    public UUID getUuid() {
//        if (uuid == null) {
//            uuid = UUID.randomUUID();
//        }
//
//        return uuid;
//    }
//
//    /*public*/ void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }

    public PayloadGroup getGroup() {
        return group;
    }

    /*public*/ void setGroup(PayloadGroup group) {
        this.group = group;
    }

    public int getOrdinal() {
        return ordinal;
    }

    /*public*/ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    protected String getText() {
        return text;
    }

    protected void setText(String text) {
        this.text = text;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public void remove() {
        group.removePayload(getOrdinal());
    }

    public boolean differsFrom(Payload other) {
        return true;
    }

    public abstract Payload copy();
}
