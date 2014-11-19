package gestion.bois;

public class Decoupe {

	int x;
	int y;
	int idPlanche;
	
	public Decoupe(int x, int y, int idPlanche) {
		this.x = x;
		this.y = y;
		this.idPlanche = idPlanche;
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
}
