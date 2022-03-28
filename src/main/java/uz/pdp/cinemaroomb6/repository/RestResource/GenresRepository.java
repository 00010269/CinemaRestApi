package uz.pdp.cinemaroomb6.repository.RestResource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.cinemaroomb6.model.Genre;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "genres",path = "genres")
public interface GenresRepository extends JpaRepository<Genre, UUID> {

}
