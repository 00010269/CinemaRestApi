//package uz.pdp.cinemaroomb6.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpEntity;
//import org.springframework.stereotype.Service;
//import uz.pdp.cinemaroomb6.dto.MovieDto;
//import uz.pdp.cinemaroomb6.model.Movie;
//import uz.pdp.cinemaroomb6.projection.MovieProjection;
//import uz.pdp.cinemaroomb6.repository.MovieRepository;
//import uz.pdp.cinemaroomb6.service.interfaces.MovieService;
//
//import java.util.UUID;
//
//
//@Service
//public class MovieServiceImpl implements MovieService {
//
//    @Autowired
//    MovieRepository movieRepository;
//
//
//    @Override
//    public HttpEntity getAllMovies(int size, int page, String search, String sort, boolean direction) {
//        Pageable pageable= PageRequest.of(size,page,
//                direction? Sort.Direction.ASC:Sort.Direction.DESC,
//                sort);
//        Page<MovieProjection> all = movieRepository.findAllMoviesPage(pageable);
//    }
//
//    @Override
//    public HttpEntity getMovieById(UUID id) {
//        return null;
//    }
//
//    @Override
//    public HttpEntity saveMovie(MovieDto movieDto) {
//        return null;
//    }
//
//    @Override
//    public HttpEntity deleteMovieById(UUID id) {
//        return null;
//    }
//}
