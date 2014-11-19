package gestion.algorithme;

import gestion.bois.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

public abstract class Algorithme {
	
	public void methode1(HashSet<Commande> commandes, HashSet<Planche> planches) {
		//TODO Généré le fichier xml avec la méthode 1
		// La sérialisation se fera plus tard
	}
	
	public static void serialisation(String filename, int longeur, int largeur, HashSet<Commande> commandes) {
		
		int marge_x = 20;
		int marge_y = 20;
		
		int bande = 5;
		String couleur_bande = "black";
		
		int marge_planche = 20;
		int marge_titre = 10;
		
		String couleur_planche = "";
		
		String couleur_decoupe = "";
		
		int nb_planche = 0;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		Iterator<Commande> ic = commandes.iterator();
		
		try {
			writer = factory.createXMLStreamWriter(System.out, "UTF-8");
		
			writer.writeStartDocument("1.0");
			
			writer.writeStartElement("svg");
			writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			writer.writeAttribute("version", "1.1");
			//writer.writeAttribute("width", "300");
			
			//writer.writeAttribute("height", "200");
			
			//TODO title, desc
/*
			while(ic.hasNext()) {	
				Commande c = ic.next();
				commandes.
				if(c.getRejet()) {
					Iterator<Decoupe> id = c.getDecoupes().iterator();
				 
					while(id.hasNext()) {
						Decoupe d = id.next();
						
						if(d.getIdPlanche() > nb_planche) {
							nb_planche = d.getIdPlanche();
							
							writer.writeStartElement("rect");
							writer.writeAttribute("width", Integer.toString(largeur));
							writer.writeAttribute("height", Integer.toString(longeur));
							writer.writeAttribute("x", "300");
							writer.writeAttribute("y", "300");
							writer.writeAttribute("fill", "black");
							
							writer.writeEndElement(); // rect
						}
					 
					}
				}
			}
			writer.writeEndElement(); // svg

			writer.writeEndDocument(); 
	
	    
	    writer.writeStartElement("catalog");

	    writer.writeStartElement("book");

	    writer.writeAttribute("id", "1");

	    writer.writeStartElement("code");
	    writer.writeCharacters("I01");
	    writer.writeEndElement();

	    writer.writeStartElement("title");
	    writer.writeCharacters("This is the title");
	    writer.writeEndElement();

	    writer.writeStartElement("price");
	    writer.writeCharacters("$2.95");
	    writer.writeEndElement();

	    writer.writeEndDocument();
	    

	    writer.flush();
	    writer.close();
	    */
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}

// It has to be without public or private
