package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.Rezervacija;
import mk.ukim.finki.wpproekt.model.Upat;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wpproekt.model.exceptions.KorisnikNotFoundException;
import mk.ukim.finki.wpproekt.model.exceptions.OddelNotFoundException;
import mk.ukim.finki.wpproekt.model.exceptions.UpatNotFoundException;
import mk.ukim.finki.wpproekt.repository.KorisnikRepository;
import mk.ukim.finki.wpproekt.repository.OddelRepository;
import mk.ukim.finki.wpproekt.repository.RezervacijaRepository;
import mk.ukim.finki.wpproekt.repository.UpatRepository;
import mk.ukim.finki.wpproekt.sevice.UpatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Service
public class UpatServiceImpl implements UpatService{

    private final UpatRepository upatRepository;
    private final OddelRepository oddelRepository;
    private final KorisnikRepository korisnikRepository;
    private final RezervacijaRepository rezervacijaRepository;

    public UpatServiceImpl(UpatRepository upatRepository, OddelRepository oddelRepository,
                           KorisnikRepository korisnikRepository, RezervacijaRepository rezervacijaRepository) {
        this.upatRepository = upatRepository;
        this.oddelRepository = oddelRepository;
        this.korisnikRepository = korisnikRepository;
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public Optional<Upat> findById(Integer id) {
        return this.upatRepository.findById(id);
    }

    @Override
    public List<Upat> findAll() {
        return this.upatRepository.findAll();
    }

    @Override
    public List<Upat> findAllWithoutReservation() {
        return this.upatRepository.findAllWithoutReservation();
    }

    @Override
    public Optional<Upat> selectedUpat(Integer upat_id) {
        if (upat_id == null) {
            throw new InvalidArgumentsException();
        }
        return this.upatRepository.findById(upat_id);
    }

    @Override
    @Transactional
    public Optional<Upat> edit(Integer upat_id, String dijagnoza, Integer korisnikId, Integer oddelId,  String doktor) {
        Upat upat = this.upatRepository.findById(upat_id).orElseThrow(()
                -> new UpatNotFoundException(upat_id));

        upat.setDijagnoza(dijagnoza);

        Oddel oddel = this.oddelRepository.findByOddelId(oddelId)
                .orElseThrow(() -> new OddelNotFoundException(oddelId));
        upat.setOddel(oddel);

        Korisnik korisnik = this.korisnikRepository.findById(korisnikId)
                .orElseThrow(() -> new KorisnikNotFoundException(korisnikId));
        upat.setKorisnik(korisnik);

        upat.setDoktor(doktor);

        return Optional.of(this.upatRepository.save(upat));
    }

    @Override
    public Optional<Upat> save(String dijagnoza, Integer korisnikId, Integer oddelId, String doktor) {
        Oddel oddel = this.oddelRepository.findByOddelId(oddelId)
                .orElseThrow(() -> new OddelNotFoundException(oddelId));
        Korisnik korisnik = this.korisnikRepository.findById(korisnikId)
                .orElseThrow(() -> new KorisnikNotFoundException(korisnikId));
        return Optional.of(this.upatRepository.save(new Upat(dijagnoza, korisnik, oddel, doktor)));
    }

    @Override
    public void deleteById(Integer upat_id) {

        List<Rezervacija> rezervacijaList = this.rezervacijaRepository.findAll();
        for (Rezervacija rezervacija : rezervacijaList) {
            if (rezervacija.getUpat().getUpat_id().equals(upat_id)) {
                this.rezervacijaRepository.delete(rezervacija);
            }
        }
        this.upatRepository.deleteById(upat_id);
    }


}
