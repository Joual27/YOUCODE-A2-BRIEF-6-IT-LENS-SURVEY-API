package ma.youcode.surveyit.service.implementations;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.answer.AnswerCreateDTO;
import ma.youcode.surveyit.dto.request.answer.AnswerUpdateDTO;
import ma.youcode.surveyit.dto.response.answer.AnswerResponseDTO;
import ma.youcode.surveyit.entity.Answer;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.AnswerMapper;
import ma.youcode.surveyit.repository.AnswerRepository;
import ma.youcode.surveyit.service.interfaces.AnswerService;
import ma.youcode.surveyit.service.interfaces.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImp implements AnswerService {

    private final AnswerRepository repository;
    private final AnswerMapper mapper;
    private final QuestionService questionService;

    @Override
    public AnswerResponseDTO createAnswer(AnswerCreateDTO dto) {

        Question question = questionService.findQuestionById(dto.questionId());

        Answer toAnswer = mapper.toAnswer(dto);
        toAnswer.setQuestion(question);

        return mapper.toResponseDTO(repository.save(toAnswer));
    }


    @Override
    public AnswerResponseDTO editAnswer(AnswerUpdateDTO dto, Long id) {

        Answer toAnswer = mapper.toAnswer(dto);
        toAnswer.setId(id);
        return mapper.toResponseDTO(repository.save(toAnswer));

    }

    @Override
    public void deleteAnswer(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<AnswerResponseDTO> getAllAnswers(int page , int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Answer> answersPage = repository.findAll(pageable);

            return answersPage.map(mapper::toResponseDTO);
    }

    @Override
    public AnswerResponseDTO getAnswer(Long id) {
        Answer answer = repository.findById(id).get();
        return mapper.toResponseDTO(answer);
    }
    @Override
    public Answer findAnswerById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Answer not found."));
    }

    @Override
    public void editSelectionCount(Answer answer) {
        repository.save(answer);
    }
}
