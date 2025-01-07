package ma.youcode.surveyit.service.implementations;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.chapter.ChapterCreateDTO;
import ma.youcode.surveyit.dto.request.chapter.ChapterUpdateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.ChapterMapper;
import ma.youcode.surveyit.repository.ChapterRepository;
import ma.youcode.surveyit.service.interfaces.EditionService;
import ma.youcode.surveyit.service.interfaces.ChapterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChapterServiceImp implements ChapterService {

    private final ChapterRepository repository;
    private final ChapterMapper mapper;
    private final EditionService editionService;

    @Override
    public ChapterResponseDTO createChapter(ChapterCreateDTO dto, Long id) {
        Chapter toChapter = mapper.toChapter(dto);
        Optional<Chapter> optionalChapter = repository.findById(id);

        if (optionalChapter.isPresent()) {
            toChapter.setParentId(id);
            toChapter.setEdition(null);
        } else {
            Edition edition = editionService.getEditionEntity(id);
            toChapter.setEdition(edition);
        }

        return mapper.toResponseDTO(repository.save(toChapter));
    }


    @Override
    public ChapterResponseDTO editChapter(ChapterUpdateDTO dto, Long id) {

        Chapter chapter = repository.findById(id).get();
        chapter.setTitle(dto.title());
        return mapper.toResponseDTO(repository.save(chapter));

    }

    @Override
    public void deleteChapter(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<ChapterResponseDTO> getAllChapters(int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Chapter> chaptersPage = repository.findAll(pageable);

        return chaptersPage.map(mapper::toResponseDTO);

    }

    @Override
    public ChapterResponseDTO getChapter(Long id) {
        Chapter chapter = repository.findById(id).get();
        return mapper.toResponseDTO(chapter);
    }

    @Override
    public Chapter findChapterById(Long chapterId) {
        return repository.findById(chapterId).orElseThrow(() -> new EntityNotFoundException("Chapter not found."));
    }
}
