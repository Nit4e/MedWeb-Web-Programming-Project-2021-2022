package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Specijalnost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecijalnostRepository extends JpaRepository<Specijalnost, Integer> {


}
