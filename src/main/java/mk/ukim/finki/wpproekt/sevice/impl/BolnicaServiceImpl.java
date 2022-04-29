package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Bolnica;
import mk.ukim.finki.wpproekt.model.exceptions.BolnicaAlreadyExistsException;
import mk.ukim.finki.wpproekt.repository.BolnicaRepository;
import mk.ukim.finki.wpproekt.sevice.BolnicaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BolnicaServiceImpl implements BolnicaService {

    private final BolnicaRepository bolnicaRepository;

    public BolnicaServiceImpl(BolnicaRepository bolnicaRepository) {
        this.bolnicaRepository = bolnicaRepository;
    }

    @Override
    public void save(Bolnica bolnica) {
        List<Bolnica> bolnicaList = this.bolnicaRepository.findAll();
        for (int i = 0; i < bolnicaList.size(); i++) {
            if (bolnica.getNaziv().equals(bolnicaList.get(i).getNaziv()) &&
                    bolnica.getGrad().equals(bolnicaList.get(i).getGrad())) {
                throw new BolnicaAlreadyExistsException(bolnica.getNaziv(), bolnica.getGrad());
            }
        }
        this.bolnicaRepository.save(bolnica);
    }

    @Override
    public List<Bolnica> findAll() {

        return this.bolnicaRepository.findAll();
    }

    @Override
    public Optional<Bolnica> findById(Integer id) {

        return this.bolnicaRepository.findById(id);
    }
}
