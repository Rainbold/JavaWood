package gestion.algorithme;

import gestion.bois.Commande;
import gestion.bois.Decoupe;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public abstract class Serialisation {

	public static void svg(String filename, int longeur, int largeur, List<Commande> commandes) {
		
		FileWriter fwriter = null;
		try{
		     fwriter = new FileWriter(filename);
		}catch(IOException ex){
		    ex.printStackTrace();
		}
		
		int xPlanche = 30; // Distance horizontale des planches 
		int yPlanche = 30; // Distance verticale de la planche courante
		
		String contourTaille = "2px";
		String contourCouleur = "black";
		
		int interPlancheX = 50; // Distance entre 2 planches sur X
		int interPlancheY = 50; // Distance entre 2 planches sur Y
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
			writer = factory.createXMLStreamWriter(fwriter);
		
			writer.writeStartDocument("1.0");
			
			writer.writeStartElement("svg");
			writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			writer.writeAttribute("version", "1.1");
			
			//TODO title, desc

			while(ic.hasNext()) {	
				Commande c = ic.next();
				
				if(!c.getRejet()) {
					Iterator<Decoupe> id = c.getDecoupes().iterator();
				 
					while(id.hasNext()) {
						Decoupe d = id.next();
						
						if(d.getIdPlanche() > nbPlanche) {
							// Nouvelle planche du fournisseur
							
							// Titre
							writer.writeStartElement("text");
							writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+interPlancheX) + xPlanche));
							writer.writeAttribute("y", Integer.toString(yPlanche - titreMarge));
							writer.writeAttribute("style", "font-size:" + textTaille + ";");
							writer.writeCharacters("Planche " + nbPlanche);
							writer.writeEndElement(); // text
							
							// Planche
							writer.writeStartElement("rect");
							writer.writeAttribute("width", Integer.toString(largeur));
							writer.writeAttribute("height", Integer.toString(longeur));
							writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+ interPlancheX) + xPlanche));
							writer.writeAttribute("y", Integer.toString(yPlanche));
							writer.writeAttribute("style", "fill:" + plancheCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
							writer.writeEndElement(); // rect
							
							nbPlanche = d.getIdPlanche();
						}
						
						// Decoupe
						writer.writeStartElement("rect");
						writer.writeAttribute("width", Integer.toString(c.getLargeur()));
						writer.writeAttribute("height", Integer.toString(c.getLongueur()));
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX()));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY()));
						writer.writeAttribute("style", "fill:" + decoupeCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
						writer.writeEndElement(); // rect
						
						// Numero de commande
						writer.writeStartElement("text");
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX() + (c.getLargeur())/2 -5));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY() + (c.getLongueur())/2));
						writer.writeAttribute("style", "font-size:" + textTaille + ";");
						writer.writeCharacters(Integer.toString(c.getId()));
						writer.writeEndElement(); // text
					}
				}
			}
			writer.writeEndElement(); // svg

			writer.writeEndDocument(); 

			//writer.flush();
			writer.close();
	    
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
