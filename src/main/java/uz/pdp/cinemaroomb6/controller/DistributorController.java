package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.cinemaroomb6.dto.DistributorDto;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.DistributorProjection;
import uz.pdp.cinemaroomb6.service.DistributorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/distributors")
public class DistributorController {

    @Autowired
    DistributorService distributorService;

    @GetMapping
    public HttpEntity getAllDistributors() {
        List<Distributor> allDistributors = distributorService.getAllDistributors();
        ApiResponse res = new ApiResponse("success", true, allDistributors);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{movieId}")
    public HttpEntity getAllDistributorsByMovieId(@PathVariable(required = true) UUID movieId){
        List<DistributorProjection> allDistributorsByMovieId = distributorService.getAllDistributorsByMovieId(movieId);
        ApiResponse res = new ApiResponse("successfully",true,allDistributorsByMovieId);
        return ResponseEntity.ok(res);
    }


    @PostMapping
    public HttpEntity saveDistributor(@RequestPart("distributor") DistributorDto distributor,
                                      @RequestPart(value = "file",required = false)MultipartFile multipartFile){
        ApiResponse res = new ApiResponse();
        try {
            distributorService.saveDistributor(distributor,multipartFile);
            res.setMessage("success");
            res.setSuccess(true);
        }catch (Exception e){
            res.setSuccess(false);
            res.setMessage("failed");
        }
        return ResponseEntity.ok(res); // TODO: 3/14/2022 Question
    }
    @DeleteMapping("/{distributorId}")
    public void deleteDistributor(@PathVariable(required = true) UUID distributorId){
        distributorService.deleteDistributor(distributorId);
    }
}
