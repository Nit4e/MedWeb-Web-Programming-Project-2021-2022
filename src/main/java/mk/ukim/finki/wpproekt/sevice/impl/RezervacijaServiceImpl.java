package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Rezervacija;
import mk.ukim.finki.wpproekt.repository.RezervacijaRepository;
import mk.ukim.finki.wpproekt.sevice.RezervacijaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RezervacijaServiceImpl implements RezervacijaService {
    private final RezervacijaRepository rezervacijaRepository;

    public RezervacijaServiceImpl(RezervacijaRepository rezervacijaRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public void save(Rezervacija rezervacija) {
        List<Rezervacija> rezervacijaList = this.rezervacijaRepository.findAll();
        for (Rezervacija r : rezervacijaList) {
            if (r.getUpat().getUpat_id().equals(rezervacija.getUpat().getUpat_id())) {
                this.rezervacijaRepository.delete(r);
            }
        }
        this.rezervacijaRepository.save(rezervacija);
    }

    @Override
    public Optional<Rezervacija> findById(Integer id) {
        return this.rezervacijaRepository.findById(id);
    }

    @Override
    public List<Rezervacija> findAllValidReservations(Integer pacient_id) {
        return this.rezervacijaRepository.listValidReservations(pacient_id);
    }

    @Override
    public List<Rezervacija> findAllValidReservationsForDoktor(Integer doktor_id) {
        return this.rezervacijaRepository.listValidReservationsForDoktor(doktor_id);
    }

}
