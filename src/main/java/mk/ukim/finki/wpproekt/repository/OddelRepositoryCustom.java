package mk.ukim.finki.wpproekt.repository;


import mk.ukim.finki.wpproekt.model.Bolnica;
import mk.ukim.finki.wpproekt.model.Oddel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OddelRepositoryCustom {

    Optional <Oddel> findByOddelId (Long oddel_id);
    //Optional <Oddel> findByBolnicaIdAndOddelName (Bolnica bolnica_id, String naziv);
}
