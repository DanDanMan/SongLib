package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class SongViewController {
	
	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;

	@FXML Accordion ac;
	int num = 1;
	public void buttons(ActionEvent e) {
		System.out.println("h");
		Button b = (Button)e.getSource();
		
		if (b == add) {
			System.out.println("1");
			//Song s = new Song
			TitledPane tp = new TitledPane("Song ", new TextField());
	        ac.getPanes().add(tp);
	        num++;
	        System.out.println("HI");
		}
		
	}
	

}
