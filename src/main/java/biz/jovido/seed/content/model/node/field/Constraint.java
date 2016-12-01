package biz.jovido.seed.content.model.node.field;

import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_field_constraint")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
public abstract class Constraint {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "min_payloads")
    private int minimumNumberOfPayloads;

    @Column(name = "max_payloads")
    private int maximumNumberOfPayloads;

    private boolean nullable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinimumNumberOfPayloads() {
        return minimumNumberOfPayloads;
    }

    public void setMinimumNumberOfPayloads(int minimumNumberOfPayloads) {
        this.minimumNumberOfPayloads = minimumNumberOfPayloads;
    }

    public int getMaximumNumberOfPayloads() {
        return maximumNumberOfPayloads;
    }

    public void setMaximumNumberOfPayloads(int maximumNumberOfPayloads) {
        this.maximumNumberOfPayloads = maximumNumberOfPayloads;
    }

    public boolean getNullable() {
        return nullable;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Transient
    private final Set<Class<? extends Payload>> supportedPayloadTypes = new HashSet<Class<? extends Payload>>();

    public Set<Class<? extends Payload>> getSupportedPayloadTypes() {
        return Collections.unmodifiableSet(supportedPayloadTypes);
    }

    protected boolean addSupportedPayloadType(Class<? extends Payload> payloadType) {
        return supportedPayloadTypes.add(payloadType);
    }

    protected boolean removeSupportedPayloadType(Class<? extends Payload> payloadType) {
        return supportedPayloadTypes.remove(payloadType);
    }
}
