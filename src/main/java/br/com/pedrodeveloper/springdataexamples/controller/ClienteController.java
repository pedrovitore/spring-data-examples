package br.com.pedrodeveloper.springdataexamples.controller;

import javax.validation.Valid;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pedrodeveloper.springdataexamples.model.dto.ClienteAddDto;
import br.com.pedrodeveloper.springdataexamples.model.dto.ClienteUpdateDto;
import br.com.pedrodeveloper.springdataexamples.model.entities.Cliente;
import br.com.pedrodeveloper.springdataexamples.repository.ClienteRepository;
import br.com.pedrodeveloper.springdataexamples.util.StringValidationUtils;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/clientes")
	public String getClientes(
			@RequestParam(required = false) String page, 
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String initialDate,
			@RequestParam(required = false) String finalDate, 
			Model model) {
		Integer pageNumber = StringValidationUtils.stringToPageNumber(page);
		if (name != null) name =  name.replace(" ", "%");
		LocalDateTime initialDateConverted = StringValidationUtils.stringToDate(initialDate);
		LocalDateTime finalDateConverted = StringValidationUtils.stringToDate(finalDate);
		
		//Add 1 dia na finalDate e na query compara usando "<" para pegar todos os horários do dia
		if (finalDateConverted != null)
			finalDateConverted = finalDateConverted.plusDays(1l);
		
		Page<Cliente> clientes = clienteRepository.find(name, initialDateConverted, finalDateConverted, PageRequest.of(pageNumber, 20));
		
		model.addAttribute("clientes", clientes);
		
		return "clientes";
	}
	
	@GetMapping("/clientes/{id}")
	public String getMovieById(@PathVariable(required = true) Integer id, Model model) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		
		if (cliente == null)
			throw new IllegalArgumentException("Cliente Id not found.");

		model.addAttribute("cliente", cliente);
		
		return "clientes-detail";
	}
	
	@PostMapping("/movies")
	public Cliente addMovie(@RequestBody @Valid ClienteAddDto dto) {
		return clienteRepository.save(dto.getEntity());
	}
	
	@PutMapping("/movies")
	public Cliente updateMovie(@RequestBody @Valid ClienteUpdateDto dto) {
		Cliente cliente = clienteRepository.findById(dto.getId()).orElse(null);
		
		if (cliente == null)
			throw new IllegalArgumentException("Cliente Id not found.");
		
		dto.updateEntity(cliente);
		clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable(required = true) Integer id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		
		if (cliente == null)
			throw new IllegalArgumentException("Cliente Id not found.");
		
		clienteRepository.delete(cliente);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
