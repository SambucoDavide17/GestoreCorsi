/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo;
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch (NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un periodo valido (1 o 2)");
    		return;
    	}catch (NullPointerException npe) {
    		txtRisultato.setText("Devi inserire un periodo valido (1 o 2)");
    		return;
    	}
    	
    	if(periodo < 1 || periodo > 2) {
    		txtRisultato.setText("Devi inserire un periodo valido (1 o 2)");
    		return;
    	}
    	
    	List<Corso> corsi = model.getCorsiByPeriodo(periodo); 
    	/*for(Corso c : corsi) {
    		txtRisultato.appendText(c.toString() + "\n");
    	}*/
    	
    	
    	StringBuilder sb = new StringBuilder();
    	for(Corso c: corsi) {
    		sb.append(String.format("%-8s ", c.getCodins()));
    		sb.append(String.format("%-4d ", c.getCrediti()));
    		sb.append(String.format("%-50s ", c.getNome()));
    		sb.append(String.format("%-4d\n", c.getPd()));
    	}
    	txtRisultato.appendText(sb.toString());
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo;
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch (NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un periodo valido (1 o 2)");
    		return;
    	}catch (NullPointerException npe) {
    		txtRisultato.setText("Devi inserire un periodo valido (1 o 2)");
    		return;
    	}
    	
    	if(periodo < 1 || periodo > 2) {
    		txtRisultato.setText("Devi inserire un periodo valido (1 o 2)");
    		return;
    	}
    	
    	Map<Corso, Integer> corsiIscrizioni = model.getIscrittiByPeriodo(periodo);
    	for(Corso c : corsiIscrizioni.keySet()) {
    		txtRisultato.appendText(c.toString());
    		Integer n = corsiIscrizioni.get(c);
    		txtRisultato.appendText(" " + n + "\n");
    	}
    }
    
    @FXML
    void stampaStudenti(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String codice = txtCorso.getText();
    	if(model.esisteCorso(codice) == false) {
    		txtRisultato.setText("Il corso non esiste");
    		return;
    	}
    	List<Studente> studenti = model.getStudenteByCorso(codice);
    	if(studenti.size() == 0) {
    		txtRisultato.setText("Il corso non ha iscritti");
    		return;
    	}
    	
    	for(Studente s: studenti) {
    		txtRisultato.appendText(s + "\n");
    	}
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	
    	txtRisultato.clear();
    	String codice = txtCorso.getText();
    	if(model.esisteCorso(codice) == false) {
    		txtRisultato.setText("Il corso non esiste");
    		return;
    	}
    	Map<String, Integer> divisione = model.getDivisioneCDS(codice);
    	for(String cds: divisione.keySet()) {
    		txtRisultato.appendText(cds + " " + divisione.get(cds) + "\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	txtRisultato.setStyle("-fx-font-family: monospace");
    }
    
    
}