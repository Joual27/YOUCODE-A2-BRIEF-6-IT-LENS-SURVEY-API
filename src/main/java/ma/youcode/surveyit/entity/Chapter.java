package ma.youcode.surveyit.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long id;

    @Column(name = "chapter_title")
    private String title;

    @Column(name = "parent_id")
    Long parentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Chapter parent;

    @OneToMany(mappedBy = "parent" ,cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Chapter> subchapters = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "edition_id")
    private Edition edition;

    @OneToMany(mappedBy = "subchapter" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();


}
