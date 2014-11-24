package gestion.algorithme;

import gestion.bois.Commande;
import gestion.bois.Decoupe;
import gestion.bois.Planche;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public abstract class Resultat {
	
	public static void xml(String filename, int nbPlanche, Planche planche, int methode, List<Commande> commandes) {
		
		int nbRejet = 0;
		FileWriter fwriter = null;
		
		@SuppressWarnings("unchecked")
		List<Decoupe>[] planches = new List[nbPlanche];
		
		List<Commande> crejet = new ArrayList<Commande>();
		
		Iterator<Commande> ic;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		try {
			fwriter = new FileWriter(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < nbPlanche; i++) {
			planches[i] = new Vector<Decoupe>();
		}
		
		ic = commandes.iterator();
		
		while(ic.hasNext()) {
			Commande c = ic.next();
			
			if(!c.getRejet()) {
				Iterator<Decoupe> id = c.getDecoupes().iterator();
			 
				while(id.hasNext()) {
					Decoupe d = id.next();
					planches[d.getIdPlanche()-1].add(d);
				}
			}
			else {
				nbRejet++;
				crejet.add(c);
			}
		}
		
		try {
			writer = factory.createXMLStreamWriter(fwriter);
			
			writer.writeStartDocument("1.0");
			fwriter.write("\n");
			
			writer.writeStartElement("simulation");
			writer.writeAttribute("fournisseur", Integer.toString(planche.getId()));
			writer.writeAttribute("prix", Float.toString(planche.getPrix() * nbPlanche));
			writer.writeAttribute("rejets", Integer.toString(nbRejet));
			writer.writeAttribute("algorithme", Integer.toString(methode));
			fwriter.write("\n    ");
		
			// Rejets
			writer.writeStartElement("rejets");
			fwriter.write("\n    ");
			
			Iterator<Commande> icrejet = crejet.iterator();
			
			while(icrejet.hasNext()) {	
				Commande c = ic.next();
				fwriter.write("    ");
				writer.writeStartElement("rejet");	
				writer.writeAttribute("commande", Integer.toString(c.getId()));
				writer.writeEndElement(); // rejet
				fwriter.write("\n     ");
			}
			
			writer.writeEndElement(); // rejets
			fwriter.write("\n     ");
			
			// Planches
			writer.writeStartElement("planches");
			fwriter.write("\n    ");
			
			for(int i = 0; i < nbPlanche; i++) {
				Iterator<Decoupe> id = planches[i].iterator();
				
				// Planche
				fwriter.write("    ");
				writer.writeStartElement("planche");	
				writer.writeAttribute("id", Integer.toString(i+1));
				fwriter.write("\n        ");
				
				while(id.hasNext()) {
					Decoupe d = id.next();
					// Decoupe
					fwriter.write("    ");
					writer.writeStartElement("decoupe");	
					writer.writeAttribute("commande", Integer.toString(d.getIdCommande()));
					writer.writeAttribute("x", Integer.toString(d.getX()));
					writer.writeAttribute("y", Integer.toString(d.getY()));
					writer.writeEndElement(); // decoupe
					fwriter.write("\n        ");
				}
				writer.writeEndElement(); // planche 
				fwriter.write("\n    ");
			}
			
			writer.writeEndElement(); // planches
			fwriter.write("\n");
			
			writer.writeEndElement(); // simulation
			fwriter.write("\n");
			
			writer.writeEndDocument();
			
			writer.close();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void svg(String filename, Planche planche, List<Commande> commandes) {
			
		FileWriter fwriter = null;
		
		int longueur = planche.getLongueur();
		int largeur = planche.getLargeur();
		
		int xPlanche = 30; // Distance horizontale des planches 
		int yPlanche = 30; // Distance verticale de la planche courante
		
		String contourTaille = "2px";
		String contourCouleur = "black";
		
		int interPlancheX = 50; // Distance entre 2 planches sur X
		//int interPlancheY = 50; // Distance entre 2 planches sur Y - not used for the moment
		String plancheCouleur = "lightblue";
		
		int titreMarge = 10; // Nombre de pixel au dessus de la planche
		String textTaille = "14px";
		String textCouleur = "black";
		
		String decoupeCouleur = "yellow";
		
		int nbPlanche = 0;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		Iterator<Commande> ic = commandes.iterator();
		
		try {
			fwriter = new FileWriter(filename);
			
			writer = factory.createXMLStreamWriter(fwriter);
		
			writer.writeStartDocument("1.0");
			fwriter.write("\n");
			
			writer.writeStartElement("svg");
			writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			writer.writeAttribute("version", "1.1");
			fwriter.write("\n");
			
			//TODO title, desc

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
								fwriter.write("\n");
								
								// Planche
								writer.writeStartElement("rect");
								writer.writeAttribute("width", Integer.toString(largeur));
								writer.writeAttribute("height", Integer.toString(longueur));
								writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+ interPlancheX) + xPlanche));
								writer.writeAttribute("y", Integer.toString(yPlanche));
								writer.writeAttribute("style", "fill:" + plancheCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
								writer.writeEndElement(); // rect
								fwriter.write("\n");
								
								nbPlanche++;
							}
						}
						
						// Decoupe
						writer.writeStartElement("rect");
						writer.writeAttribute("width", Integer.toString(c.getLargeur()));
						writer.writeAttribute("height", Integer.toString(c.getLongueur()));
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX()));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY()));
						writer.writeAttribute("style", "fill:" + decoupeCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
						writer.writeEndElement(); // rect
						fwriter.write("\n");
						
						// Numero de commande
						writer.writeStartElement("text");
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX() + (c.getLargeur())/2 -5));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY() + (c.getLongueur())/2));
						writer.writeAttribute("style", "font-size:" + textTaille + ";color:" + textCouleur + ";");
						writer.writeCharacters(Integer.toString(c.getId()));
						writer.writeEndElement(); // text
						fwriter.write("\n");
					}
				}
			}
			writer.writeEndElement(); // svg
			fwriter.write("\n");
			
			writer.writeEndDocument();

			writer.close();
	    
		} catch (XMLStreamException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
