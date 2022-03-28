package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cinemaroomb6.dto.HallDto;
import uz.pdp.cinemaroomb6.model.Hall;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.service.HallService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hall")
public class HallController {

    @Autowired
    HallService hallService;

    @GetMapping
    public HttpEntity getAllHalls() {
        List<Hall> allHalls = hallService.getAllHalls();
        ApiResponse res = new ApiResponse("success", true, allHalls);
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public HttpEntity saveHall(@RequestPart("hall") HallDto hallDto){
        ApiResponse res=new ApiResponse();
        Hall hall = hallService.saveHall(hallDto);
        res.setData(hall);
        res.setMessage("Success");
        res.setSuccess(true);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{hallId}")
    public HttpEntity deleteHall(@PathVariable UUID hallId){
        hallService.deleteHall(hallId);
        return ResponseEntity.ok(new ApiResponse("success",true));
    }

}
