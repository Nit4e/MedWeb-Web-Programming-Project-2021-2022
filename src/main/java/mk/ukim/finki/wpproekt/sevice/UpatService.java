package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Upat;

import java.util.List;
import java.util.Optional;

public interface UpatService {
    Optional<Upat> findById (Long id);
    List<Upat> findAll();
    List<Upat> findAllWithoutReservation();
    Optional<Upat> selectedUpat (Long upat_id);
    Optional<Upat> save(String dijagnoza, Long korisnikId, Long oddelId, String doktor);
    void deleteById(Long upat_id);
    Optional<Upat> edit(Long upat_id, String dijagnoza, Long korisnikId, Long oddelId, String doktor);
}
