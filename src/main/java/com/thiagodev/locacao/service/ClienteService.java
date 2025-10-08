package com.thiagodev.locacao.service;

import com.thiagodev.locacao.dto.ClienteRequest;
import com.thiagodev.locacao.entity.Cliente;
import com.thiagodev.locacao.entity.Veiculo;
import com.thiagodev.locacao.repository.ClienteRepository;
import com.thiagodev.locacao.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, VeiculoRepository veiculoRepository) {
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public void saveCliente(ClienteRequest dto) {
        Cliente cliente= new Cliente();
        cliente.setName(dto.name());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());
        clienteRepository.save(cliente);
    }

    public void deleteCliente(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));

        // MELHORIA: Lógica de negócio para não deletar cliente com veículos alugados
        if (!veiculoRepository.findByCliente(cliente).isEmpty()) {
            throw new IllegalStateException("Não é possível deletar um cliente que possui veículos alugados.");
        }

        // CORREÇÃO: Usar delete() em vez de save()
        clienteRepository.delete(cliente);
    }

    public Cliente getCliente(UUID id) {
        // CORREÇÃO: Lançar exceção se o cliente não for encontrado em vez de usar .get() diretamente
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));
    }

    public List<Veiculo> findVeiculosByCliente(UUID clienteId) {
        Cliente cliente = getCliente(clienteId);
        return veiculoRepository.findByCliente(cliente);
    }


    @Transactional // MELHORIA: Usar transação para garantir a consistência dos dados
    public void adicionarVeiculo(Long veiculoId, UUID clienteId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + veiculoId));
        Cliente cliente = getCliente(clienteId);

        // MELHORIA: Lógica de negócio para verificar se o veículo está disponível
        if (!veiculo.isDisponibilidade()) {
            throw new IllegalStateException("Veículo com ID " + veiculoId + " não está disponível para locação.");
        }

        veiculo.setDisponibilidade(false);
        veiculo.setCliente(cliente);
        veiculoRepository.save(veiculo);
        // CORREÇÃO: Não é necessário salvar o cliente, pois a relação é do lado do Veículo.
    }
    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void removerVeiculo(Long veiculoId, UUID clienteId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + veiculoId));
        Cliente cliente = getCliente(clienteId);

        // MELHORIA: Lógica de negócio para garantir que o veículo pertence ao cliente
        if (veiculo.getCliente() == null || !veiculo.getCliente().getUuid().equals(clienteId)) {
            throw new IllegalStateException("O veículo não está alugado para este cliente.");
        }

        veiculo.setDisponibilidade(true);
        veiculo.setCliente(null);
        veiculoRepository.save(veiculo);
    }
}
