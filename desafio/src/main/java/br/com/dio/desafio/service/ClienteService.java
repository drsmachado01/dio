package br.com.dio.desafio.service;

import org.springframework.stereotype.Service;

import br.com.dio.desafio.model.Cliente;

@Service
public interface ClienteService {

	Iterable<Cliente> listar();

	Cliente buscarPorId(Long id);
	
	Cliente gravar(Cliente cliente);
	
	Cliente atualizar(Long id, Cliente cliente);
	
	void excluir(Long id);
}
