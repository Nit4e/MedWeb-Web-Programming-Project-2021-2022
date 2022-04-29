package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Rezervacija;
import mk.ukim.finki.wpproekt.repository.RezervacijaRepository;
import mk.ukim.finki.wpproekt.sevice.RezervacijaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RezervacijaServiceImpl implements RezervacijaService {
    private final RezervacijaRepository rezervacijaRepository;

    public RezervacijaServiceImpl(RezervacijaRepository rezervacijaRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public void save(Rezervacija rezervacija) {

        this.rezervacijaRepository.save(rezervacija);
    }

    @Override
    public Optional<Rezervacija> findById(Integer id) {
        return this.rezervacijaRepository.findById(id);
    }
}
