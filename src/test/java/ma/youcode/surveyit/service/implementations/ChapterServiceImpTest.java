package ma.youcode.surveyit.service.implementations;

import ma.youcode.surveyit.dto.request.chapter.ChapterCreateDTO;
import ma.youcode.surveyit.dto.request.chapter.ChapterUpdateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.dto.response.edition.EditionCustomDTO;
import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.ChapterMapper;
import ma.youcode.surveyit.repository.ChapterRepository;
import ma.youcode.surveyit.service.interfaces.EditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChapterServiceImpTest {

    @Mock
    private ChapterRepository repository;

    @Mock
    private ChapterMapper mapper;

    @Mock
    private EditionService editionService;

    @InjectMocks
    private ChapterServiceImp chapterService;

    private ChapterCreateDTO chapterCreateDTO;
    private ChapterUpdateDTO chapterUpdateDTO;
    private ChapterResponseDTO chapterResponseDTO;
    private Chapter chapter;
    private Edition edition;
    private final Long chapterId = 1L;
    private final Long editionId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Setting up test data
        edition = new Edition();
        edition.setId(editionId);

        chapterCreateDTO = new ChapterCreateDTO("Sample Chapter");
        chapterUpdateDTO = new ChapterUpdateDTO("Updated Chapter" , 1L);

        chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setTitle("Sample Chapter");
        EditionCustomDTO editionCustomDTO = new EditionCustomDTO(1L , LocalDateTime.now() , LocalDateTime.now());
        List<ChapterEmbeddedDTO> subchapters = new ArrayList<>();
        chapterResponseDTO = new ChapterResponseDTO(chapterId, "Sample Chapter", editionCustomDTO , subchapters);
    }

    @Test
    void shouldCreateChapterSuccessfullyWhenEditionExists() {
        // Arrange
        when(editionService.getEditionEntity(editionId)).thenReturn(edition);
        when(mapper.toChapter(chapterCreateDTO)).thenReturn(chapter);
        when(repository.save(chapter)).thenReturn(chapter);
        when(mapper.toResponseDTO(chapter)).thenReturn(chapterResponseDTO);

        // Act
        ChapterResponseDTO result = chapterService.createChapter(chapterCreateDTO, editionId);

        // Assert
        assertNotNull(result);
        assertEquals(chapterResponseDTO, result);
        verify(editionService).getEditionEntity(editionId);
        verify(repository).save(chapter);
        verify(mapper).toResponseDTO(chapter);
    }

    @Test
    void shouldCreateSubchapterSuccessfullyWhenEditionExists() {
        // Arrange
        Chapter subchapter = new Chapter();
        when(mapper.toChapter(chapterCreateDTO)).thenReturn(subchapter);
        when(repository.findById(chapterId)).thenReturn(Optional.of(chapter));
        subchapter.setParentId(chapterId);
        subchapter.setEdition(null);
        when(repository.save(subchapter)).thenReturn(subchapter);
        when(mapper.toResponseDTO(subchapter)).thenReturn(chapterResponseDTO);

        // Act
        ChapterResponseDTO result = chapterService.createChapter(chapterCreateDTO, chapterId);

        // Assert
        assertNotNull(result);
        assertEquals(chapterResponseDTO, result);

        // verify interaction
        verify(repository).findById(chapterId);
        verify(repository).save(subchapter);
        verify(mapper).toResponseDTO(subchapter);
    }


    @Test
    void shouldCreateChapterSuccessfullyWhenNoEditionExists() {
        // Arrange
        when(repository.findById(editionId)).thenReturn(Optional.empty());
        when(editionService.getEditionEntity(editionId)).thenReturn(edition);
        when(mapper.toChapter(chapterCreateDTO)).thenReturn(chapter);
        when(repository.save(chapter)).thenReturn(chapter);
        when(mapper.toResponseDTO(chapter)).thenReturn(chapterResponseDTO);

        // Act
        ChapterResponseDTO result = chapterService.createChapter(chapterCreateDTO, editionId);

        // Assert
        assertNotNull(result);
        assertEquals(chapterResponseDTO, result);
        verify(editionService).getEditionEntity(editionId);
        verify(repository).save(chapter);
        verify(mapper).toResponseDTO(chapter);
    }

    @Test
    void shouldEditChapterSuccessfully() {
        // Arrange
        when(repository.findById(chapterId)).thenReturn(Optional.of(chapter));
        when(repository.save(chapter)).thenReturn(chapter);
        when(mapper.toResponseDTO(chapter)).thenReturn(chapterResponseDTO);

        // Act
        ChapterResponseDTO result = chapterService.editChapter(chapterUpdateDTO, chapterId);

        // Assert
        assertNotNull(result);
        assertEquals(chapterResponseDTO, result);
        verify(repository).save(chapter);
        verify(mapper).toResponseDTO(chapter);
    }

    @Test
    void shouldDeleteChapterSuccessfully() {
        // Arrange
        doNothing().when(repository).deleteById(chapterId);

        // Act
        chapterService.deleteChapter(chapterId);

        // Assert
        verify(repository).deleteById(chapterId);
    }

    @Test
    void shouldReturnPageOfChaptersWhenGetAllChaptersCalled() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Chapter> chaptersPage = new PageImpl<>(Arrays.asList(chapter, chapter));
        when(repository.findAll(pageable)).thenReturn(chaptersPage);
        when(mapper.toResponseDTO(any(Chapter.class))).thenReturn(chapterResponseDTO);

        // Act
        Page<ChapterResponseDTO> result = chapterService.getAllChapters(0, 10);

        // Assert
        assertNotNull(result);
        verify(repository).findAll(pageable);
    }

    @Test
    void shouldReturnChapterResponseDTOWhenGetChapterById() {
        // Arrange
        when(repository.findById(chapterId)).thenReturn(Optional.of(chapter));
        when(mapper.toResponseDTO(chapter)).thenReturn(chapterResponseDTO);

        // Act
        ChapterResponseDTO result = chapterService.getChapter(chapterId);

        // Assert
        assertNotNull(result);
        assertEquals(chapterResponseDTO, result);
        verify(repository).findById(chapterId);
        verify(mapper).toResponseDTO(chapter);
    }


    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindChapterByIdFails() {
        // Arrange
        when(repository.findById(chapterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chapterService.findChapterById(chapterId));

        verify(repository).findById(chapterId);
    }
}
