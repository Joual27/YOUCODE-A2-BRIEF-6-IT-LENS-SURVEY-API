package org.youcode.ITLens.survey.core.entity;

import jakarta.persistence.*;
import org.youcode.ITLens.common.BaseEntity;
import org.youcode.ITLens.owner.core.entities.Owner;
import org.youcode.ITLens.surveyEdition.core.entity.SurveyEdition;

import java.util.List;


@Entity
public class Survey extends BaseEntity {
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    @OneToMany(mappedBy = "survey" , fetch = FetchType.EAGER , cascade = CascadeType.REFRESH)
    private List<SurveyEdition> editions;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<SurveyEdition> getEditions() {
        return editions;
    }

    public void setEditions(List<SurveyEdition> editions) {
        this.editions = editions;
    }
}
