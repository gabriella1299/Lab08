package it.polito.tdp.extflightdelays.model;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	Graph<Airport,DefaultWeightedEdge> grafo;
	
	ExtFlightDelaysDAO dao;
	List<Airport> airport; //potevo anche fare un'idMap
						   //cambio metodo loadAllAirports()

	public Model() {
		dao=new ExtFlightDelaysDAO();
		airport= dao.loadAllAirports();
	}
	public void creaGrafo(int x) {
		
		//grafo deve essere semplice, non orientato e pesato
		//i vertici devono rappresentare gli aeroporti
		//mentre gli archi devono indicare le rotte tra gli aeroporti collegati tra di loro da almeno un volo
		//peso dell’arco rappresenta la “distanza media percorsa” tra i due aeroporti
		
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiunta vertici
		Graphs.addAllVertices(this.grafo, airport);
		
		//Aggiunta archi
		//L’arco tra due aeroporti deve essere aggiunto solo se 
		//la distanza media percorsa è superiore a x
		for(Distance d: dao.getAvgDistance(x)) {
			
			Airport a1=getAirport(d.getA1_id());
			Airport a2=getAirport(d.getA2_id());
			
			if(!this.grafo.containsEdge(a1, a2))
				Graphs.addEdge(this.grafo, a1, a2, d.getMedia());
			else { //l'arco esiste gia' quinid aggiorno solo il peso
				DefaultWeightedEdge e=this.grafo.getEdge(a1, a2);
				double pesoVecchio=this.grafo.getEdgeWeight(e);
				double pesoNuovo=(pesoVecchio+d.getMedia())/2;
				this.grafo.setEdgeWeight(a1, a2, pesoNuovo);
			}
		}
		
		
	}
	
	public String stampaGrafo() {
		return "Grafo creato con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi "+"\n\nELENCO ROTTE:\n";
	}
		
	public Airport getAirport(int id) {
		
		for(Airport a: airport) {
			if(a.getId()==id)
				return a;
		}
		
		return null;
	}
	
	public String stampaArchi() {
		String stampa="";
		Set<DefaultWeightedEdge> archi=this.grafo.edgeSet();
		for(DefaultWeightedEdge d: archi) {
			stampa=stampa+d.toString().replaceAll("[()]", "").replaceAll("[:]", "-")+": "+this.grafo.getEdgeWeight(d)+"\n";
		}
		return stampa;
	}
	
	public int getArchi(){
		return this.grafo.edgeSet().size();
	}
}


