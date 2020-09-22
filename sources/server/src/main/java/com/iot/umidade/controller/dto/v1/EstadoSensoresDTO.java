package com.iot.umidade.controller.dto.v1;

import java.io.Serializable;
import java.util.Objects;

import com.iot.umidade.model.EstadoAparelho;

public class EstadoSensoresDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6064852583217367141L;
	
	private final EstadoAparelho estadoUmidificador;
	private final EstadoAparelho estadoArCondicionado;
	
	private EstadoSensoresDTO(EstadoAparelho estadoUmidificador, EstadoAparelho estadoArCondicionado) {
		if (EstadoAparelho.AUTO.equals(estadoUmidificador) || EstadoAparelho.AUTO.equals(estadoArCondicionado)) {
			throw new IllegalArgumentException(String.format("O estado %s não é permitido.", EstadoAparelho.AUTO));
		}
		this.estadoUmidificador = Objects.requireNonNull(estadoUmidificador);
		this.estadoArCondicionado = Objects.requireNonNull(estadoArCondicionado);
	}
	
	public static EstadoSensoresDTO getInstance(EstadoAparelho estadoUmidificador, EstadoAparelho estadoArCondicionado) {
		return new EstadoSensoresDTO(estadoUmidificador, estadoArCondicionado);
	}

	public EstadoAparelho getEstadoUmidificador() {
		return estadoUmidificador;
	}

	public EstadoAparelho getEstadoArCondicionado() {
		return estadoArCondicionado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estadoArCondicionado == null) ? 0 : estadoArCondicionado.hashCode());
		result = prime * result + ((estadoUmidificador == null) ? 0 : estadoUmidificador.hashCode());
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
		EstadoSensoresDTO other = (EstadoSensoresDTO) obj;
		if (estadoArCondicionado != other.estadoArCondicionado)
			return false;
		if (estadoUmidificador != other.estadoUmidificador)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstadoSensoresDTO [estadoUmidificador=" + estadoUmidificador + ", estadoArCondicionado="
				+ estadoArCondicionado + "]";
	}
}
