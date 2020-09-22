package com.iot.umidade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "parametros")
public class Parametro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1032205732648177182L;
	
	public enum Tipo {
		ESTADO_UMIDIFICADOR,
		ESTADO_AR_CONDICIONADO,
		UMIDADE_MINIMA,
		UMIDADE_MAXIMA,
		TEMPERATURA_MINIMA,
		TEMPERATURA_MAXIMA
	}
	
	public Parametro() { }
	
	private Parametro(Tipo tipo) {
		this.tipo = tipo;
	}
	
	private Parametro(Tipo tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public static Parametro getInstance(Tipo tipo) {
		Parametro parametro = new Parametro(tipo);
		return parametro;
	}
	
	public static Parametro getInstance(Tipo tipo, String valor) {
		Parametro parametro = new Parametro(tipo, valor);
		return parametro;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "tipo", nullable = false)
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	@Column(name = "valor", nullable = true)
	@Lob
	private String valor;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Parametro other = (Parametro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipo != other.tipo)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
