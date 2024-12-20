package ma.youcode.surveyit.service.interfaces;

import ma.youcode.surveyit.dto.request.chapter.ChapterCreateDTO;
import ma.youcode.surveyit.dto.request.chapter.ChapterUpdateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.entity.Chapter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChapterService {
    Page<ChapterResponseDTO> getAllChapters(int page , int size);
    ChapterResponseDTO getChapter(Long id);
    ChapterResponseDTO createChapter(ChapterCreateDTO dto , Long editionId);
    ChapterResponseDTO editChapter(ChapterUpdateDTO dto , Long id);
    void deleteChapter(Long id);
    Chapter findChapterById(Long chapterId);


}
