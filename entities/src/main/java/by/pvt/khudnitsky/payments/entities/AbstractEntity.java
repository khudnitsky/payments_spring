/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Describes the abstract entity <b>AbstractEntity</b>
 * @author khudnitsky
 * @version 1.0
 *
 */

@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class AbstractEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    protected Long id;

    public AbstractEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity abstractEntity = (AbstractEntity) o;

        return id != null ? id.equals(abstractEntity.id) : abstractEntity.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AbstractEntity [id=" + id + "]";
    }
}
