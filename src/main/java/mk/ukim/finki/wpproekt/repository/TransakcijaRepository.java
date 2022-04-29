package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Transakcija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Integer> {
}
