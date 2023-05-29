package br.com.vitordev.upload.rest.upload.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.vitordev.upload.rest.upload.rest.entity.Attachament;
import br.com.vitordev.upload.rest.upload.rest.exception.AttachmentNotFoundException;
import br.com.vitordev.upload.rest.upload.rest.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    @Autowired
    private AttachmentRepository attachmentRepository;


    @Override
    public Attachament saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                // Aqui eu poderia criar um dto para esse erro
                // e capturar em um handler global, mas
                // o intuito não é esse :D
                throw new Exception("O nome do arquivo tem um caminho inválido. [ " + fileName + " ]");
            }

            Attachament attachament = new Attachament(
                fileName, file.getContentType(), file.getBytes()
            );
            return attachmentRepository.saveAndFlush(attachament);
        } catch (Exception e) {
            throw new Exception("Não foi possivel salvar o arquivo '" + fileName + "'.");
        }
    }


    @Override
    public Attachament findAttachmentByIdOrElseThrowBadRequest(String fileId) {
        return getAttachament(fileId);
    }




    private Attachament getAttachament(String fileId) {
        return attachmentRepository.findById(fileId)
        .orElseThrow(
            () -> new AttachmentNotFoundException("Nenhum arquivo foi encontrado com esse id.")
        );
    }
    
    
}
