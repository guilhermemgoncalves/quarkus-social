package io.github.guilhermemgoncalves.rest.resource;

import io.github.guilhermemgoncalves.domain.model.Followers;
import io.github.guilhermemgoncalves.domain.model.User;
import io.github.guilhermemgoncalves.infra.repository.FollowerRepository;
import io.github.guilhermemgoncalves.infra.repository.UserRepository;
import io.github.guilhermemgoncalves.rest.dto.followers.FollowPerUserResponse;
import io.github.guilhermemgoncalves.rest.dto.followers.FollowRequest;
import io.github.guilhermemgoncalves.rest.dto.followers.FollowerResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    @Inject
    FollowerRepository followerRepository;

    @Inject
    UserRepository userRepository;

    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId, FollowRequest followRequest)
    {
        User user = userRepository.findById(userId);

        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User follower = userRepository.findById(followRequest.getFollower_id());

        boolean follows = followerRepository.follows(follower, user);

        if(!follows){
            var entity = new Followers();
            entity.setUser(user);
            entity.setFollower(follower);

            followerRepository.persist(entity);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId){

        List<Followers> followers = followerRepository.findByUser(userId);

        FollowPerUserResponse responseObject = new FollowPerUserResponse();
        responseObject.setCount(followers.size());

        List<FollowerResponse> followersResponse = followers.stream().map(FollowerResponse::new).collect(Collectors.toList());
        responseObject.setContent(followersResponse);

        return Response.ok().entity(responseObject).build();

    }


}
