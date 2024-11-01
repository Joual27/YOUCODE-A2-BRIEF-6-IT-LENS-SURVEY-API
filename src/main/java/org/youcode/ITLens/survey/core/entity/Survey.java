package org.youcode.ITLens.survey.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.youcode.ITLens.common.BaseEntity;
import org.youcode.ITLens.owner.core.entities.Owner;


@Entity
public class Survey extends BaseEntity {
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne()
    private Owner owner;
    public Survey() {}

    public Survey(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
