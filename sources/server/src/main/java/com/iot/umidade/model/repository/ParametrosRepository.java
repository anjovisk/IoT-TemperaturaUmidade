package com.iot.umidade.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot.umidade.model.Parametro;
import com.iot.umidade.model.Parametro.Tipo;

public interface ParametrosRepository extends JpaRepository<Parametro, Long> {
	Parametro findByTipo(Tipo tipo);
}
