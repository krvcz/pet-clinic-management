package pl.ssanko.petclinic.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ssanko.petclinic.data.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT count(1) FROM events " +
            "WHERE date(\"date\") = :date ", nativeQuery = true)
    Long countToday(LocalDate date);
}
