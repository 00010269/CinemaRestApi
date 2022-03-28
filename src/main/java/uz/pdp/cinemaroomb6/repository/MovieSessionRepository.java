package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.MovieSession;
import uz.pdp.cinemaroomb6.projection.MovieSessionByIdProjection;
import uz.pdp.cinemaroomb6.projection.MovieSessionProjection;
import uz.pdp.cinemaroomb6.projection.MovieSessionQRInfoProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieSessionRepository extends JpaRepository<MovieSession, UUID> {

    @Query(nativeQuery = true,
    value = "select cast(ms.id as varchar) as id,\n" +
            "       cast(ms.movie_announcement_id as varchar) as movieAnnouncementId,\n" +
            "       cast(ms.session_date_id as varchar) as sessionDateId,\n" +
            "       sd.date as sessionDate,\n" +
            "       cast(ms.start_time_id as varchar) as startTimeId,\n" +
            "       (select st.time from session_times st where st.id = ms.start_time_id) as startTime,\n" +
            "       cast(ms.end_time_id as varchar) as endTimeId,\n" +
            "       (select st.time from session_times st where st.id = ms.end_time_id) as endTime\n" +
            "from movie_sessions ms\n" +
            "join session_dates sd on ms.session_date_id = sd.id\n" +
            "where ms.hall_id =:hallId")

    List<MovieSessionProjection> findByHallId(UUID hallId);


    @Query(nativeQuery = true,
            value = "select cast(ms.id as varchar) as id," +
                    "cast(ms.session_date_id as varchar) as sessionDateId," +
                    "sd.date as sessionDate," +
                    "cast(ms.start_time_id as varchar) as startTimeId," +
                    "(select st.time from session_times st where st.id = ms.start_time_id)\n" +
                    "               as startTime," +
                    "cast(ms.end_time_id as varchar) as endTimeId," +
                    "(select st.time from session_times st where st.id = ms.end_time_id)\n" +
                    "               as endTime" +
                    " from movie_sessions ms " +
                    "join session_dates sd on ms.session_date_id = sd.id " +
                    "where ms.id=:sessionId")

    MovieSessionByIdProjection findBySessionId(UUID sessionId);




    @Query(nativeQuery = true,
    value = "select h.name as sessionHallName, " +
            " sd.date as sessionDate," +
            " (select st.time from session_times st where st.id = ms.start_time_id) as sessionStartTime, " +
            " (select st.time from session_times st where st.id = ms.end_time_id) as sessionTime " +
            " from movie_sessions ms " +
            " join halls h on h.id = ms.hall_id " +
            " join session_dates sd on sd.id = ms.session_date_id " +
            " where ms.id=:sessionId")
    MovieSessionQRInfoProjection findBySessionIdQRInfo(UUID sessionId);


}
