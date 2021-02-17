package com.newcarshop.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newcarshop.springbatch.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
}
