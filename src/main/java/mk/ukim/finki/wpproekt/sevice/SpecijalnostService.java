package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Specijalnost;

import java.util.List;
import java.util.Optional;

public interface SpecijalnostService {

    void save (Specijalnost specijalnost);
    List<Specijalnost> findAll();
    Optional<Specijalnost> findById(Integer id);
}
