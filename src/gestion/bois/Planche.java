package gestion.bois;

import java.util.Vector;

public class Planche extends Dimension {

	private float prix;
	Vector<Decoupe> decoupes;
	
	public Planche(int id, int longueur, int largeur, float prix) {
		super(id, longueur, largeur);
		this.prix = prix;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Planche) {
			Planche p = (Planche) o;
			if(p.id == this.id)
				return true;
			else
				return false;
		}
		return true;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public float getPrix() {
		return prix;
	}	

	public Vector<Decoupe> getDecoupes() {
		return this.decoupes;
	}

	public void setDecoupes(Vector<Decoupe> decoupes) {
		this.decoupes = decoupes;
	}
}
