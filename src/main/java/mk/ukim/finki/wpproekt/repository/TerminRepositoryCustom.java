package mk.ukim.finki.wpproekt.repository;


import mk.ukim.finki.wpproekt.model.Termin;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


@Repository
public interface TerminRepositoryCustom {
    List<Termin> findAllByTerminId (Integer termin_id);
    Termin findByTerminId (Integer termin_id);
    void deleteByTerminId (Integer termin_id);
}
