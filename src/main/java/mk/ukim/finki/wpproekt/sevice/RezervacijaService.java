package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Rezervacija;

import java.util.List;
import java.util.Optional;

public interface RezervacijaService {
    void save (Rezervacija rezervacija);
    Optional<Rezervacija> findById (Integer id);
    List<Rezervacija> findAllValidReservations(Integer pacient_id);
    List<Rezervacija> findAllValidReservationsForDoktor(Integer doktor_id);

}
