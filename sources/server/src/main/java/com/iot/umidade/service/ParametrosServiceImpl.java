package com.iot.umidade.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.umidade.model.Parametro;
import com.iot.umidade.model.Parametro.Tipo;
import com.iot.umidade.model.repository.ParametrosRepository;

@Service
public class ParametrosServiceImpl implements ParametrosService {
	@Autowired
	private ParametrosRepository parametrosRepository;
	
	@Override
	public Collection<Parametro> getParametros() {
		List<Parametro> parametros = parametrosRepository.findAll();
		return parametros;
	}

	@Override
	public void updateParametro(Tipo tipo, String valor) {
		Parametro parametro = parametrosRepository.findByTipo(tipo);
		if (Objects.isNull(parametro)) {
			parametro = Parametro.getInstance(tipo);
		}
		parametro.setValor(valor);
		parametrosRepository.save(parametro);
	}

}
