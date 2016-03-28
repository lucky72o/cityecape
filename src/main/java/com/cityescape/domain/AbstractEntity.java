package com.cityescape.domain;

import com.cityescape.util.DateTimeHelper;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.io.Serializable;

public abstract class AbstractEntity<T extends Serializable> implements PersistentEntity<T> {

    @Column(name = "CREATED_DATE", updatable = false, nullable = false)
    private DateTime createdDate;
    @Column(name = "UPDATED_DATE", nullable = false)
    private DateTime updatedDate;

    @Transient
    public boolean isNew() {
        return getId() == null;
    }

    @PrePersist
    protected void onCreate() {
        setCreatedDate(DateTimeHelper.getCurrentDateTime());
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdatedDate(DateTimeHelper.getCurrentDateTime());
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public DateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(DateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
