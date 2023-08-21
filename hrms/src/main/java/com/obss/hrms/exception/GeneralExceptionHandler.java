package com.obss.hrms.exception;


import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdvertisementApplicationNotFoundException.class)
    public ResponseEntity<?> advertisementApplicationNotFoundExceptionHandler(AdvertisementApplicationNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AdvertisementNotFoundException.class)
    public ResponseEntity<?> advertisementNotFoundExceptionHandler(AdvertisementNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AdvertisementStatueIsWrongException.class)
    public ResponseEntity<?> advertisementStatueIsWrongExceptionHandler(AdvertisementStatueIsWrongException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ApplyAdvertisementStatueIsWrongException.class)
    public ResponseEntity<?> applyAdvertisementStatueIsWrongExceptionHandler(ApplyAdvertisementStatueIsWrongException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlackListNotFoundException.class)
    public ResponseEntity<?> blackListNotFoundExceptionHandler(BlackListNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(HumanResourceNotFoundException.class)
    public ResponseEntity<?> humanResourceNotFoundExceptionHandler(HumanResourceNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JobSeekerDontOccupyBlackListException.class)
    public ResponseEntity<?> jobSeekerDontOccupyBlackListExceptionHandler(JobSeekerDontOccupyBlackListException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JobSeekerNotFoundException.class)
    public ResponseEntity<?> jobSeekerNotFoundExceptionHandler(JobSeekerNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PersonalSkillNotFoundException.class)
    public ResponseEntity<?> personalSkillNotFoundExceptionHandler(PersonalSkillNotFoundException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicantNotFoundThisAdvertisementException.class)
    public ResponseEntity<?> applicantNotFoundThisAdvertisementExceptionHandler(ApplicantNotFoundThisAdvertisementException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AdvertisementPassiveException.class)
    public ResponseEntity<?> advertisementPassiveExceptionHandler(AdvertisementPassiveException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdvertisementHaveAlreadyBeenCreatingException.class)
    public ResponseEntity<?> advertisementHaveAlreadyBeenCreatingExceptionHandler(AdvertisementHaveAlreadyBeenCreatingException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AvdCodeHasBeUniqueException.class)
    public ResponseEntity<?> avdCodeHasBeUniqueExceptionHandler(AvdCodeHasBeUniqueException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(JobSeekerIsAlreadyApplyingAdvertisementException.class)
    public ResponseEntity<?> jobSeekerIsAlreadyApplyingAdvertisementExceptionHandler(JobSeekerIsAlreadyApplyingAdvertisementException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(JobSeekerIsAlreadyOccupyingBlackListException.class)
    public ResponseEntity<?> jobSeekerIsAlreadyOccupyingBlackListExceptionHandler(JobSeekerIsAlreadyOccupyingBlackListException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<?> tokenNotValidExceptionHandler(TokenNotValidException exception)  {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
   }
