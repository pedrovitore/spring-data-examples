package br.com.pedrodeveloper.springdataexamples.model.dto;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.pedrodeveloper.springdataexamples.model.entities.Cliente;

@Entity
public class ClienteAddDto {

	private String nome;

	@JsonIgnore
	public Cliente getEntity() {
		Cliente cliente = new Cliente();
		
		cliente.setNome(getNome());
		
		return null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
