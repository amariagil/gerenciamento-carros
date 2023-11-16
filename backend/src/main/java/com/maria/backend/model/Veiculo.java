package com.maria.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "veiculo")

public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String cor;
    @Column(nullable = false)
    private Integer ano_fabricacao;
    @Column(nullable = false)
    private String placa;
    @Column(nullable = false)
    private Boolean reservado;

}
