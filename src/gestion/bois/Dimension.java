package gestion.bois;

public abstract class Dimension {

	protected int id;
	protected int longueur;
	protected int largeur;
	
	public Dimension(int id, int longueur, int largeur) {
		this.id = id;
		this.longueur = longueur;
		this.largeur = largeur;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int hashCode() {
		return this.id;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	
}
