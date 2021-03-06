package gestion.bois;

import java.util.Vector;

public class Commande extends Dimension {

	protected int quantite;
	protected Boolean rejet;
	Vector<Decoupe> decoupes;
	
	public Commande(int id, int longueur, int largeur, int quantite) {
		super(id, longueur, largeur);
		this.quantite = quantite;
		this.rejet = false;
		this.decoupes = new Vector<Decoupe>();
	}

	public boolean equals(Object o) {
		if(o instanceof Commande) {
			Commande c = (Commande) o;
			if(c.id == this.id)
				return true;
			else
				return false;
		}
		return true;
	}
	
	public Vector<Decoupe> getDecoupes() {
		return this.decoupes;
	}
	
	public Boolean getRejet() {
		return this.rejet;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	public void decQuantite(){
		this.quantite--;
	}

	public void setRejet(Boolean rejet) {
		this.rejet = rejet;
	}

	public void setDecoupes(Vector<Decoupe> decoupes) {
		this.decoupes = decoupes;
	}
}