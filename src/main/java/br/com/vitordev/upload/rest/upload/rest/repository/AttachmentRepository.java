package br.com.vitordev.upload.rest.upload.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vitordev.upload.rest.upload.rest.entity.Attachament;

public interface AttachmentRepository extends JpaRepository<Attachament, String> {
    
}
