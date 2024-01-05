package io.github.guilhermemgoncalves.rest.resource;
import io.github.guilhermemgoncalves.domain.model.User;
import io.github.guilhermemgoncalves.infra.repository.UserRepository;
import io.github.guilhermemgoncalves.rest.dto.user.CreateUserRequest;
import io.github.guilhermemgoncalves.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;

    @Inject
    Validator validator;

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest){

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);

        if(!violations.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return responseError.withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        var user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        userRepository.persist(user);


        return Response.status(Response.Status.CREATED)
                .entity(user.getId())
                .build();
    }

    @GET
    public Response listAllusers(){

        PanacheQuery<User> users = userRepository.findAll();
        return Response.ok(users.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        User user = userRepository.findById(id);

        if(user != null){
            userRepository.delete(user);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest){
        User user = userRepository.findById(id);

        if(user != null){
            user.setAge(userRequest.getAge());
            user.setName(userRequest.getName());
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
