package com.iot.umidade.service;

import java.util.Collection;

import com.iot.umidade.model.Parametro;
import com.iot.umidade.model.Parametro.Tipo;

public interface ParametrosService {
	Collection<Parametro> getParametros();
	void updateParametro(Tipo tipo, String valor);
}
