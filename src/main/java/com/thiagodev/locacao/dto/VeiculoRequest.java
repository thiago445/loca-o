package com.thiagodev.locacao.dto;

public record VeiculoRequest(String marca,
                            String modelo,
                            Long ano,
                            String placa,
                            boolean disponibilidade) {


}

