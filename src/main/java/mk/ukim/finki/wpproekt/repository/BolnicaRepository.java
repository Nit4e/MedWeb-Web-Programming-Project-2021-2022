package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Bolnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolnicaRepository extends JpaRepository<Bolnica, Integer> {

}
