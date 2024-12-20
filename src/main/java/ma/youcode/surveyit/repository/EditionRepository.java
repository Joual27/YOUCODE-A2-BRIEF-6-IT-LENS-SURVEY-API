package ma.youcode.surveyit.repository;

import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {
//    @Query("SELECT e FROM Edition e " +
//            "LEFT JOIN FETCH e.chapters c " +
//            "LEFT JOIN FETCH c.subchapters s " +
//            "LEFT JOIN FETCH c.questions q")
//    List<Edition> findAllWithDetails();
}
