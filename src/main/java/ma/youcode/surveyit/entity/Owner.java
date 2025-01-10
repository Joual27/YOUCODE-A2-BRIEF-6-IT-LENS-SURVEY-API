package ma.youcode.surveyit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "OWNERS")
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends User{
    @Column(name = "OWNER_NAME")
    private String name;

    @OneToMany(mappedBy = "owner" , fetch = FetchType.EAGER)
    private List<Survey> surveys = new ArrayList<>();

    @Override
    public String getUserRole() {
        return "ROLE_OWNER";
    }
}
