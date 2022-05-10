package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Rezervacija;

import java.util.List;
import java.util.Optional;

public interface RezervacijaService {
    void save (Rezervacija rezervacija);
    Optional<Rezervacija> findById (Long id);
    List<Rezervacija> findAllValidReservations(Long pacient_id);
    List<Rezervacija> findAllValidReservationsForDoktor(Long doktor_id);

}
