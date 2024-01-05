package io.github.guilhermemgoncalves.rest.resource;

import io.github.guilhermemgoncalves.domain.model.Posts;
import io.github.guilhermemgoncalves.domain.model.User;
import io.github.guilhermemgoncalves.infra.repository.PostRepository;
import io.github.guilhermemgoncalves.infra.repository.UserRepository;
import io.github.guilhermemgoncalves.rest.dto.post.CreatePostRequest;
import io.github.guilhermemgoncalves.rest.dto.post.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    UserRepository userRepository;

    @Inject
    PostRepository postRepository;
    
    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest postRequest){
        User user = userRepository.findById(userId);

        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Posts post = new Posts();
        post.setText(postRequest.getText());
        post.setUser(user);

        postRepository.persist(post);

        return Response.created(URI.create("/users/2/posts/1")).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId){
        User user = userRepository.findById(userId);

        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<Posts> query = postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending) ,user);
        List<Posts> list = query.list();

        var postResponseList = list.stream()
                .map(post -> PostResponse.fromEntity(post))
                .toList();

        return Response.ok().entity(postResponseList).build();
    }
}
