package ma.youcode.surveyit.repository;

import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
