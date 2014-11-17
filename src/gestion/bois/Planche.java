package gestion.bois;

public class Planche extends Dimension{

	protected float prix;
	
	public Planche(int id,int longueur,int largeur,int prix){
		super(id,longueur,largeur);
		this.prix=prix;
	}
	
}
