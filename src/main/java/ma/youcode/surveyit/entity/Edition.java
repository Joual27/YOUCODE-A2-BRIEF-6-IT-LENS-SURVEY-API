package ma.youcode.surveyit.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "editions")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edition_id")
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "edition_year")
    private int year;

    @OneToMany(mappedBy = "edition" , fetch = FetchType.EAGER)
    private List<Chapter> chapters;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

}
