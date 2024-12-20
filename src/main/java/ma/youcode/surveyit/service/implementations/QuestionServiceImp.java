package ma.youcode.surveyit.service.implementations;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.question.QuestionCreateDTO;
import ma.youcode.surveyit.dto.request.question.QuestionUpdateDTO;
import ma.youcode.surveyit.dto.response.question.QuestionResponseDTO;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.QuestionMapper;
import ma.youcode.surveyit.repository.QuestionRepository;
import ma.youcode.surveyit.service.interfaces.ChapterService;
import ma.youcode.surveyit.service.interfaces.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionServiceImp implements QuestionService {

    private final QuestionRepository repository;
    private final QuestionMapper mapper;
    private final ChapterService chapterService;

    @Override
    public QuestionResponseDTO createQuestion(QuestionCreateDTO dto, Long subchapterId) {

        Question toQuestion = mapper.toQuestion(dto);
        Chapter subchapter = chapterService.findChapterById(subchapterId);

        if (!subchapter.getSubchapters().isEmpty() || subchapter.getParentId() == null) {
            throw new IllegalArgumentException("cannot added question because is not subchapter");
        }

        toQuestion.setSubchapter(subchapter);
        return mapper.toResponseDTO(repository.save(toQuestion));

    }


    @Override
    public QuestionResponseDTO editQuestion(QuestionUpdateDTO dto, Long id) {

        Question question = repository.findById(id).get();
        question.setText(dto.text());
        return mapper.toResponseDTO(repository.save(question));
    }

    @Override
    public void deleteQuestion(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<QuestionResponseDTO> getAllQuestions(int page , int size) {

        Pageable pageable = PageRequest.of(page , size);
        Page<Question> questionsPage = repository.findAll(pageable);

        return questionsPage.map(mapper::toResponseDTO);

    }

    @Override
    public QuestionResponseDTO getQuestion(Long id) {
        Question question = repository.findById(id).get();
        return mapper.toResponseDTO(question);
    }

    @Override
    public Question findQuestionById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Question not found."));
    }

    @Override
    public void editAnswerCount(Question question) {
        repository.save(question);
    }
}
