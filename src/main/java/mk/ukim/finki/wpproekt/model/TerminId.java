package mk.ukim.finki.wpproekt.model;

import java.io.Serializable;
import java.util.Objects;

public class TerminId implements Serializable {
    private Long covek_id;
    private Long termin_id;

    public TerminId() {
    }

    public TerminId(Long covek_id, Long termin_id) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerminId terminId = (TerminId) o;
        return covek_id.equals(terminId.covek_id) &&
                termin_id.equals(terminId.termin_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(covek_id, termin_id);
    }
}
