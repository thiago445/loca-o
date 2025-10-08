package com.thiagodev.locacao.repository;

import com.thiagodev.locacao.entity.Cliente;
import com.thiagodev.locacao.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    List<Cliente> findByVeiculosIsNotEmpty();



}
