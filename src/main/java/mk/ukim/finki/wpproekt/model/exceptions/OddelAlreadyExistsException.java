package mk.ukim.finki.wpproekt.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class OddelAlreadyExistsException extends RuntimeException {
    public OddelAlreadyExistsException(Long bolnica_id, String naziv) {
        super(String.format("Department with hospital_id: %d and department_name %s already exists", bolnica_id, naziv));
    }
}
