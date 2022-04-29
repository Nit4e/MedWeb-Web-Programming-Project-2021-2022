package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Termin;
import mk.ukim.finki.wpproekt.model.TerminId;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface TerminService {
    Optional<Termin> findTerminById (TerminId terminId);
    List<Termin> findTerminByTermin_id (Integer termin_id);
    Termin findOneTerminByTerminId (Integer termin_id);
    void save (Termin termin);
    void deleteTermin (Termin termin);
    List<Termin> findAll();
    List<Termin> findOnlyFutureAndFree (ZonedDateTime now);
    List<Termin> findOnlyFutureAndFreeAndByDoktor (ZonedDateTime now, Integer doktor_id);
}