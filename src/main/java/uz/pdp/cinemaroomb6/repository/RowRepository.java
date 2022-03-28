package uz.pdp.cinemaroomb6.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.Row;
import uz.pdp.cinemaroomb6.projection.RowProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface RowRepository extends JpaRepository<Row, UUID> {
    Row findByNumber(Integer number);

//    @Query(nativeQuery = true,value = "select cast(r.id as varchar) as id,r.number from rows r\n" +
//            "where r.hall_id='3c78da3c-293f-4346-860a-f01dd06cd7fb'")
//    Page<RowProjection>findAllByMovieId(Pageable pageable,UUID movieId);

    List<RowProjection>findByHallId(UUID hallId);


}
