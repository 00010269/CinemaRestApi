package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.dto.RowDto;
import uz.pdp.cinemaroomb6.model.Hall;
import uz.pdp.cinemaroomb6.model.Row;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.RowProjection;
import uz.pdp.cinemaroomb6.repository.HallRepository;
import uz.pdp.cinemaroomb6.repository.RowRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RowService {

    @Autowired
    RowRepository rowRepository;

    @Autowired
    HallRepository hallRepository;

    public List<RowProjection> getHallRows(UUID hallId) {
        return rowRepository.findByHallId(hallId);
    }

    public HttpEntity<?> saveHallRow(RowDto rowDto) {
        Row row = new Row();
        try {
            if (rowDto.getId() != null) {
                row.setId(rowDto.getId());
            }
            Hall hallById = hallRepository.getById(rowDto.getHall_id());
            row.setHall(hallById);
            row.setNumber(rowDto.getNumber());
            return ResponseEntity.ok(
                    new ApiResponse("success", true) //todo Question
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }
    }
}