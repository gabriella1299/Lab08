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
	
	ExtFlightDelaysDAO dao=new ExtFlightDelaysDAO();
	List<Airport> airport;

	public void creaGrafo(int x) {
		
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		airport= dao.loadAllAirports();
		Graphs.addAllVertices(this.grafo, airport);
		
		for(Distance d: dao.getAvgDistance(x)) {
			Airport a1=getAirport(d.getA1_id());
			Airport a2=getAirport(d.getA2_id());
			if(!this.grafo.containsEdge(a1, a2))
				Graphs.addEdge(this.grafo, a1, a2, d.getMedia());
			else {
				DefaultWeightedEdge e=this.grafo.getEdge(a1, a2);
				double peso=this.grafo.getEdgeWeight(e);
				peso=(peso+d.getMedia())/2;
				this.grafo.setEdgeWeight(a1, a2, peso);
			}
		}
		
		
	}
	
	public String stampaGrafo() {
		return "Grafo creato con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi "+"\n";
	}
	
	public List<Distance> getAvgDistance(int x) {
		return dao.getAvgDistance(x);
	}
	
	public Airport getAirport(int id) {
		
		for(Airport a: dao.loadAllAirports()) {
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
	
}


