package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.TelefonskiBroevi;
import mk.ukim.finki.wpproekt.repository.TelefonskiBroeviRepository;
import mk.ukim.finki.wpproekt.sevice.TelefonskiBroeviService;
import org.springframework.stereotype.Service;


@Service
public class TelefonskiBroeviServiceImpl implements TelefonskiBroeviService {

    private final TelefonskiBroeviRepository telefonskiBroeviRepository;

    public TelefonskiBroeviServiceImpl(TelefonskiBroeviRepository telefonskiBroeviRepository) {

        this.telefonskiBroeviRepository = telefonskiBroeviRepository;
    }

    @Override
    public void save(TelefonskiBroevi telefonskiBroevi) {
        this.telefonskiBroeviRepository.save(telefonskiBroevi);
    }
}
