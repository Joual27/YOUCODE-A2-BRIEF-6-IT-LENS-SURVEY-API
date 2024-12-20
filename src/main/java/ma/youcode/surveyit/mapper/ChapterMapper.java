package ma.youcode.surveyit.mapper;

import ma.youcode.surveyit.dto.request.chapter.ChapterCreateDTO;
import ma.youcode.surveyit.dto.request.chapter.ChapterUpdateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterEmbeddedDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    ChapterResponseDTO toResponseDTO(Chapter chapter);
    @Mapping(target = "subchapters", expression = "java(toEmbeddedDTOs(chapter.getSubchapters()))")
    ChapterEmbeddedDTO toEmbeddedDTO(Chapter chapter);

    Chapter toChapter(ChapterCreateDTO dto);
    Chapter toChapter(ChapterUpdateDTO dto);

    default List<ChapterEmbeddedDTO> toEmbeddedDTOs(List<Chapter> chapters) {
        return chapters.stream()
                .map(this::toEmbeddedDTO)
                .distinct()
                .collect(Collectors.toList());
    }


}
