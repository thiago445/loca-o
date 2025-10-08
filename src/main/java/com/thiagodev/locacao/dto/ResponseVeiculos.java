package com.thiagodev.locacao.dto;

import com.thiagodev.locacao.entity.Veiculo;

import java.util.List;

public record ResponseVeiculos(Long id, String marca, String modelo, Long ano, String placa){

    public static ResponseVeiculos toResponse(Veiculo veiculo) {
        return new ResponseVeiculos(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getPlaca()
        );
    }
}
