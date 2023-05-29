package br.com.vitordev.upload.rest.upload.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.vitordev.upload.rest.upload.rest.dto.ResponseData;
import br.com.vitordev.upload.rest.upload.rest.entity.Attachament;
import br.com.vitordev.upload.rest.upload.rest.service.AttachmentService;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        Attachament attachament = null;
        attachament = attachmentService.saveAttachment(file);

        String downloadURL = "";

        // A melhor forma seria usar o HATEOAS, mas como é um projeto bem simples
        // escolhi usar o ServletUriComponentBuilder
        // pra não ter que adicionar outra dependência :P
        downloadURL = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/attachment")
            .path("/download/")
            .path(attachament.getId())
            .toUriString();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseData(
                attachament.getFileName(),
                downloadURL,
                file.getContentType(),
                file.getSize()
            ));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Attachament attachament = null;
        attachament = attachmentService.findAttachmentByIdOrElseThrowBadRequest(fileId);
        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(attachament.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachament.getFileName() + "\"")
            .body(new ByteArrayResource(attachament.getData()));
    
    }
}
