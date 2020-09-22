package com.iot.umidade.controller.v1;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iot.umidade.controller.dto.v1.EstadoSensoresDTO;
import com.iot.umidade.model.TemperaturaUmidade;
import com.iot.umidade.service.TemperaturaUmidadeService;

@RestController("EstadoAparelhosControllerV1")
@RequestMapping("/public/v1/estadoAparelhos")
public class EstadoAparelhosController {
	private Logger logger = LoggerFactory.getLogger(EstadoAparelhosController.class);
	
	@Autowired
	private TemperaturaUmidadeService temperaturaUmidadeService;
	
	@RequestMapping(method = { RequestMethod.GET })
	public ResponseEntity<EstadoSensoresDTO> obter() {
		logger.info("Obtendo estado dos aparelhos");
		Collection<TemperaturaUmidade> ultimosRegistros = temperaturaUmidadeService.obterUltimosRegistros();
		return ResponseEntity.ok(EstadoSensoresDTO.getInstance(
				temperaturaUmidadeService.calcularEstadoUmidificador(ultimosRegistros),
				temperaturaUmidadeService.calcularEstadoArCondicionado(ultimosRegistros)));
	}
}
