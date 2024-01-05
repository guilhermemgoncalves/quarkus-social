package io.github.guilhermemgoncalves.rest.dto.followers;

import io.github.guilhermemgoncalves.domain.model.Followers;
import lombok.Data;

@Data
public class FollowerResponse {
    private Long id;
    private String name;

    public FollowerResponse() {
    }

    public FollowerResponse(Followers followers){
        this(followers.getId(), followers.getFollower().getName());
    }

    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
