package mk.ukim.finki.wpproekt.model;

import java.io.Serializable;
import java.util.Objects;

public class TelefonskiBroeviId implements Serializable {
    private Long covek_broj_id;
    private String telefonski_broj;

    public TelefonskiBroeviId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelefonskiBroeviId telefonskiBroeviId = (TelefonskiBroeviId) o;
        return covek_broj_id.equals(telefonskiBroeviId.covek_broj_id) &&
                telefonski_broj.equals(telefonskiBroeviId.telefonski_broj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(covek_broj_id, telefonski_broj);
    }
}
