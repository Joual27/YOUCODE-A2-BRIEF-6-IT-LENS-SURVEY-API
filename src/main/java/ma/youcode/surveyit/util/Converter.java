package ma.youcode.surveyit.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.participate.ParticipateDTO;
import java.io.IOException;

@AllArgsConstructor
public abstract class Converter {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object process(String request) {

        try {
            JsonNode jsonNode = objectMapper.readTree(request);
            if (jsonNode.has("responses")) {
                return objectMapper.treeToValue(jsonNode , ParticipateDTO.Responses.class);
            }else {
                return objectMapper.treeToValue(jsonNode , ParticipateDTO.Response.class);
            }
        }catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
