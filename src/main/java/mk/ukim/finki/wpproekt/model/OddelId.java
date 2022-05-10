package mk.ukim.finki.wpproekt.model;


import java.io.Serializable;
import java.util.Objects;

public class OddelId implements Serializable {
    private Long bolnica_id;
    private Long oddel_id;

    public OddelId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OddelId oddelId = (OddelId) o;
        return bolnica_id.equals(oddelId.bolnica_id) &&
                oddel_id.equals(oddelId.oddel_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bolnica_id, oddel_id);
    }
}
