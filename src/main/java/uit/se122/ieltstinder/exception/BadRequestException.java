package uit.se122.ieltstinder.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import static uit.se122.ieltstinder.controller.advice.ExceptionTranslator.BAD_REQUEST_TITLE;

public class BadRequestException extends AbstractThrowableProblem {
    public BadRequestException(String message) {
        super(null, BAD_REQUEST_TITLE, Status.BAD_REQUEST, message);
    }
}
