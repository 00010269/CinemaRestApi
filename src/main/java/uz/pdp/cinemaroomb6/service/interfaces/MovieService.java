package uz.pdp.cinemaroomb6.service.interfaces;

import org.springframework.http.HttpEntity;
import uz.pdp.cinemaroomb6.dto.MovieDto;

import java.util.UUID;

public interface MovieService {

    HttpEntity getAllMovies(int size,int page,String search,String sort, boolean direction);
    HttpEntity getMovieById(UUID id);
    HttpEntity saveMovie(MovieDto movieDto);
    HttpEntity deleteMovieById(UUID id);
}
