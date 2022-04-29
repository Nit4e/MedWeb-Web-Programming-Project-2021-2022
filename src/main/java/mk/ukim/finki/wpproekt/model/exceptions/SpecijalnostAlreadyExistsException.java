package mk.ukim.finki.wpproekt.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class SpecijalnostAlreadyExistsException extends RuntimeException {

    public SpecijalnostAlreadyExistsException(String naziv) {
        super(String.format("Specialty with specialty_name: %s already exists", naziv));
    }
}
