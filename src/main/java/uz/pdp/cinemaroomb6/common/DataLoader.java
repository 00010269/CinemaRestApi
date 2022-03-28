package uz.pdp.cinemaroomb6.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.cinemaroomb6.model.*;
import uz.pdp.cinemaroomb6.model.enums.CastType;
import uz.pdp.cinemaroomb6.model.enums.MovieStatus;
import uz.pdp.cinemaroomb6.model.enums.TicketStatus;
import uz.pdp.cinemaroomb6.repository.*;
import uz.pdp.cinemaroomb6.repository.RestResource.GenresRepository;
import uz.pdp.cinemaroomb6.repository.RestResource.SessionDatesRepository;
import uz.pdp.cinemaroomb6.repository.RestResource.SessionTimesRepository;
import uz.pdp.cinemaroomb6.service.GenerateQRService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    String initMode;


    @Autowired
    CastRepository castRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    RowRepository rowRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    PriceCategoryRepository priceCategoryRepo;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenresRepository genresRepository;

    @Autowired
    DistributorRepository distributorRepository;

    @Autowired
    SessionTimesRepository sessionTimesRepository;

    @Autowired
    SessionDatesRepository sessionDatesRepository;

    @Autowired
    MovieSessionRepository movieSessionRepository;

    @Autowired
    MovieAnnouncementRepository movieAnnouncementRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {

            // ZALLARNI DBGA QO'SHISH
            List<Hall> hallList = new ArrayList<>(Arrays.asList(
                    new Hall("Zal 1"),
                    new Hall("Zal 2", 5.0),
                    new Hall("Zal 3"),
                    new Hall("Zal 4")
            ));
            hallRepository.saveAll(hallList);


            //PRICE CATEGORIES DBGA QO'SHISH
//            PriceCategory cat1 = new PriceCategory("KATEGORIYA 1", 10.0, "#FF2244");
            PriceCategory cat1 = priceCategoryRepo.save(new PriceCategory("KATEGORIYA 1", 10.0, "#FF2244"));
            PriceCategory cat2 = priceCategoryRepo.save(new PriceCategory("KATEGORIYA 2", 8.0, "#AA0033"));

            // JOYLARNI DBGA QO'SHISH (QATORLARINI HAM BIRGALIKDA)


            Hall hall1 = hallRepository.findByName("Zal 1");
            Hall hall2 = hallRepository.findByName("Zal 2");

            Row row1 = rowRepository.save(new Row(1, hall1));
            Row row2 = rowRepository.save(new Row(2, hall1));
            Row row3 = rowRepository.save(new Row(3, hall1));
            Row row12 = rowRepository.save(new Row(1, hall2));

            List<Seat> seatList = new ArrayList<>(Arrays.asList(
                    new Seat(1, row1, cat2),
                    new Seat(2, row1, cat2),
                    new Seat(3, row1, cat2),
                    new Seat(4, row1, cat1),
                    new Seat(5, row1, cat1),
                    new Seat(6, row1, cat1),
                    new Seat(7, row1, cat1),
                    new Seat(8, row1, cat2),
                    new Seat(9, row1, cat2),
                    new Seat(10, row1, cat2),
                    new Seat(1, row2, cat2),
                    new Seat(2, row2, cat2),
                    new Seat(3, row2, cat2),
                    new Seat(4, row2, cat1),
                    new Seat(5, row2, cat1),
                    new Seat(6, row2, cat1),
                    new Seat(7, row2, cat1),
                    new Seat(8, row2, cat2),
                    new Seat(9, row2, cat2),
                    new Seat(10, row2, cat2),
                    new Seat(1, row3, cat2),
                    new Seat(2, row3, cat2),
                    new Seat(3, row3, cat2),
                    new Seat(4, row3, cat1),
                    new Seat(5, row3, cat1),
                    new Seat(6, row3, cat1),
                    new Seat(7, row3, cat1),
                    new Seat(8, row3, cat2),
                    new Seat(9, row3, cat2),
                    new Seat(10, row3, cat2),
                    new Seat(1, row12, cat2),
                    new Seat(2, row12, cat2),
                    new Seat(3, row12, cat2),
                    new Seat(4, row12, cat1),
                    new Seat(5, row12, cat1),
                    new Seat(6, row12, cat1),
                    new Seat(7, row12, cat1),
                    new Seat(8, row12, cat2),
                    new Seat(9, row12, cat2),
                    new Seat(10, row12, cat2)

            ));

            List<Seat> savedSeats = seatRepository.saveAll(seatList);



            List<Genre>genreList=new ArrayList<>(Arrays.asList(
                    new Genre("Genre 1"),
                    new Genre("Genre 2"),
                    new Genre("Genre 3"),
                    new Genre("Genre 4")
            ));

            List<Genre> savedGenreList = genresRepository.saveAll(genreList);

            List<Cast> castList = new ArrayList<>(Arrays.asList(
                    new Cast("Lazizbek", null, CastType.CAST_ACTOR),
                    new Cast("Abror", null, CastType.CAST_ACTOR),
                    new Cast("Avaz", null, CastType.CAST_ACTOR),
                    new Cast("Eldor", null, CastType.CAST_ACTOR)
            ));
            List<Cast> savedCastList = castRepository.saveAll(castList);

            List<Movie>movieList= new ArrayList<>(Arrays.asList(
                    new Movie("Movie1","Description1",100,100000,"trailervideo1",null, LocalDate.now().plusDays(20),10000.1,null,1.2,savedCastList,savedGenreList, MovieStatus.MOVIE_SOON),
                    new Movie("Movie2","Description2",110,110000,"trailervideo2",null, LocalDate.now().plusDays(30),10000.2,null,2.2,savedCastList,savedGenreList, MovieStatus.MOVIE_SOON),
                    new Movie("Movie3","Description3",120,120000,"trailervideo3",null, LocalDate.now().plusDays(40),10000.3,null,3.2,savedCastList,savedGenreList, MovieStatus.MOVIE_SOON),
                    new Movie("Movie4","Description4",130,130000,"trailervideo4",null, LocalDate.now().plusDays(50),10000.4,null,4.2,savedCastList,savedGenreList, MovieStatus.MOVIE_SOON),
                    new Movie("Movie5","Description5",140,140000,"trailervideo5",null, LocalDate.now().plusDays(60),10000.5,null,5.2,savedCastList,savedGenreList, MovieStatus.MOVIE_SOON)
            ));
            List<Movie> savedMovies = movieRepository.saveAll(movieList);

            List<Distributor>distributorList=new ArrayList<>(Arrays.asList(
                    new Distributor("Distributor1","DistrDesc1",null),
                    new Distributor("Distributor2","DistrDesc2",null),
                    new Distributor("Distributor3","DistrDesc3",null),
                    new Distributor("Distributor4","DistrDesc4",null)
            ));
            List<Distributor> savedDistributors = distributorRepository.saveAll(distributorList);

            List<SessionTime> sessionTimeList=new ArrayList<>(Arrays.asList(
                    new SessionTime(LocalTime.now().plusHours(1),1),
                    new SessionTime(LocalTime.now().plusHours(3),2),
                    new SessionTime(LocalTime.now().plusHours(5),3),
                    new SessionTime(LocalTime.now().plusHours(7),4),
                    new SessionTime(LocalTime.now().plusHours(9),5)
            ));
            List<SessionTime> savedSessionTimeList = sessionTimesRepository.saveAll(sessionTimeList);


            List<SessionDate>sessionDateList=new ArrayList<>(Arrays.asList(
                    new SessionDate(LocalDate.now().plusDays(10)),
                    new SessionDate(LocalDate.now().plusDays(20)),
                    new SessionDate(LocalDate.now().plusDays(30)),
                    new SessionDate(LocalDate.now().plusDays(40)),
                    new SessionDate(LocalDate.now().plusDays(50))
            ));
            List<SessionDate> savedSessionDateList = sessionDatesRepository.saveAll(sessionDateList);


            List<MovieAnnouncement>movieAnnouncements=new ArrayList<>(Arrays.asList(
                    new MovieAnnouncement(savedMovies.get(0),true),
                    new MovieAnnouncement(savedMovies.get(1),true),
                    new MovieAnnouncement(savedMovies.get(2),true),
                    new MovieAnnouncement(savedMovies.get(3),true),
                    new MovieAnnouncement(savedMovies.get(4),true)
            ));
            List<MovieAnnouncement> savedMovieAnnouncements = movieAnnouncementRepository.saveAll(movieAnnouncements);


            List<MovieSession>movieSessionList=new ArrayList<>(Arrays.asList(
                    new MovieSession(hall1,savedSessionDateList.get(0),savedSessionTimeList.get(0),savedSessionTimeList.get(1),savedMovieAnnouncements.get(0)),
                    new MovieSession(hall2,savedSessionDateList.get(1),savedSessionTimeList.get(1),savedSessionTimeList.get(2),savedMovieAnnouncements.get(1)),
                    new MovieSession(hall1,savedSessionDateList.get(2),savedSessionTimeList.get(2),savedSessionTimeList.get(3),savedMovieAnnouncements.get(2)),
                    new MovieSession(hall2,savedSessionDateList.get(3),savedSessionTimeList.get(3),savedSessionTimeList.get(4),savedMovieAnnouncements.get(3)),
                    new MovieSession(hall1,savedSessionDateList.get(4),savedSessionTimeList.get(2),savedSessionTimeList.get(4),savedMovieAnnouncements.get(4))
            ));
            List<MovieSession> savedMovieSessions = movieSessionRepository.saveAll(movieSessionList);

            List<Users>usersList=new ArrayList<>(Arrays.asList(
                    new Users("Nurbek","nurbek","123","email","123")
            ));
            List<Users> savedUsers = userRepository.saveAll(usersList);


//            List<Ticket>ticketList=new ArrayList<>(Arrays.asList(
//                    new Ticket(savedMovieSessions.get(0),savedSeats.get(0), GenerateQRService.createQRCodeItext(savedSeats.get(0).getNumber().toString(),"1"),100.2,null, TicketStatus.NEW),
//                    new Ticket(savedMovieSessions.get(0),savedSeats.get(1), GenerateQRService.createQRCodeItext(savedSeats.get(1).getNumber().toString(),"2"),100.55,null, TicketStatus.NEW),
//                    new Ticket(savedMovieSessions.get(0),savedSeats.get(2), GenerateQRService.createQRCodeItext(savedSeats.get(2).getNumber().toString(),"3"),100.12,null, TicketStatus.NEW)
//            )
//            );
//            ticketRepository.saveAll(ticketList);
        }




    }
}
