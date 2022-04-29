package mk.ukim.finki.wpproekt.sevice.impl;


import mk.ukim.finki.wpproekt.model.Specijalnost;
import mk.ukim.finki.wpproekt.model.exceptions.SpecijalnostAlreadyExistsException;
import mk.ukim.finki.wpproekt.repository.SpecijalnostRepository;
import mk.ukim.finki.wpproekt.sevice.SpecijalnostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecijalnostImpl implements SpecijalnostService {

    private final SpecijalnostRepository specijalnostRepository;

    public SpecijalnostImpl(SpecijalnostRepository specijalnostRepository) {
        this.specijalnostRepository = specijalnostRepository;
    }

    @Override
    public void save(Specijalnost specijalnost) {
        List<Specijalnost> specijalnostList = this.specijalnostRepository.findAll();
        for (int i = 0; i < specijalnostList.size(); i++) {
            if (specijalnost.getNaziv().equals(specijalnostList.get(i).getNaziv())) {
                throw new SpecijalnostAlreadyExistsException(specijalnost.getNaziv());
            }
        }
        this.specijalnostRepository.save(specijalnost);
    }

    @Override
    public List<Specijalnost> findAll() {
        return this.specijalnostRepository.findAll();
    }

    @Override
    public Optional<Specijalnost> findById(Integer id) {
        return this.specijalnostRepository.findById(id);
    }
}
