package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.OddelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("mainO")
public interface OddelRepository extends JpaRepository<Oddel, OddelId>, OddelRepositoryCustom {
}
