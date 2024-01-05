package io.github.guilhermemgoncalves.infra.repository;

import io.github.guilhermemgoncalves.domain.model.Followers;
import io.github.guilhermemgoncalves.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Followers> {

    public boolean follows(User follower, User user){
        Map<String, Object> params = Parameters.with("follower", follower)
                .and("user", user).map();

        PanacheQuery<Followers> followersPanacheQuery = find("follower =:follower and user =:user", params);
        Optional<Followers> result = followersPanacheQuery.firstResultOptional();

        return result.isPresent();

    }

    public List<Followers> findByUser(Long userId){
        PanacheQuery<Followers> query = find("user.id", userId);
        return query.list();
    }
}
