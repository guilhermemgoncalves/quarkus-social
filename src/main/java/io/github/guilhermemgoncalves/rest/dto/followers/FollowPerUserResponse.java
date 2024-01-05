package io.github.guilhermemgoncalves.rest.dto.followers;

import lombok.Data;

import java.util.List;

@Data
public class FollowPerUserResponse {
    private Integer count;
    private List<FollowerResponse> content;
}
