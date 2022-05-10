package mk.ukim.finki.wpproekt.repository;


import mk.ukim.finki.wpproekt.model.Termin;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


@Repository
public interface TerminRepositoryCustom {
    List<Termin> findAllByTerminId (Long termin_id);
    Termin findByTerminId (Long termin_id);
    void deleteByTerminId (Long termin_id);
}
