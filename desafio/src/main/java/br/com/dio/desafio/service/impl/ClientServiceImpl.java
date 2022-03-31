package br.com.dio.desafio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dio.desafio.model.Cliente;
import br.com.dio.desafio.model.Endereco;
import br.com.dio.desafio.repository.ClienteRepository;
import br.com.dio.desafio.repository.EnderecoRepository;
import br.com.dio.desafio.service.ClienteService;
import br.com.dio.desafio.service.ViaCepService;

@Service
public class ClientServiceImpl implements ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;

	@Override
	public Iterable<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	public Cliente gravar(Cliente cliente) {
		return gravarClienteComCepValidado(cliente);
	}

	@Override
	public Cliente atualizar(Long id, Cliente cliente) {
		Cliente valid = clienteRepository.findById(id).orElse(null);
		if(null != valid) {
			return gravarClienteComCepValidado(cliente);
		}
		return null;
	}

	@Override
	public void excluir(Long id) {
		clienteRepository.deleteById(id);;
	}

	private Cliente gravarClienteComCepValidado(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			return enderecoRepository.save(viaCepService.consultarCep(cep));
		});
		cliente.setEndereco(endereco);
		return clienteRepository.save(cliente);
	}

	
}
