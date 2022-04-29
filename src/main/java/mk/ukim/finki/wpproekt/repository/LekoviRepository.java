package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Lekovi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LekoviRepository extends JpaRepository<Lekovi, Integer> {
}
