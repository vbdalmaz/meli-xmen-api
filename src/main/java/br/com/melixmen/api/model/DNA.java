package br.com.melixmen.api.model;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.melixmen.api.model.enumeration.SpecieType;

public class DNA implements Serializable {

	private static final long serialVersionUID = 2019010901L;
	
	public DNA() {
		super();
	}
	
	public DNA(String[] dna, SpecieType specieType) {
		super();
		this.dna = dna;
		this.specieType = specieType;
	}


	@JsonProperty(value = "Dna", required=true)
	private String[] dna;

	@JsonProperty(value = "specieType", required=false)
	private SpecieType specieType;

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	public SpecieType getSpecieType() {
		return specieType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(dna);
		result = prime * result + ((specieType == null) ? 0 : specieType.hashCode());
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
		DNA other = (DNA) obj;
		if (!Arrays.equals(dna, other.dna))
			return false;
		if (specieType != other.specieType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DNA [dna=" + Arrays.toString(dna) + ", specieType=" + specieType + "]";
	}
}
