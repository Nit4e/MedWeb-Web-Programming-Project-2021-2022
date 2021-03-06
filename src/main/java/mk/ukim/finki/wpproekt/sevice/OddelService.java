package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.OddelId;

import java.util.List;
import java.util.Optional;

public interface OddelService {

    void save (Oddel oddel);
    List<Oddel> findAll();
    Optional<Oddel> findById(OddelId id);
    Optional<Oddel> findByOddelId(Long oddel_id);
    Oddel save (String naziv, Long bolnica_id, Long specijalnost_id);
}
