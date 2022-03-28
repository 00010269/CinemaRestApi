package uz.pdp.cinemaroomb6.repository.RestResource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.cinemaroomb6.model.Country;

import java.util.UUID;



@RepositoryRestResource(collectionResourceRel = "countries",path = "countries")
public interface CountriesRepository extends JpaRepository<Country, UUID> {
}
