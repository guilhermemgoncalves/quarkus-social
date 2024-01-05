package io.github.guilhermemgoncalves.rest.dto.post;

import io.github.guilhermemgoncalves.domain.model.Posts;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Posts posts){
        var response = new PostResponse();
        response.setText(posts.getText());
        response.setDateTime(posts.getDateTime());

        return response;
    }

}
