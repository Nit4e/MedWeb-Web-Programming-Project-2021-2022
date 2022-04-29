package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Upat;

import java.util.List;
import java.util.Optional;

public interface UpatService {
    Optional<Upat> findById (Integer id);
    List<Upat> findAll();
    List<Upat> findAllWithoutReservation();
    Optional<Upat> selectedUpat (Integer upat_id);
    Optional<Upat> save(String dijagnoza, Integer korisnikId, Integer oddelId, String doktor);
    void deleteById(Integer upat_id);
    Optional<Upat> edit(Integer upat_id, String dijagnoza, Integer korisnikId, Integer oddelId, String doktor);
}
