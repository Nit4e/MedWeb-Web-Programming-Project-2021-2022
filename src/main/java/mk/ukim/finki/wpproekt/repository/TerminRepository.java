package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Termin;
import mk.ukim.finki.wpproekt.model.TerminId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
@Component("main")
public interface TerminRepository extends JpaRepository<Termin, TerminId>, TerminRepositoryCustom{

    @Query("select t from Termin t left join Rezervacija r on t.termin_id = r.termin.termin_id left join Transakcija tr on r.rezervacija_id = tr.rezervacija.rezervacija_id where (t.vreme > :now and r.termin is null) or (t.vreme > :now and r.termin is not null and tr.rezervacija is null)")
    List<Termin> findFutureAndFree (ZonedDateTime now);

    @Query("select t from Termin t left join Rezervacija r on t.termin_id = r.termin.termin_id left join Transakcija tr on r.rezervacija_id = tr.rezervacija.rezervacija_id where (t.vreme > :now and t.covek_id = :doktor_id) and ((t.vreme > :now and r.termin is not null and tr.rezervacija is null) or (t.vreme > :now and r.termin is null))")
    List<Termin> findFutureAndFreeAndByDoktor (ZonedDateTime now, Long doktor_id);

}
