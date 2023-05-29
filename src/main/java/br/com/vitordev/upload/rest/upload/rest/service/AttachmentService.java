package br.com.vitordev.upload.rest.upload.rest.service;

import org.springframework.web.multipart.MultipartFile;

import br.com.vitordev.upload.rest.upload.rest.entity.Attachament;

public interface AttachmentService {

    Attachament saveAttachment(MultipartFile file) throws Exception;

    Attachament findAttachmentByIdOrElseThrowBadRequest(String fileId);
    
}
