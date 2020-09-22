package com.iot.umidade.controller.dto.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import com.iot.umidade.model.EstadoAparelho;
import com.iot.umidade.model.Parametro;

public class ParametrosDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2757579190405305844L;
	
	public ParametrosDTO() {}
	
	public static ParametrosDTO getInstance(Collection<Parametro> parametros) {
		ParametrosDTO resultado = new ParametrosDTO();
		parametros.forEach(parametro -> {
			if (!Objects.isNull(parametro.getValor())) {
				switch (parametro.getTipo()) {
				case ESTADO_AR_CONDICIONADO:
					resultado.setEstadoArCondicionado(EstadoAparelho.valueOf(parametro.getValor()));
					break;
				case ESTADO_UMIDIFICADOR:
					resultado.setEstadoUmidificador(EstadoAparelho.valueOf(parametro.getValor()));
					break;
				case TEMPERATURA_MAXIMA:
					resultado.setTemperaturaMaxima(new BigDecimal(parametro.getValor()));
					break;
				case TEMPERATURA_MINIMA:
					resultado.setTemperaturaMinima(new BigDecimal(parametro.getValor()));
					break;
				case UMIDADE_MAXIMA:
					resultado.setUmidadeMaxima(new BigDecimal(parametro.getValor()));
					break;
				case UMIDADE_MINIMA:
					resultado.setUmidadeMinima(new BigDecimal(parametro.getValor()));
					break;
				}
			}
		});
		return resultado;
	}
	
	public static ParametrosDTO getInstance(Collection<Parametro> parametros, BigDecimal umidadeAtual, BigDecimal temperaturaAtual) {
		ParametrosDTO resultado = getInstance(parametros);
		resultado.setUmidadeAtual(umidadeAtual);
		resultado.setTemperaturaAtual(temperaturaAtual);
		return resultado;
	}
	
	private EstadoAparelho estadoUmidificador = EstadoAparelho.AUTO;
	private EstadoAparelho estadoArCondicionado = EstadoAparelho.AUTO;
	private BigDecimal umidadeMinima = BigDecimal.valueOf(55);
	private BigDecimal umidadeMaxima = BigDecimal.valueOf(65);
	private BigDecimal temperaturaMinima = BigDecimal.valueOf(23);
	private BigDecimal temperaturaMaxima = BigDecimal.valueOf(26);
	private BigDecimal umidadeAtual = BigDecimal.ZERO;
	private BigDecimal temperaturaAtual = BigDecimal.ZERO;
	
	public EstadoAparelho getEstadoUmidificador() {
		return estadoUmidificador;
	}
	public void setEstadoUmidificador(EstadoAparelho estadoUmidificador) {
		this.estadoUmidificador = estadoUmidificador;
	}
	public EstadoAparelho getEstadoArCondicionado() {
		return estadoArCondicionado;
	}
	public void setEstadoArCondicionado(EstadoAparelho estadoArCondicionado) {
		this.estadoArCondicionado = estadoArCondicionado;
	}
	public BigDecimal getUmidadeMinima() {
		return umidadeMinima;
	}
	public void setUmidadeMinima(BigDecimal umidadeMinima) {
		this.umidadeMinima = umidadeMinima;
	}
	public BigDecimal getUmidadeMaxima() {
		return umidadeMaxima;
	}
	public void setUmidadeMaxima(BigDecimal umidadeMaxima) {
		this.umidadeMaxima = umidadeMaxima;
	}
	public BigDecimal getTemperaturaMinima() {
		return temperaturaMinima;
	}
	public void setTemperaturaMinima(BigDecimal temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}
	public BigDecimal getTemperaturaMaxima() {
		return temperaturaMaxima;
	}
	public void setTemperaturaMaxima(BigDecimal temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}
	public BigDecimal getUmidadeAtual() {
		return umidadeAtual;
	}

	public void setUmidadeAtual(BigDecimal umidadeAtual) {
		this.umidadeAtual = umidadeAtual;
	}

	public BigDecimal getTemperaturaAtual() {
		return temperaturaAtual;
	}

	public void setTemperaturaAtual(BigDecimal temperaturaAtual) {
		this.temperaturaAtual = temperaturaAtual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estadoArCondicionado == null) ? 0 : estadoArCondicionado.hashCode());
		result = prime * result + ((estadoUmidificador == null) ? 0 : estadoUmidificador.hashCode());
		result = prime * result + ((temperaturaAtual == null) ? 0 : temperaturaAtual.hashCode());
		result = prime * result + ((temperaturaMaxima == null) ? 0 : temperaturaMaxima.hashCode());
		result = prime * result + ((temperaturaMinima == null) ? 0 : temperaturaMinima.hashCode());
		result = prime * result + ((umidadeAtual == null) ? 0 : umidadeAtual.hashCode());
		result = prime * result + ((umidadeMaxima == null) ? 0 : umidadeMaxima.hashCode());
		result = prime * result + ((umidadeMinima == null) ? 0 : umidadeMinima.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametrosDTO other = (ParametrosDTO) obj;
		if (estadoArCondicionado != other.estadoArCondicionado)
			return false;
		if (estadoUmidificador != other.estadoUmidificador)
			return false;
		if (temperaturaAtual == null) {
			if (other.temperaturaAtual != null)
				return false;
		} else if (!temperaturaAtual.equals(other.temperaturaAtual))
			return false;
		if (temperaturaMaxima == null) {
			if (other.temperaturaMaxima != null)
				return false;
		} else if (!temperaturaMaxima.equals(other.temperaturaMaxima))
			return false;
		if (temperaturaMinima == null) {
			if (other.temperaturaMinima != null)
				return false;
		} else if (!temperaturaMinima.equals(other.temperaturaMinima))
			return false;
		if (umidadeAtual == null) {
			if (other.umidadeAtual != null)
				return false;
		} else if (!umidadeAtual.equals(other.umidadeAtual))
			return false;
		if (umidadeMaxima == null) {
			if (other.umidadeMaxima != null)
				return false;
		} else if (!umidadeMaxima.equals(other.umidadeMaxima))
			return false;
		if (umidadeMinima == null) {
			if (other.umidadeMinima != null)
				return false;
		} else if (!umidadeMinima.equals(other.umidadeMinima))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParametrosDTO [estadoUmidificador=" + estadoUmidificador + ", estadoArCondicionado="
				+ estadoArCondicionado + ", umidadeMinima=" + umidadeMinima + ", umidadeMaxima=" + umidadeMaxima
				+ ", temperaturaMinima=" + temperaturaMinima + ", temperaturaMaxima=" + temperaturaMaxima
				+ ", umidadeAtual=" + umidadeAtual + ", temperaturaAtual=" + temperaturaAtual + "]";
	}
}
