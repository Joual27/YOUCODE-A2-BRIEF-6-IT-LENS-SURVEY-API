package org.youcode.ITLens.surveyEdition.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.youcode.ITLens.common.BaseEntity;
import org.youcode.ITLens.survey.core.entity.Survey;

import java.time.LocalDate;

@Entity
public class SurveyEdition extends BaseEntity {
    private LocalDate creationDate;
    private LocalDate startDate;
    private int year;

    @ManyToOne
    @JoinColumn(name = "SURVEY_ID")
    private Survey survey;

    public SurveyEdition() {}

    public SurveyEdition(LocalDate creationDate, LocalDate startDate, int year, Survey survey) {
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.year = year;
        this.survey = survey;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
