package com.maria.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maria.backend.dtos.VeiculoDto;
import com.maria.backend.model.Veiculo;
import com.maria.backend.repositories.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo cadastrarVeiculo(VeiculoDto veiculoDto) {
        var veiculo = new Veiculo();
        BeanUtils.copyProperties(veiculoDto, veiculo);
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

    public ResponseEntity<String> reservarVeiculo(Integer id) {
        try {
            Optional<Veiculo> veiculoReservado = veiculoRepository.findById(id);

            if (veiculoReservado.isPresent()) {
                Veiculo veiculo = veiculoReservado.get();
                veiculo.setReservado(true);
                veiculoRepository.save(veiculo);
                return ResponseEntity.ok("Veículo reservado com sucesso.");
            } else {
                return ResponseEntity.status(404).body("Veículo não encontrado com o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao reservar veículo: " + e.getMessage());
        }
    }

    public void apagarVeiculo(Integer id) {
        Optional<Veiculo> veiculoReservado = veiculoRepository.findById(id);

        if (veiculoReservado.isPresent()) {
            Veiculo veiculo = veiculoReservado.get();
            veiculoRepository.delete(veiculo);
        } else {
            throw new RuntimeException("Nao é possivel apagar");
        }
    }

}
