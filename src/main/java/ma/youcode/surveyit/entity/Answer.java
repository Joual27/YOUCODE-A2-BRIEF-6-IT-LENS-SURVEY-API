package ma.youcode.surveyit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text")
    private String text;

    @JoinColumn(name = "selection_count")
    private int selectionCount;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;



}
