package ma.youcode.surveyit.repository;

import ma.youcode.surveyit.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT c FROM Chapter c LEFT JOIN FETCH c.subchapters WHERE c.parentId IS NULL")
    List<Chapter> findAllByChapterIdIsNull();

}
