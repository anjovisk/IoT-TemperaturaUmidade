package com.iot.umidade.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iot.umidade.controller.dto.v1.ParametrosDTO;
import com.iot.umidade.service.ParametrosService;
import com.iot.umidade.service.TemperaturaUmidadeService;

@Controller("MainControllerV1")
public class MainController {
	private Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private ParametrosService parametroService;
	@Autowired
	private TemperaturaUmidadeService temperaturaUmidadeService;
	
	@RequestMapping(method = { RequestMethod.GET })
	public String obter(Model model) {
		logger.info("Obtendo parametros");
		ParametrosDTO parametros = ParametrosDTO.getInstance(parametroService.getParametros(), temperaturaUmidadeService.obterUmidade(), temperaturaUmidadeService.obterTemperatura());
		model.addAttribute("parametros", parametros);
		return "index";
	}
}
