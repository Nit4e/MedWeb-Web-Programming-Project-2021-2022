package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Lekovi;

import java.util.List;
import java.util.Optional;

public interface LekoviService {

    List<Lekovi> listAll();

    Optional<Lekovi> findById(Long lekId);

    Lekovi save(String generickoIme, String imeLek);
}
