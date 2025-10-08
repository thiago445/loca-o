package com.thiagodev.locacao.controller;

import com.thiagodev.locacao.dto.ResponseVeiculos;
import com.thiagodev.locacao.dto.VeiculoRequest;
import com.thiagodev.locacao.entity.Veiculo;
import com.thiagodev.locacao.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// CORREÇÃO 1: Usar @RestController
@RestController
@RequestMapping("/veiculos") // MELHORIA: Usar nomes de recurso no plural
public class VeiculoController {

    private final VeiculoService veiculoService;

    @Autowired
    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    // MELHORIA: Nome do método mais claro
    public ResponseEntity<Void> criarVeiculo(@RequestBody VeiculoRequest dto){
        veiculoService.adicionandoVeiculo(dto);
        return ResponseEntity.created(null).build();
    }

    // CORREÇÃO 2: Usar @DeleteMapping e @PathVariable
    @DeleteMapping("/{veiculoId}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable(name = "veiculoId") Long id){
        veiculoService.deleteVeiculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponiveis") // MELHORIA: Endpoint mais específico
    public ResponseEntity<List<ResponseVeiculos>> veiculosDisponiveis(){
        List<Veiculo> veiculosDisponiveis = veiculoService.findAllAvailableVeiculos();

        // MELHORIA: Retornar o DTO para manter a consistência da API
        List<ResponseVeiculos> response = veiculosDisponiveis.stream()
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
}
