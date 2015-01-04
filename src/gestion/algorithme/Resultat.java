package gestion.algorithme;

import gestion.bois.Commande;
import gestion.bois.Decoupe;
import gestion.bois.Planche;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public abstract class Resultat {
	
	// Permet de creer un fichier xml contenant les resultats des methodes
	public static void xml(String filename, int nbPlanche, Planche planche, int methode, List<Commande> commandes) {
		
		int nbRejet = 0;
		FileWriter fwriter = null;
		
		@SuppressWarnings("unchecked") // Necessaire pour faire le tableau de List<Decoupe>
		List<Decoupe>[] planches = new List[nbPlanche];
		
		List<Commande> crejet = new ArrayList<Commande>();
		
		Iterator<Commande> ic;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		for(int i = 0; i < nbPlanche; i++) {
			// Creation des List dans le tableau de List 
			planches[i] = new ArrayList<Decoupe>();
		}
		
		ic = commandes.iterator();
		
		// Remplissage de planches avec les objets "Decoupe" et remplissage de crejet avec les commandes rejetees
		while(ic.hasNext()) {
			Commande c = ic.next();
			
			if(!c.getRejet()) { // Commande non rejetee
				Iterator<Decoupe> id = c.getDecoupes().iterator();
			 
				while(id.hasNext()) { // Pour chaque decoupe, on ajoute cette derniere dans la bonne planche
					Decoupe d = id.next();
					planches[d.getIdPlanche()-1].add(d);
				}
			}
			else { // Commande rejetee
				nbRejet++;
				crejet.add(c);
			}
		}
		
		try { // Ecriture des resultats
			fwriter = new FileWriter(filename);
			writer = factory.createXMLStreamWriter(fwriter);
			
			writer.writeStartDocument("1.0");
			writer.writeCharacters("\n");
			
			writer.writeStartElement("simulation");
			writer.writeAttribute("fournisseur", Integer.toString(planche.getId()));
			writer.writeAttribute("prix", Float.toString(planche.getPrix() * nbPlanche));
			writer.writeAttribute("rejets", Integer.toString(nbRejet));
			writer.writeAttribute("algorithme", Integer.toString(methode));
			writer.writeCharacters("\n    ");
		
			// Rejets
			writer.writeStartElement("rejets");
			writer.writeCharacters("\n    ");
			
			Iterator<Commande> icrejet = crejet.iterator();
			
			while(icrejet.hasNext()) {	
				Commande c = icrejet.next();
				writer.writeCharacters("    ");
				writer.writeEmptyElement("rejet");
				writer.writeAttribute("commande", Integer.toString(c.getId()));
				writer.writeCharacters("\n    ");
			}
			
			writer.writeEndElement(); // rejets
			writer.writeCharacters("\n    ");
			
			// Planches
			writer.writeStartElement("planches");
			writer.writeCharacters("\n    ");
			
			for(int i = 0; i < nbPlanche; i++) {
				Iterator<Decoupe> id = planches[i].iterator();
				
				// Planche
				writer.writeCharacters("    ");
				writer.writeStartElement("planche");	
				writer.writeAttribute("id", Integer.toString(i+1));
				writer.writeCharacters("\n        ");
				
				while(id.hasNext()) {
					Decoupe d = id.next();
					// Decoupe
					writer.writeCharacters("    ");
					writer.writeEmptyElement("decoupe");	
					writer.writeAttribute("commande", Integer.toString(d.getIdCommande()));
					writer.writeAttribute("x", Integer.toString(d.getX()));
					writer.writeAttribute("y", Integer.toString(d.getY()));
					writer.writeCharacters("\n        ");
				}
				writer.writeEndElement(); // planche 
				writer.writeCharacters("\n    ");
			}
			
			writer.writeEndElement(); // planches
			writer.writeCharacters("\n");
			
			writer.writeEndElement(); // simulation
			writer.writeCharacters("\n");
			
			writer.writeEndDocument();
			
			writer.close();
			fwriter.close();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void svg(String filename, int nbPlancheMax, Planche planche, List<Commande> commandes) {
			
		FileWriter fwriter = null;
		
		int longueur = planche.getLongueur();
		int largeur = planche.getLargeur();
		
		int xPlanche = 50; // Distance horizontale des planches 
		int yPlanche = 30; // Distance verticale de la planche courante
		
		String contourTaille = "2px";
		String contourCouleur = "black";
		
		int interPlancheX = 50; // Distance entre 2 planches sur X
		
		String plancheCouleur = "lightblue";
		
		int titreMarge = 10; // Nombre de pixel au dessus de la planche
		String textTaille = "14px";
		String textCouleur = "black";
		
		String decoupeCouleur = "yellow";
		
		int nbPlanche = 0;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		Iterator<Commande> ic = commandes.iterator();
		
		try { // Ecriture des resultats
			fwriter = new FileWriter(filename);
			
			writer = factory.createXMLStreamWriter(fwriter);
		
			writer.writeStartDocument("1.0");
			writer.writeCharacters("\n");
			
			writer.writeStartElement("svg");
			writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			writer.writeAttribute("version", "1.1");
			writer.writeAttribute("width", Integer.toString(xPlanche + nbPlancheMax*(interPlancheX+largeur)));
			writer.writeAttribute("height", Integer.toString(2*yPlanche+longueur));
			writer.writeCharacters("\n\n    ");

			while(ic.hasNext()) {	
				Commande c = ic.next();
				
				if(!c.getRejet()) {
					Iterator<Decoupe> id = c.getDecoupes().iterator();
				 
					while(id.hasNext()) {
						Decoupe d = id.next();
						
						if(d.getIdPlanche() > nbPlanche) {
							// Nouvelle(s) planche(s) du fournisseur
							
							while(nbPlanche < d.getIdPlanche()) {
								// Titre
								writer.writeStartElement("text");
								writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+interPlancheX) + xPlanche));
								writer.writeAttribute("y", Integer.toString(yPlanche - titreMarge));
								writer.writeAttribute("style", "font-size:" + textTaille + ";color:" + textCouleur + ";");
								writer.writeCharacters("Planche " + (nbPlanche+1));
								writer.writeEndElement(); // text
								writer.writeCharacters("\n    ");
								
								// Planche
								writer.writeEmptyElement("rect");
								writer.writeAttribute("width", Integer.toString(largeur));
								writer.writeAttribute("height", Integer.toString(longueur));
								writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+ interPlancheX) + xPlanche));
								writer.writeAttribute("y", Integer.toString(yPlanche));
								writer.writeAttribute("style", "fill:" + plancheCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
								writer.writeCharacters("\n    ");
								
								nbPlanche++;
							}
						}
						
						// Decoupe
						writer.writeEmptyElement("rect");
						writer.writeAttribute("width", Integer.toString(c.getLargeur()));
						writer.writeAttribute("height", Integer.toString(c.getLongueur()));
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX()));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY()));
						writer.writeAttribute("style", "fill:" + decoupeCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
						writer.writeCharacters("\n    ");
						
						// Numero de commande
						writer.writeStartElement("text");
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX() + (c.getLargeur())/2 -5));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY() + (c.getLongueur())/2));
						writer.writeAttribute("style", "font-size:" + textTaille + ";color:" + textCouleur + ";");
						writer.writeCharacters(Integer.toString(c.getId()));
						writer.writeEndElement(); // text
						writer.writeCharacters("\n    ");
					}
				}
			}
			writer.writeCharacters("\n");
			writer.writeEndElement(); // svg
			writer.writeCharacters("\n");
			
			writer.writeEndDocument();

			writer.close();
			fwriter.close();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		} 
	}
}
