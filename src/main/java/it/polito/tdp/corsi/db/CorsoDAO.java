package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	public List<Corso> getCorsoByPeriodo(Integer periodo){
		
		String sql = "select * "
				+ "	from corso "
				+ "	where pd = ?";
		List<Corso> risultato = new ArrayList<Corso>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				risultato.add(c);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		return risultato;
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo){
		
		String sql = "select c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot "
				+ "from corso c , iscrizione i "
				+ "where c.codins = i.codins and c.pd = ? "
				+ "group by c.codins, c.nome, c.crediti, c.pd";
		Map<Corso, Integer> risultato = new HashMap<Corso, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				risultato.put(c, n);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		return risultato;
	}
	
	public List<Studente> getStudenteByCorso (Corso corso){
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> risultato = new LinkedList<Studente>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS"));
				risultato.add(s);
			}
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return risultato;
	}
	
	public Map<String, Integer> getDivisioneStudenti(Corso corso){
		String sql = "SELECT s.CDS, COUNT(*) AS tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? AND s.cds <> '' "
				+ "GROUP BY s.CDS";
		Map<String, Integer> risultato = new HashMap<String, Integer>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				risultato.put(rs.getString("CDS"), rs.getInt("tot"));
			}
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return risultato;
	}

	public boolean esisteCorso(Corso corso) {
		
		String sql = "SELECT * FROM corso WHERE codins = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				conn.close();
				return true;
			}
			else {
				conn.close();
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
