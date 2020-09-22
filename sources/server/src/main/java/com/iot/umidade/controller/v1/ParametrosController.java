package com.iot.umidade.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iot.umidade.controller.dto.v1.ParametrosDTO;
import com.iot.umidade.model.Parametro.Tipo;
import com.iot.umidade.service.ParametrosService;
import com.iot.umidade.service.TemperaturaUmidadeService;

@Controller("ParametrosControllerV1")
@RequestMapping("parametros")
public class ParametrosController {
	private Logger logger = LoggerFactory.getLogger(ParametrosController.class);
	
	@Autowired
	private ParametrosService parametroService;
	@Autowired
	private TemperaturaUmidadeService temperaturaUmidadeService;
	
	@RequestMapping(method = { RequestMethod.GET })
	public String obter(Model model) {
		logger.info("Obtendo parametros");
		ParametrosDTO parametros = ParametrosDTO.getInstance(parametroService.getParametros());
		model.addAttribute("parametros", parametros);
		return "parametros";
	}
	
	@RequestMapping(method = { RequestMethod.POST })
	public String obter(@ModelAttribute ParametrosDTO parametros, BindingResult result, Model model) {
		logger.info(String.format("Salvando parametros: %s", parametros));
		parametroService.updateParametro(Tipo.ESTADO_UMIDIFICADOR, parametros.getEstadoUmidificador().name());
		parametroService.updateParametro(Tipo.UMIDADE_MINIMA, parametros.getUmidadeMinima().toPlainString());
		parametroService.updateParametro(Tipo.UMIDADE_MAXIMA, parametros.getUmidadeMaxima().toPlainString());
		parametroService.updateParametro(Tipo.ESTADO_AR_CONDICIONADO, parametros.getEstadoArCondicionado().name());
		parametroService.updateParametro(Tipo.TEMPERATURA_MINIMA, parametros.getTemperaturaMinima().toPlainString());
		parametroService.updateParametro(Tipo.TEMPERATURA_MAXIMA, parametros.getTemperaturaMaxima().toPlainString());
		parametros.setUmidadeAtual(temperaturaUmidadeService.obterUmidade());
		parametros.setTemperaturaAtual(temperaturaUmidadeService.obterTemperatura());
		model.addAttribute("parametros", parametros);
		return "index";
	}
}
