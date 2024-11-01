package org.youcode.ITLens.owner.core.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.youcode.ITLens.common.BaseEntity;
import org.youcode.ITLens.survey.core.entity.Survey;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Owner extends BaseEntity {
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "owner" , fetch = FetchType.EAGER)
    private List<Survey> surveys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
}

