package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {

    @Query("select r from Rezervacija r left join Upat u on r.upat.upat_id = u.upat_id where r.transakcija is not null and u.korisnik.covek_id = :pacient_id")
    List<Rezervacija> listValidReservations(Integer pacient_id);

    @Query("select r from Rezervacija r left join Transakcija t on r.rezervacija_id = t.rezervacija.rezervacija_id where t.rezervacija is not null and r.termin.korisnik.covek_id = :doktor_id")
    List<Rezervacija> listValidReservationsForDoktor(Integer doktor_id);
}
