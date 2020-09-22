package com.iot.umidade.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.iot.umidade.model.EstadoAparelho;
import com.iot.umidade.model.TemperaturaUmidade;

public interface TemperaturaUmidadeService {
	Optional<TemperaturaUmidade> salvar(TemperaturaUmidade temperaturaUmidade);
	List<TemperaturaUmidade> obterUltimosRegistros();
	EstadoAparelho calcularEstadoUmidificador(Collection<TemperaturaUmidade> historicoTemperaturaUmidade);
	EstadoAparelho calcularEstadoArCondicionado(Collection<TemperaturaUmidade> historicoTemperaturaUmidade);
	BigDecimal obterUmidade();
	BigDecimal obterTemperatura();
}
