package gestion.algorithme;

import  gestion.bois.*;
import javax.xml.stream.*;
import java.io.*;
import java.util.HashSet;
import javax.xml.stream.events.XMLEvent;

public abstract class Algorithme {
	

	public String Methode1() {
		return "";
	}

	public String Methode2() {
		return "";
	}

	public static String XMLParseFournisseur(String file, HashSet<Planche> planches) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory f = XMLInputFactory.newInstance();
		FileInputStream fis = new FileInputStream(file);
	    XMLStreamReader r = f.createXMLStreamReader(fis);
	   
	    int i = 0;
	    
		while(r.hasNext()) {
			int eventType = r.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                	if(i == 0 && r.getLocalName() != "fournisseurs")
            		{
                		// Todo throw an exception
            			System.err.println("Fichier " + file + " invalide");
            			System.exit(1);
            		}
                	
                	switch(r.getLocalName())
                	{
                		case "fournisseur":
                			int attributes = r.getAttributeCount();
                			for(int index=0; index<attributes; index++) {
                    			System.out.println(r.getAttributeLocalName(index) + " : " + r.getAttributeValue(index));
                			}
                			break;
                		
//                case XMLEvent.START_ELEMENT:
//                    int attributes = r.getAttributeCount();
//                    for(int i=0;i<attributes;++i) {
//                    }
//                break;
                	
                	}
                	break;
            }
            
            i++;
		}
		
		return "";
	}

	public void Writer() {
	}
}
