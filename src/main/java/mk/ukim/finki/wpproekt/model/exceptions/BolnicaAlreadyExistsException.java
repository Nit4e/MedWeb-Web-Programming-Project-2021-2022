package mk.ukim.finki.wpproekt.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BolnicaAlreadyExistsException extends RuntimeException {

    public BolnicaAlreadyExistsException (String naziv, String grad) {
        super(String.format("Hospital with hospital_name: %s in city: %s already exists", naziv, grad));
    }
}
