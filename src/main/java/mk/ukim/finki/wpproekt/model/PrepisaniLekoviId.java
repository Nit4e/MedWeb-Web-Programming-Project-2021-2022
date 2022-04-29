package mk.ukim.finki.wpproekt.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Data
//@Embeddable
public class PrepisaniLekoviId implements Serializable {

    //@Column(name = "covek_id")
    private Integer covek_id;
    //@Column(name = "lek_id")
    private Integer lek_id;
    private String pacient_username;


    public PrepisaniLekoviId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrepisaniLekoviId prepisaniLekoviId = (PrepisaniLekoviId) o;
        return covek_id.equals(prepisaniLekoviId.covek_id) &&
                lek_id.equals(prepisaniLekoviId.lek_id) &&
                pacient_username.equals(prepisaniLekoviId.pacient_username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(covek_id, lek_id, pacient_username);
    }
}
