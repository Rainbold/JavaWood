package gestion.bois;

public class Commande extends Dimension {

	protected int quantite;
	protected Boolean rejet;
	
	public Commande(int id, int longueur, int largeur, int quantite, Boolean rejet) {
		super(id, longueur, largeur);
		this.quantite = quantite;
		this.rejet = rejet;
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
}