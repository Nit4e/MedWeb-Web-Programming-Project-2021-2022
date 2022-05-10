package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Rezervacija;
import mk.ukim.finki.wpproekt.model.Termin;
import mk.ukim.finki.wpproekt.model.TerminId;
import mk.ukim.finki.wpproekt.repository.RezervacijaRepository;
import mk.ukim.finki.wpproekt.repository.TerminRepository;
import mk.ukim.finki.wpproekt.repository.TerminRepositoryCustom;
import mk.ukim.finki.wpproekt.sevice.TerminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TerminServiceImpl implements TerminService {

    @Qualifier("main")
    private final TerminRepository terminRepository;
    @Qualifier(value = "custom")
    private final TerminRepositoryCustom terminRepositoryCustom;
    private final RezervacijaRepository rezervacijaRepository;

    public TerminServiceImpl(TerminRepository terminRepository,
                             TerminRepositoryCustom terminRepositoryCustom,
                             RezervacijaRepository rezervacijaRepository) {
        this.terminRepository = terminRepository;
        this.terminRepositoryCustom = terminRepositoryCustom;
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public Optional<Termin> findTerminById(TerminId terminId) {

        return this.terminRepository.findById(terminId);
    }

    @Override
    public List<Termin> findTerminByTermin_id(Long termin_id) {

        return this.terminRepositoryCustom.findAllByTerminId(termin_id);
    }

    @Override
    public Termin findOneTerminByTerminId(Long termin_id) {

        return this.terminRepositoryCustom.findByTerminId(termin_id);
    }

    @Override
    public void save(Termin termin) {

        this.terminRepository.save(termin);
    }

    @Override
    public void deleteTermin(Termin termin) {

        List<Rezervacija> rezervacijaList = this.rezervacijaRepository.findAll();

        for (Rezervacija rezervacija : rezervacijaList) {
            if (rezervacija.getRezervacija_id().equals(termin.getRezervacija().getRezervacija_id())) {
                this.rezervacijaRepository.delete(rezervacija);
            }
        }

        this.terminRepository.delete(termin);
    }

    @Override
    public List<Termin> findAll() {
        return this.terminRepository.findAll();
    }

    @Override
    public List<Termin> findOnlyFutureAndFree (ZonedDateTime now) {
        return this.terminRepository.findFutureAndFree(now);
    }

    @Override
    public List<Termin> findOnlyFutureAndFreeAndByDoktor(ZonedDateTime now, Long doktor_id) {
        return this.terminRepository.findFutureAndFreeAndByDoktor(now, doktor_id);
    }
}
