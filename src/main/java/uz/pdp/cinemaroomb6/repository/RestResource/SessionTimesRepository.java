package uz.pdp.cinemaroomb6.repository.RestResource;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.cinemaroomb6.model.SessionTime;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "session-time",path = "session-time")
public interface SessionTimesRepository extends JpaRepository<SessionTime, UUID> {
}
