package org.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.farmacia.model.Produto;
import org.generation.farmacia.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;
	
	@GetMapping
	private ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtosRepository.findAll());
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtosRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	@GetMapping("/nome/{nome}")
	private ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping ("/nome")
	public List<String> getNome(){
		return produtosRepository.findNome();
	}
	
	@PostMapping 
	private ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtosRepository.save(produto));
	}
	
	@PutMapping
	private ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
		return produtosRepository.findById(produto.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> protuto = produtosRepository.findById(id);
				if(protuto.isEmpty())
						throw new ResponseStatusException(HttpStatus.NOT_FOUND);
				
				produtosRepository.deleteById(id);
	}
	
}
