package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
}
