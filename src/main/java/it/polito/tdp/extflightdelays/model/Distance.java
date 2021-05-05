package it.polito.tdp.extflightdelays.model;

public class Distance {
	private int a1_id;
	private int a2_id;
	private double media;
	
	public Distance(int a1_id, int a2_id, double media) {
		super();
		this.a1_id = a1_id;
		this.a2_id = a2_id;
		this.media = media;
	}
	public int getA1_id() {
		return a1_id;
	}
	public void setA1_id(int a1_id) {
		this.a1_id = a1_id;
	}
	public int getA2_id() {
		return a2_id;
	}
	public void setA2_id(int a2_id) {
		this.a2_id = a2_id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a1_id;
		result = prime * result + a2_id;
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
		Distance other = (Distance) obj;
		if (a1_id != other.a1_id)
			return false;
		if (a2_id != other.a2_id)
			return false;
		return true;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}
	@Override
	public String toString() {
		return "a1_id=" + a1_id + " a2_id=" + a2_id + " media=" + media + "\n";
	}
	
	
}
