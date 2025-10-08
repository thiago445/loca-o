package com.thiagodev.locacao.service;

import com.thiagodev.locacao.dto.VeiculoRequest;
import com.thiagodev.locacao.entity.Veiculo;
import com.thiagodev.locacao.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Autowired
    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public void adicionandoVeiculo(VeiculoRequest dto){
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(dto.ano());
        veiculo.setPlaca(dto.placa());
        veiculo.setModelo(dto.modelo());
        veiculo.setDisponibilidade(true); // Um novo veículo sempre começa como disponível
        veiculo.setMarca(dto.marca());

        veiculoRepository.save(veiculo);
    }

    public void deleteVeiculo(Long id){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + id));

        // MELHORIA: Lógica de negócio para não deletar veículo alugado
        if (!veiculo.isDisponibilidade()) {
            throw new IllegalStateException("Não é possível deletar um veículo que está atualmente alugado.");
        }

        veiculoRepository.delete(veiculo);
    }

    public List<Veiculo> findAllAvailableVeiculos() {
        // Este método depende que você crie a query no VeiculoRepository
        // Ex: List<Veiculo> findByDisponibilidadeTrue();
        return veiculoRepository.findByDisponibilidadeTrue();
    }
}
