package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Bolnica;
import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.OddelId;
import mk.ukim.finki.wpproekt.model.Specijalnost;
import mk.ukim.finki.wpproekt.model.exceptions.OddelAlreadyExistsException;
import mk.ukim.finki.wpproekt.repository.BolnicaRepository;
import mk.ukim.finki.wpproekt.repository.OddelRepository;
import mk.ukim.finki.wpproekt.repository.OddelRepositoryCustom;
import mk.ukim.finki.wpproekt.repository.SpecijalnostRepository;
import mk.ukim.finki.wpproekt.sevice.OddelService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OddelServiceImpl implements OddelService {

    @Qualifier("mainO")
    private final OddelRepository oddelRepository;
    @Qualifier("customO")
    private final OddelRepositoryCustom oddelRepositoryCustom;
    private final BolnicaRepository bolnicaRepository;
    private final SpecijalnostRepository specijalnostRepository;

    public OddelServiceImpl(OddelRepository oddelRepository, OddelRepositoryCustom oddelRepositoryCustom,
                            BolnicaRepository bolnicaRepository,
                            SpecijalnostRepository specijalnostRepository) {
        this.oddelRepository = oddelRepository;
        this.oddelRepositoryCustom = oddelRepositoryCustom;
        this.bolnicaRepository = bolnicaRepository;
        this.specijalnostRepository = specijalnostRepository;
    }

    @Override
    public void save(Oddel oddel) {

        this.oddelRepository.save(oddel);
    }

    @Override
    public List<Oddel> findAll() {

        return this.oddelRepository.findAll();
    }

    @Override
    public Optional<Oddel> findById(OddelId id) {

        return this.oddelRepository.findById(id);
    }

    @Override
    public Optional<Oddel> findByOddelId(Long oddel_id) {
        return this.oddelRepositoryCustom.findByOddelId(oddel_id);
    }

    @Override
    public Oddel save(String naziv, Long bolnica_id, Long specijalnost_id) {
        Bolnica bolnica = this.bolnicaRepository.findById(bolnica_id).get();
        Specijalnost specijalnost = this.specijalnostRepository.findById(specijalnost_id).get();

        List<Oddel> oddelList = this.oddelRepository.findAll();

        for (int i = 0; i < oddelList.size(); i++) {
            if (oddelList.get(i).getBolnica_id().equals(bolnica) &&
                    oddelList.get(i).getNaziv().equals(naziv)) {
                throw new OddelAlreadyExistsException(bolnica.getBolnica_id(), naziv);
            }
        }
        Oddel oddel = new Oddel(naziv, bolnica, specijalnost);
        return oddelRepository.save(oddel);
    }
}
