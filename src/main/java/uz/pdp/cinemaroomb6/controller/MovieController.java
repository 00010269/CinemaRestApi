package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.cinemaroomb6.dto.MovieDto;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.model.Movie;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.service.MovieService;

import java.util.Date;
import java.util.UUID;

import static uz.pdp.cinemaroomb6.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieService movieService;



//    @GetMapping
//    public ResponseEntity<Page<Movie>> getAllMovies(@RequestParam(value = "size",required = false,defaultValue = "5")Integer size,
//                                   @RequestParam(value = "page",required = false,defaultValue = "1")Integer page){
//        Page<Movie> movieList = movieService.getAllMovies(size,page);
//        ApiResponse res = new ApiResponse("success", true, movieList);
//
//    }

    @GetMapping
    public HttpEntity<?> getAllMovies(@RequestParam(value = "page",required = false,defaultValue = "0")Integer pageNumber,
                                                   @RequestParam(value = "size",required = false,defaultValue = DEFAULT_PAGE_SIZE)Integer pageSize){
        return new ResponseEntity<>(movieService.findAll(
                PageRequest.of(
                        pageNumber, pageSize
                )
        ), HttpStatus.OK);
    }




    @PostMapping
    public HttpEntity saveMovie(@RequestPart("movie")MovieDto movieDto,
                                @RequestPart(value = "file",required = false)MultipartFile multipartFile,
                                @RequestPart(value = "distributor",required = false) Distributor distributor,
                                @RequestPart(value = "date",required = false)Date date){
        ApiResponse res = new ApiResponse();
        Movie movie = movieService.saveMovie(movieDto, multipartFile,distributor,date);
        res.setMessage("success");
        res.setSuccess(true);
        res.setData(movie);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{movieId}")
    public HttpEntity deleteMovie(@PathVariable UUID movieId){
        movieService.deleteMovie(movieId);
        ApiResponse res = new ApiResponse("success",true);
        return ResponseEntity.ok(res);
    }
}
