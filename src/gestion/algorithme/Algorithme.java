package gestion.algorithme;

import gestion.bois.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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
		int bande_noir = 5;
		int marge_planche = 20;
		int marge_titre = 10;
		
		int nb_planche = 0;
		
		Iterator<Commande> i = commandes.iterator();
		
		while(i.hasNext()) {
			 Commande e = i.next();
			 
			 //if(e.)
			 
			 
		}
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
	    XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(System.out);
		

	    writer.writeStartDocument("1.0");

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
	    
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
