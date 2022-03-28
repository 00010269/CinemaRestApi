package uz.pdp.cinemaroomb6.repository.RestResource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.cinemaroomb6.model.SessionDate;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "session-date",path = "session-date")
public interface SessionDatesRepository extends JpaRepository<SessionDate, UUID> {
}
