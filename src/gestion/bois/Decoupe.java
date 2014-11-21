package gestion.bois;

public class Decoupe {

	int x;
	int y;
	int idPlanche;
	int idCommande;
	
	public Decoupe(int x, int y, int idPlanche, int idCommande) {
		this.x = x;
		this.y = y;
		this.idPlanche = idPlanche;
		this.idCommande = idCommande;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIdPlanche() {
		return idPlanche;
	}

	public int getIdCommande() {
		return idCommande;
	}
}
