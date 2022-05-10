package mk.ukim.finki.wpproekt.repository.impl;

import mk.ukim.finki.wpproekt.model.Termin;
import mk.ukim.finki.wpproekt.repository.TerminRepositoryCustom;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Primary
@Component("custom")
public class TerminRepositoryImpl implements TerminRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Termin> findAllByTerminId(Long termin_id) {
        return entityManager.createQuery("select o from Termin o where o.termin_id = :termin_id",
                Termin.class).setParameter("termin_id", termin_id).getResultList();
    }

    @Override
    public Termin findByTerminId(Long termin_id) {
        return entityManager.createQuery("select o from Termin o where o.termin_id = :termin_id",
                Termin.class).setParameter("termin_id", termin_id).setMaxResults(1).getSingleResult();
    }

    @Override
    public void deleteByTerminId(Long termin_id) {
        entityManager.createQuery("delete from Termin o where o.termin_id = :termin_id");
    }

}
