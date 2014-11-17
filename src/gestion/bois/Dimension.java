package gestion.bois;

public abstract class Dimension {

	protected int id;
	protected int longueur;
	protected int largeur;
	
	public Dimension(int id,int longueur,int largeur){
		this.id=id;
		this.longueur=longueur;
		this.largeur=largeur;
	}
	
}
