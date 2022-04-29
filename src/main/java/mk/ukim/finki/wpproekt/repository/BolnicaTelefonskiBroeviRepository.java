package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.BolnicaBroeviId;
import mk.ukim.finki.wpproekt.model.BolnicaTelefonskiBroevi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolnicaTelefonskiBroeviRepository extends JpaRepository<BolnicaTelefonskiBroevi, BolnicaBroeviId> {
}
