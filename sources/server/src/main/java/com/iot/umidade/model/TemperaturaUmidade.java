package com.iot.umidade.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "historico_temperatura_umidade")
public class TemperaturaUmidade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5234846010572108596L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "data", nullable = false)
	private LocalDateTime data;
	@Column(name = "temperatura", nullable = false)
	private BigDecimal temperatura;
	@Column(name = "umidade", nullable = false)
	private BigDecimal umidade;
	
	public TemperaturaUmidade() {
		
	}
	
	private TemperaturaUmidade(Long id, LocalDateTime data, BigDecimal temperatura, BigDecimal umidade) {
		this.id = id;
		this.data = data;
		this.temperatura = temperatura;
		this.umidade = umidade;
	}
	
	public static TemperaturaUmidade getInstance(Long id, LocalDateTime data, BigDecimal temperatura, BigDecimal umidade) {
		return new TemperaturaUmidade(id, data, temperatura, umidade);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public BigDecimal getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}
	public BigDecimal getUmidade() {
		return umidade;
	}
	public void setUmidade(BigDecimal umidade) {
		this.umidade = umidade;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((temperatura == null) ? 0 : temperatura.hashCode());
		result = prime * result + ((umidade == null) ? 0 : umidade.hashCode());
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
		TemperaturaUmidade other = (TemperaturaUmidade) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (temperatura == null) {
			if (other.temperatura != null)
				return false;
		} else if (!temperatura.equals(other.temperatura))
			return false;
		if (umidade == null) {
			if (other.umidade != null)
				return false;
		} else if (!umidade.equals(other.umidade))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TemperaturaUmidade [id=" + id + ", data=" + data + ", temperatura=" + temperatura + ", umidade="
				+ umidade + "]";
	}
}
