package br.com.pedrodeveloper.springdataexamples.model.dto;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.pedrodeveloper.springdataexamples.model.entities.Cliente;

@Entity
public class ClienteUpdateDto {

	@NotNull
    private Integer id;

	private String nome;

	@JsonIgnore
	public Cliente updateEntity(Cliente cliente) {
		
		if (getNome() != null) {
			cliente.setNome(getNome());
		}
		
		return cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
