package ma.youcode.surveyit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @Column(name = "owner_name")
    private String name;

    @OneToMany(mappedBy = "owner" , fetch = FetchType.EAGER)
    private List<Survey> surveys = new ArrayList<>();

}
