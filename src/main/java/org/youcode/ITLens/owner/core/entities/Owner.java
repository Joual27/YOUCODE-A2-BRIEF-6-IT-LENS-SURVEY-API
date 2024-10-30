package org.youcode.ITLens.owner.core.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.youcode.ITLens.common.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Owner extends BaseEntity {
    @Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

