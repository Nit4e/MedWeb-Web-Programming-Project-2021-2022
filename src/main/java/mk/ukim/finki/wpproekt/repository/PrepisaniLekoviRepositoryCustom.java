package mk.ukim.finki.wpproekt.repository;


import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrepisaniLekoviRepositoryCustom {

    List<Lekovi> findPacientLekovi();
    Optional<PrepisaniLekovi> findById(Integer lek_id);
}
