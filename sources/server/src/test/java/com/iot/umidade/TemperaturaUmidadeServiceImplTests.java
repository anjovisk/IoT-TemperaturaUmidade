package com.iot.umidade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import com.iot.umidade.model.EstadoAparelho;
import com.iot.umidade.model.Parametro;
import com.iot.umidade.model.Parametro.Tipo;
import com.iot.umidade.model.TemperaturaUmidade;
import com.iot.umidade.model.repository.ParametrosRepository;
import com.iot.umidade.model.repository.TemperaturaUmidadeRepository;
import com.iot.umidade.service.TemperaturaUmidadeService;
import com.iot.umidade.service.TemperaturaUmidadeServiceImpl;

@SpringBootTest
@TestPropertySource("file:C:/Sistemas/IoT/application.properties")
class TemperaturaUmidadeServiceImplTests {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
 
        @Bean
        public TemperaturaUmidadeService temperaturaUmidadeService() {
            return new TemperaturaUmidadeServiceImpl();
        }
    }
	
	@Autowired
	private TemperaturaUmidadeService temperaturaUmidadeService;
	@MockBean
	private TemperaturaUmidadeRepository temperaturaUmidadeRepository;
	@MockBean 
	private ParametrosRepository parametroRepository;
	
	@BeforeEach
	public void setUp() {
		setUpParametros();
	}
	
	private void setUpParametros() {
		Parametro parametroEstadoArCondicionado = Parametro.getInstance(Tipo.ESTADO_AR_CONDICIONADO, "AUTO");
		Parametro parametroEstadoUmidificador = Parametro.getInstance(Tipo.ESTADO_UMIDIFICADOR, "AUTO");
		Parametro parametroTemperaturaMaxima = Parametro.getInstance(Tipo.TEMPERATURA_MAXIMA, "26");
		Parametro parametroTemperaturaMinima = Parametro.getInstance(Tipo.TEMPERATURA_MINIMA, "23");
		Parametro parametroUmidadeMaxima = Parametro.getInstance(Tipo.UMIDADE_MAXIMA, "65");
		Parametro parametroUmidadeMinima = Parametro.getInstance(Tipo.UMIDADE_MINIMA, "55");
		
	    Mockito.when(parametroRepository.findByTipo(parametroEstadoArCondicionado.getTipo()))
	      .thenReturn(parametroEstadoArCondicionado);
	    Mockito.when(parametroRepository.findByTipo(parametroEstadoUmidificador.getTipo()))
	      .thenReturn(parametroEstadoUmidificador);
	    Mockito.when(parametroRepository.findByTipo(parametroTemperaturaMaxima.getTipo()))
	      .thenReturn(parametroTemperaturaMaxima);
	    Mockito.when(parametroRepository.findByTipo(parametroTemperaturaMinima.getTipo()))
	      .thenReturn(parametroTemperaturaMinima);
	    Mockito.when(parametroRepository.findByTipo(parametroUmidadeMaxima.getTipo()))
	      .thenReturn(parametroUmidadeMaxima);
	    Mockito.when(parametroRepository.findByTipo(parametroUmidadeMinima.getTipo()))
	      .thenReturn(parametroUmidadeMinima);
	}

	@Test
	void quandoUmidadeAcima_entaoDesligaUmidificador1() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					null, BigDecimal.valueOf(66));
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.OFF, temperaturaUmidadeService.calcularEstadoUmidificador(dados));
	}
	
	@Test
	void quandoUmidadeAcima_entaoDesligaUmidificador2() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					null, BigDecimal.valueOf((id % 2 == 0) ? 66 : 64.1));
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.OFF, temperaturaUmidadeService.calcularEstadoUmidificador(dados));
	}

	@Test
	void quandoUmidadeAbaixo_entaoDesligaUmidificador() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					null, BigDecimal.valueOf(54));
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.ON, temperaturaUmidadeService.calcularEstadoUmidificador(dados));
	}
	
	@Test
	void quandoUmidadeAceitavel_entaoMantemEstadoUmidificador1() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					null, BigDecimal.valueOf(60));
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.NONE, temperaturaUmidadeService.calcularEstadoUmidificador(dados));
	}
	
	@Test
	void quandoUmidadeAceitavel_entaoMantemEstadoUmidificador2() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					null, BigDecimal.valueOf((id % 2 == 0) ? 66 : 64));
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.NONE, temperaturaUmidadeService.calcularEstadoUmidificador(dados));
	}
	
	@Test
	void quandoTemperaturaAcima_entaoLigaArCondicionado1() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					BigDecimal.valueOf(27), null);
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.ON, temperaturaUmidadeService.calcularEstadoArCondicionado(dados));
	}
	
	@Test
	void quandoTemperaturaAcima_entaoLigaArCondicionado2() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					BigDecimal.valueOf((id % 2 == 0) ? 27 : 25.01), null);
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.ON, temperaturaUmidadeService.calcularEstadoArCondicionado(dados));
	}

	@Test
	void quandoTemperaturaAbaixo_entaoDesligaArCondigionado() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					BigDecimal.valueOf(22.9), null);
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.OFF, temperaturaUmidadeService.calcularEstadoArCondicionado(dados));
	}
	
	@Test
	void quandoTemperaturaAceitavel_entaoMantemEstadoArCondicionado1() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					BigDecimal.valueOf(24), null);
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.NONE, temperaturaUmidadeService.calcularEstadoArCondicionado(dados));
	}
	
	@Test
	void quandoTemperaturaAceitavel_entaoMantemEstadoArCondicionado2() {
		List<TemperaturaUmidade> dados = new ArrayList<>();
		for (Long id = 1L; id < 11; id++) {
			TemperaturaUmidade dado = TemperaturaUmidade.getInstance(id, LocalDateTime.now(), 
					BigDecimal.valueOf((id % 2 == 0) ? 26 : 24), null);
			dados.add(dado);
		}
		assertEquals(EstadoAparelho.NONE, temperaturaUmidadeService.calcularEstadoArCondicionado(dados));
	}
}
