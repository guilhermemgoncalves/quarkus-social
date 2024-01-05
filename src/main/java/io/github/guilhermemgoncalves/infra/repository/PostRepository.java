package io.github.guilhermemgoncalves.infra.repository;

import io.github.guilhermemgoncalves.domain.model.Posts;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Posts> {


}
