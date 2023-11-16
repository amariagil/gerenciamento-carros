package com.maria.backend.dtos;

public record VeiculoDto(String modelo, String marca, String cor, Integer ano_fabricacao, String placa,
                Boolean reservado) {

}
