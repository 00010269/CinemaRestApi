package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cinemaroomb6.dto.RowDto;
import uz.pdp.cinemaroomb6.model.Row;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.RowProjection;
import uz.pdp.cinemaroomb6.service.RowService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/row")
public class RowController {

    @Autowired
    RowService rowService;

    @GetMapping("/{hallId}")
    public HttpEntity getAllRows(@PathVariable UUID hallId){
        List<RowProjection> hallRows=rowService.getHallRows(hallId);
        return ResponseEntity.ok(new ApiResponse("success",true,hallRows));
    }

    @PostMapping
    public HttpEntity<?> saveHallRow(@RequestPart("row") RowDto row){
        return  rowService.saveHallRow(row);
    }
}