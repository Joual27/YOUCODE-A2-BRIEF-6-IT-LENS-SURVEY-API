package ma.youcode.surveyit.repository;

import jakarta.transaction.Transactional;
import ma.youcode.surveyit.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Question q WHERE q.id = :id")
    void delete(@Param("id") Long id);
}
