package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Rezervacija;

import java.util.Optional;

public interface RezervacijaService {
    void save (Rezervacija rezervacija);
    Optional<Rezervacija> findById (Integer id);
}
