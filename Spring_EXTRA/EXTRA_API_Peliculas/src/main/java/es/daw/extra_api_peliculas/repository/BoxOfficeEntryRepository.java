package es.daw.extra_api_peliculas.repository;

import es.daw.extra_api_peliculas.dto.response.TopGrossingMovieReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface BoxOfficeRepository {
    @Query("""



    """)
    List<TopGrossingMovieReport> topGrossingMovies(
            @Param("genre") String genre,
            @Param("from")  LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );
}
