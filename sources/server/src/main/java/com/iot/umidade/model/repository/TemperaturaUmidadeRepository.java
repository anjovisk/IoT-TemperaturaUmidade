package com.iot.umidade.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot.umidade.model.TemperaturaUmidade;

public interface TemperaturaUmidadeRepository extends JpaRepository<TemperaturaUmidade, Long> {

}
