package ma.youcode.surveyit.enums;

import lombok.Getter;

@Getter
public enum QuestionType {
    SINGLE_CHOICE("Single choice option where only one answer can be selected"),
    MULTI_CHOICE("Multiple choice option allowing multiple answers");

    private final String desc;

    QuestionType(String desc) {
        this.desc = desc;
    }


}
