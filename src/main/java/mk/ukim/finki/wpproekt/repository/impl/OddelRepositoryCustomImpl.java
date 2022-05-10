package mk.ukim.finki.wpproekt.repository.impl;


import mk.ukim.finki.wpproekt.model.Bolnica;
import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.repository.OddelRepositoryCustom;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Component("customO")
public class OddelRepositoryCustomImpl implements OddelRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Oddel> findByOddelId(Long oddel_id) {
        return Optional.ofNullable(entityManager.createQuery("select o from Oddel o where o.oddel_id = :oddel_id",
                Oddel.class).setParameter("oddel_id", oddel_id).setMaxResults(1).getSingleResult());
    }

//    @Override
//    public Optional<Oddel> findByBolnicaIdAndOddelName(Bolnica bolnica_id, String naziv) {
//        return Optional.ofNullable(entityManager.createQuery("select o from Oddel o where o.bolnica_id = :bolnica_id and o.naziv = :naziv",
//                Oddel.class).setParameter("bolnica_id", bolnica_id).setParameter("naziv", naziv)
//                .setMaxResults(1).getSingleResult());
//    }
}
