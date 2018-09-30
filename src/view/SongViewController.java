package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

public class SongViewController {
	
	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	@FXML Button done;
	@FXML Button cancel;
	

	@FXML Accordion ac;
	int num = 1;
	public void buttons(ActionEvent e) {
		Button b = (Button)e.getSource();
		
		if (b == add) {
			//Song s = new Song
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Album"));
			grid.addRow(1, new Label(""));
			grid.addRow(2, new Label("Year"));
			grid.addRow(3, new Label(""));
			//TitledPane tp = new TitledPane("Song ", new TextField("Name:"));
			TitledPane tp = new TitledPane("Song ", grid);

	        ac.getPanes().add(tp);
	        num++;
		}
		
		
	}
	

}
