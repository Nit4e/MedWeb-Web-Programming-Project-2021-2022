package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Upat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UpatRepository extends JpaRepository<Upat, Integer> {
    Optional<Upat> findById(Integer upat_id);

    @Query("select u from Upat u left join Rezervacija r on u.upat_id = r.upat.upat_id left join Transakcija t on r.rezervacija_id = t.rezervacija.rezervacija_id where (r.upat.upat_id is null) or (r.upat.upat_id is not null and t.rezervacija.rezervacija_id is null)")
    List<Upat> findAllWithoutReservation();
}
