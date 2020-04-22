package it.polito.tdp.nobel.model;

import java.util.*;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	private List<Esame> esami;
	
	private double bestMedia = 0.0;
	private Set<Esame> bestSoluzione = null;
	
	public Model () {
		EsameDAO dao = new EsameDAO();
		this.esami= dao.getTuttiEsami();
	}

	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		int livello=0;
		Set<Esame>parziale= new HashSet<>();
		cerca(parziale,livello,numeroCrediti);
		
		
		return bestSoluzione;
	}
	
	private void cerca(Set<Esame> parziale,int livello,int m) {
	
		//casi terminazione
		int crediti= sommaCrediti(parziale);
		
		if(crediti>m) 
			return;
		if(crediti==m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				bestSoluzione= new HashSet<>(parziale);
				bestMedia= media;
			}
		}
		
		//sicuramnte crediti < m
		if (livello == esami.size()) {
			return;
		}
		
	
		//generare sotto problemi
		//esame di l è da aggiungere o no? provo entrambe le soluzioni
		
		//provo ad agg
		parziale.add(esami.get(livello));
		cerca(parziale,livello+1,m);
		parziale.remove(esami.get(livello));
		
		//prova non add
		cerca(parziale,livello+1,m);
		
		
		
	}


	public double calcolaMedia(Set<Esame> parziale) {
		
		int crediti=0;
		int somma=0;
		
		for(Esame e :parziale) {
			crediti+=e.getCrediti();
			somma += (e.getVoto()*e.getCrediti());
		}
		
		return (somma/crediti);
	}


	private int sommaCrediti(Set<Esame> parziale) {
		int somma=0;
		for(Esame e: parziale ) {
			somma+=e.getCrediti();
		}
		return somma;
	}
	
	

}
