package com.iot.umidade.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.iot.umidade.model.EstadoAparelho;
import com.iot.umidade.model.Parametro;
import com.iot.umidade.model.TemperaturaUmidade;
import com.iot.umidade.model.Parametro.Tipo;
import com.iot.umidade.model.repository.ParametrosRepository;
import com.iot.umidade.model.repository.TemperaturaUmidadeRepository;

@Service
public class TemperaturaUmidadeServiceImpl implements TemperaturaUmidadeService {
	@Autowired
	private TemperaturaUmidadeRepository temperaturaUmidadeRepository;
	@Autowired
	private ParametrosRepository parametroRepository;
	
	@Transactional
	public Optional<TemperaturaUmidade> salvar(TemperaturaUmidade temperaturaUmidade) {
		temperaturaUmidade.setData(LocalDateTime.now());
		return Optional.of(temperaturaUmidadeRepository.save(temperaturaUmidade));
	}
	
	@Override
	public EstadoAparelho calcularEstadoUmidificador(Collection<TemperaturaUmidade> historicoTemperaturaUmidade) {
		Parametro estadoUmidificador = parametroRepository.findByTipo(Tipo.ESTADO_UMIDIFICADOR);
		Parametro umidadeMaxima = parametroRepository.findByTipo(Tipo.UMIDADE_MAXIMA);
		Parametro umidadeMinima = parametroRepository.findByTipo(Tipo.UMIDADE_MINIMA);
		Function<TemperaturaUmidade, BigDecimal> mapperValor = TemperaturaUmidade::getUmidade;
		Predicate<BigDecimal> ligarSe = (b -> b.compareTo(new BigDecimal(umidadeMinima.getValor())) < 0);
		Predicate<BigDecimal> desligarSe = (b -> b.compareTo(new BigDecimal(umidadeMaxima.getValor())) > 0);
		return calcularEstadoAparelho(historicoTemperaturaUmidade, estadoUmidificador, 
				umidadeMinima, umidadeMaxima, mapperValor,
				ligarSe, desligarSe);
	}
	
	@Override
	public EstadoAparelho calcularEstadoArCondicionado(Collection<TemperaturaUmidade> historicoTemperaturaUmidade) {
		Parametro estadoArCondicionado = parametroRepository.findByTipo(Tipo.ESTADO_AR_CONDICIONADO);
		Parametro temperaturaMaxima = parametroRepository.findByTipo(Tipo.TEMPERATURA_MAXIMA);
		Parametro temperaturaMinima = parametroRepository.findByTipo(Tipo.TEMPERATURA_MINIMA);
		Function<TemperaturaUmidade, BigDecimal> mapperValor = TemperaturaUmidade::getTemperatura;
		Predicate<BigDecimal> ligarSe = (b -> b.compareTo(new BigDecimal(temperaturaMaxima.getValor())) > 0);
		Predicate<BigDecimal> desligarSe = (b -> b.compareTo(new BigDecimal(temperaturaMinima.getValor())) < 0);
		return calcularEstadoAparelho(historicoTemperaturaUmidade, estadoArCondicionado, 
				temperaturaMinima, temperaturaMaxima, mapperValor,
				ligarSe, desligarSe);
	}
	
	private EstadoAparelho calcularEstadoAparelho(Collection<TemperaturaUmidade> historicoTemperaturaUmidade, 
			Parametro estadoAparelho, Parametro valorAceitavelInferior, Parametro valorAceitavelSuperior,
			Function<TemperaturaUmidade, BigDecimal> mapperValor,
			Predicate<BigDecimal> ligarSe,
			Predicate<BigDecimal> desligarSe) {
		if (historicoTemperaturaUmidade.size() < 10) {
			return EstadoAparelho.OFF;
		}
		if (!Objects.isNull(estadoAparelho) && !Objects.isNull(estadoAparelho.getValor())
				&& !EstadoAparelho.AUTO.equals(EstadoAparelho.valueOf(estadoAparelho.getValor()))) {
			return EstadoAparelho.valueOf(estadoAparelho.getValor());
		}
		if (Objects.isNull(valorAceitavelSuperior) || Objects.isNull(valorAceitavelSuperior.getValor())
				|| Objects.isNull(valorAceitavelInferior) || Objects.isNull(valorAceitavelInferior.getValor())) {
			return EstadoAparelho.OFF;
		}
		BigDecimal sum = historicoTemperaturaUmidade.stream().map(mapperValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal media = sum.divide(new BigDecimal(historicoTemperaturaUmidade.size()), 2, RoundingMode.HALF_UP);
		if (ligarSe.test(media)) {
			return EstadoAparelho.ON;
		}
		if (desligarSe.test(media)) {
			return EstadoAparelho.OFF;
		}
		return EstadoAparelho.NONE;
	}

	@Override
	public List<TemperaturaUmidade> obterUltimosRegistros() {
		Page<TemperaturaUmidade> page = temperaturaUmidadeRepository.findAll(PageRequest.of(0, 10, Sort.by(Order.desc("data"))));
		return page.toList();
	}
	
	@Override
	public BigDecimal obterUmidade() {
		List<TemperaturaUmidade> ultimosRegistros = obterUltimosRegistros();
		BigDecimal sum = ultimosRegistros.stream().map(TemperaturaUmidade::getUmidade)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal media = sum.divide(new BigDecimal(ultimosRegistros.size()), 2, RoundingMode.HALF_UP);
		return media;
	}
	
	@Override
	public BigDecimal obterTemperatura() {
		List<TemperaturaUmidade> ultimosRegistros = obterUltimosRegistros();
		BigDecimal sum = ultimosRegistros.stream().map(TemperaturaUmidade::getTemperatura)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal media = sum.divide(new BigDecimal(ultimosRegistros.size()), 2, RoundingMode.HALF_UP);
		return media;
	}
}
