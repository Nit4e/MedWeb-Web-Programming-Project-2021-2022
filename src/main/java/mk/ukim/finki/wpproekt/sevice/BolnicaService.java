package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Bolnica;


import java.util.List;
import java.util.Optional;

public interface BolnicaService {

    void save (Bolnica bolnica);
    List<Bolnica> findAll();
    Optional<Bolnica> findById(Integer id);
}
