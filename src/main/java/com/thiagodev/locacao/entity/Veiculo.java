package com.thiagodev.locacao.entity;


import com.fasterxml.jackson.annotation.JsonBackReference; // 1. ADICIONE ESTA IMPORTAÇÃO
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String marca;
    private String modelo;
    private Long ano;
    private String placa;
    private boolean disponibilidade;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference // 2. ADICIONE ESTA ANOTAÇÃO
    private Cliente cliente; // se o carro estiver alugado, guarda o cliente
}

