package com.maria.backend.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import com.maria.backend.dtos.VeiculoDto;
import com.maria.backend.model.Veiculo;
import com.maria.backend.repositories.VeiculoRepository;
import com.maria.backend.services.VeiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("veiculos")
public class VeiculosController {

    @Autowired
    VeiculoRepository repositorio;
    @Autowired
    VeiculoService veiculoService;

    @Operation(description = "Cadastra os veiculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso! Foi cadastraodo corretamente."),
            @ApiResponse(responseCode = "400", description = "Não foi possivél cadastrar.")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody VeiculoDto veiculoDto) {
        Veiculo veiculo = veiculoService.cadastrarVeiculo(veiculoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
    }

    @Operation(description = "Lista todos os veiculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com todos os veiculos cadastrados"),
            @ApiResponse(responseCode = "400", description = "Não há nenhum veiculo")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos = veiculoService.listarVeiculos();
        return ResponseEntity.status(HttpStatus.OK).body(veiculos);
    }

    @Operation(description = "Lista os veiculos que estão disponiveis, ou seja, que reservao = Falso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com todos os veiculos cadastrados"),
            @ApiResponse(responseCode = "400", description = "Não há nenhum veiculo disponivel")
    })
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Veiculo>> listarVeiculosDisponiveis() {
        List<Veiculo> veiculos = veiculoService.listarVeiculos();
        List<Veiculo> veiculosDisponiveis = veiculos.stream()
                .filter(veiculo -> Boolean.FALSE.equals(veiculo.getReservado())).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(veiculosDisponiveis);
    }

    @Operation(description = "Faz reserva em um veiculo já existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva alterada com sucerro!"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel realizar a reserva.")
    })
    @PutMapping("/reservar/{id}")
    public ResponseEntity<String> reservarVeiculo(@PathVariable Integer id) {
        try {
            veiculoService.reservarVeiculo(id);
            return new ResponseEntity<>("Veículo reservado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao reservar veículo: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Remove veiculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad request. Veiculos reservados não podem ser removidos.")
    })
    @DeleteMapping("/{id}")
    public void removerVeiculo(@PathVariable Integer id) {

        Optional<Veiculo> veiculoReservado = repositorio.findById(id);
        if (veiculoReservado.isPresent()) {
            Veiculo veiculo = veiculoReservado.get();
            if (!veiculo.getReservado()) {
                repositorio.delete(veiculo);
            } else {
                throw new RuntimeException("Nao é possivel apagar");
            }
        }

    }
}
