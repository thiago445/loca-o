package com.thiagodev.locacao.controller;

import com.thiagodev.locacao.dto.ClienteRequest;
import com.thiagodev.locacao.dto.ResponseVeiculos;
import com.thiagodev.locacao.entity.Cliente;
import com.thiagodev.locacao.entity.Veiculo;
import com.thiagodev.locacao.repository.VeiculoRepository;
import com.thiagodev.locacao.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// CORREÇÃO 1: Usar @RestController para APIs REST que retornam JSON.
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // CORREÇÃO 2: Usar @RequestBody para receber o corpo da requisição em JSON.
    @PostMapping
    public ResponseEntity<Void> createCliente(@RequestBody ClienteRequest dto){
        clienteService.saveCliente(dto);
        return ResponseEntity.created(null).build(); // MELHORIA: Retornar status 201 Created para criação.
    }

    // CORREÇÃO 3: Usar @DeleteMapping e @PathVariable para variáveis no caminho da URL.
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable UUID clienteId){
        clienteService.deleteCliente(clienteId);
        return ResponseEntity.noContent().build(); // MELHORIA: Retornar status 204 No Content para deleção.
    }

    // CORREÇÃO 4: Usar @PutMapping ou @PostMapping para ações e @PathVariable.
    @PutMapping("/{clienteId}/veiculos/{veiculoId}")
    public ResponseEntity<Void> adicionarVeiculo(@PathVariable UUID clienteId, @PathVariable Long veiculoId){
        clienteService.adicionarVeiculo(veiculoId, clienteId);
        return ResponseEntity.ok().build();
    }

    // CORREÇÃO 5: Usar @DeleteMapping ou @PutMapping para representar a devolução.
    @DeleteMapping("/{clienteId}/veiculos/{veiculoId}")
    public ResponseEntity<Void> removerVeiculo(@PathVariable UUID clienteId, @PathVariable Long veiculoId){
        clienteService.removerVeiculo(veiculoId, clienteId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clienteId}/veiculos")
    public ResponseEntity<List<ResponseVeiculos>> getVeiculosDoCliente(@PathVariable UUID clienteId){
        List<Veiculo> veiculos = clienteService.findVeiculosByCliente(clienteId);

        // CORREÇÃO 6: Mapear a lista de Entidades (Veiculo) para a lista de DTOs (ResponseVeiculos).
        List<ResponseVeiculos> response = veiculos.stream()
                .map(veiculo -> new ResponseVeiculos(
                        veiculo.getId(),
                        veiculo.getMarca(),
                        veiculo.getModelo(),
                        veiculo.getAno(),
                        veiculo.getPlaca()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAllClientes();
        return ResponseEntity.ok(clientes);
    }


}
