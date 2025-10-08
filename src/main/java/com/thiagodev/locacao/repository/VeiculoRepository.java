package com.thiagodev.locacao.repository;

import com.thiagodev.locacao.entity.Cliente;
import com.thiagodev.locacao.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {
    List<Veiculo> findByDisponibilidadeTrue();

    List<Veiculo> findBycliente(Cliente cliente);

    List<Veiculo> findByCliente(Cliente cliente);
}
