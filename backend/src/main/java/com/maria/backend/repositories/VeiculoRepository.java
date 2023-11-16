package com.maria.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maria.backend.model.Veiculo;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
    Optional<Veiculo> findById(Integer id);

    boolean existsById(Integer id);
}