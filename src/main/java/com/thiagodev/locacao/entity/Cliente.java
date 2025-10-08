package com.thiagodev.locacao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference; // 1. ADICIONE ESTA IMPORTAÇÃO
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;
    private String telefone;
    private String cpf;

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference // 2. ADICIONE ESTA ANOTAÇÃO
    private List<Veiculo> veiculos;
}
