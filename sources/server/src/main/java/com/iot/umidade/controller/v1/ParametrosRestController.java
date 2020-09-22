package com.iot.umidade.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iot.umidade.controller.dto.v1.ParametrosDTO;
import com.iot.umidade.service.ParametrosService;

@RestController("ParametrosRestControllerV1")
@RequestMapping("/public/v1/parametros")
public class ParametrosRestController {
	private Logger logger = LoggerFactory.getLogger(ParametrosRestController.class);
	
	@Autowired
	private ParametrosService parametroService;
	
	@RequestMapping(method = { RequestMethod.GET })
	public ResponseEntity<ParametrosDTO> obter() {
		logger.info("Obtendo parametros");
		return ResponseEntity.ok(ParametrosDTO.getInstance(parametroService.getParametros()));
	}
}
