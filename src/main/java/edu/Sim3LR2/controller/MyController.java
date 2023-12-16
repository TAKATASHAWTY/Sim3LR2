package edu.Sim3LR2.controller;

import edu.Sim3LR2.exception.UnsupportedCodeException;
import edu.Sim3LR2.exception.ValidationFailedException;
import edu.Sim3LR2.model.Request;
import edu.Sim3LR2.model.Response;
import edu.Sim3LR2.sevice.UnsupportedCodeMatchExceptionService;
import edu.Sim3LR2.sevice.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {

    private final ValidationService validationService;
    private final UnsupportedCodeMatchExceptionService unsupportedCodeMatchExceptionService;

    @Autowired
    public MyController(ValidationService validationService, UnsupportedCodeMatchExceptionService unsupportedCodeMatchExceptionService, UnsupportedCodeMatchExceptionService unsupportedCodeMatchExceptionService1) {
        this.validationService = validationService;
        this.unsupportedCodeMatchExceptionService = unsupportedCodeMatchExceptionService1;
    }


    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            validationService.isValid(bindingResult);
            unsupportedCodeMatchExceptionService.isUidMatch(request);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("Не поддерживаемая ошибка");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
