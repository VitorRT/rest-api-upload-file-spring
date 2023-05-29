package br.com.vitordev.upload.rest.upload.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
public class AttachmentNotFoundException extends RuntimeException{

    private Integer status;
    private String message;

    public AttachmentNotFoundException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST.value();
    } 
}
