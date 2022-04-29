package mk.ukim.finki.wpproekt.model;


import java.io.Serializable;
import java.util.Objects;

public class BolnicaBroeviId implements Serializable {
    private Integer bolnica_id;
    private String telefonski_br_bolnica;

    public BolnicaBroeviId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BolnicaBroeviId bolnica_broevi_id = (BolnicaBroeviId) o;
        return bolnica_id.equals(bolnica_broevi_id.bolnica_id) &&
                telefonski_br_bolnica.equals(bolnica_broevi_id.telefonski_br_bolnica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bolnica_id, telefonski_br_bolnica);
    }
}
