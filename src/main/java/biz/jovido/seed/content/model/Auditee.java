package biz.jovido.seed.content.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * @author Stephan Grundner
 */
public interface Auditee {

    @CreatedDate
    Date getCreated();

    @LastModifiedDate
    Date getLastModified();
}
