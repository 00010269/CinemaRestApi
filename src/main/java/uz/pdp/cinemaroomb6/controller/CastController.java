package uz.pdp.cinemaroomb6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.cinemaroomb6.dto.CastDto;
import uz.pdp.cinemaroomb6.dto.DistributorDto;
import uz.pdp.cinemaroomb6.model.Cast;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.CastProjection;
import uz.pdp.cinemaroomb6.service.CastService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/casts")
public class CastController {



    @Autowired
    CastService castService;


    @GetMapping
    public HttpEntity getAllCasts() {
        List<Cast> allCasts = castService.getAllCasts();
        ApiResponse res = new ApiResponse("success", true, allCasts);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{movieId}")
    public HttpEntity getAllCastsByMovieId(@PathVariable UUID movieId) {
        List<CastProjection> allCastsByMovieId = castService.getAllCastsByMovieId(movieId);
        ApiResponse res = new ApiResponse("success", true, allCastsByMovieId);
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public void saveCast(@RequestPart("cast") CastDto castDto, @RequestPart(value = "file",required = false) MultipartFile multipartFile){
         castService.saveCast(castDto,multipartFile);
    }

    @DeleteMapping("/{castId}")
    public void deleteCast(@PathVariable UUID castId){
        castService.deleteCast(castId);
    }
}
