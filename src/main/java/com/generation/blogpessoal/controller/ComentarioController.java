package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Comentario;
import com.generation.blogpessoal.repository.ComentarioRepository;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    @GetMapping
    public ResponseEntity<Page<Comentario>> getAll(
            @PageableDefault(size = 10, sort = "data", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(comentarioRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getById(@PathVariable("id") Long id) {
        return comentarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<Comentario>> getByPostagem(@PathVariable("postagemId") Long postagemId) {
        if (!postagemRepository.existsById(postagemId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada!");

        return ResponseEntity.ok(comentarioRepository.findAllByPostagemId(postagemId));
    }

    @PostMapping
    public ResponseEntity<Comentario> post(@Valid @RequestBody Comentario comentario) {
        if (!postagemRepository.existsById(comentario.getPostagem().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Postagem não existe!");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comentarioRepository.save(comentario));
    }

    @PutMapping
    public ResponseEntity<Comentario> put(@Valid @RequestBody Comentario comentario) {
        if (!comentarioRepository.existsById(comentario.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado!");

        if (!postagemRepository.existsById(comentario.getPostagem().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Postagem não existe!");

        return ResponseEntity.ok(comentarioRepository.save(comentario));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        comentarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado!"));

        comentarioRepository.deleteById(id);
    }
}
