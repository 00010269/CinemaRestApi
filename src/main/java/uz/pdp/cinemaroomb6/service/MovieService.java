package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.cinemaroomb6.dto.MovieDto;
import uz.pdp.cinemaroomb6.model.Attachment;
import uz.pdp.cinemaroomb6.model.AttachmentContent;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.model.Movie;
import uz.pdp.cinemaroomb6.repository.AttachmentContentRepository;
import uz.pdp.cinemaroomb6.repository.AttachmentRepository;
import uz.pdp.cinemaroomb6.repository.MovieRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;


@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;


    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

//    public Page<Movie> getAllMovies(Integer page,Integer size) {
//        Pageable pageable=PageRequest.of(page,size);
//        return movieRepository.findAll(pageable);
//    }

    public Movie saveMovie(MovieDto movieDto, MultipartFile multipartFile, Distributor distributor, Date date) {
        Movie movie = new Movie();
        try {
            if (movieDto.getId() != null) {
                movie = movieRepository.getById(movieDto.getId());
            }
            movie.setTitle(movieDto.getTitle());
            movie.setDescription(movieDto.getDescription());
            movie.setDurationInMin(movieDto.getDurationInMin());
            movie.setTicketInitPrice(movieDto.getTicketInitPrice());
            movie.setTrailerVideoUrl(movieDto.getTrailerVideoUrl());
            movie.setReleaseDate(LocalDate.parse(movieDto.getReleaseDate()));
            movie.setBudget(movieDto.getBudget());
            movie.setDistributorShareInPercentage(movieDto.getDistributorShareInPercentage());
            movie.setMovieStatus(movieDto.getMovieStatus());
            if(distributor!=null){
                movie.setDistributor(distributor);
            }
            if (multipartFile != null) {
                Attachment attachment = new Attachment(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize());
                attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = null;
                attachmentContent = new AttachmentContent(multipartFile.getBytes(), attachment);
                attachmentContentRepository.save(attachmentContent);
                movie.setPosterImg(attachment);
            }
            movieRepository.save(movie);
            return movie;
        }catch(Exception e){
            e.printStackTrace();
            return movie;
        }
    }


    public void deleteMovie(UUID movieId) {
        movieRepository.deleteById(movieId);
    }


    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }
}
