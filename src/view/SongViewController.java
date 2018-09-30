package view;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

public class SongViewController {
	
	@FXML Button add, edit, delete, done, cancel;
	@FXML Accordion ac;
	@FXML TextField songText, artistText, albumText, yearText;
	
	
	public void buttons(ActionEvent e) {
		Button b = (Button)e.getSource();
		
		if (b == add) {
			//Make user able to fill in the text fields
			songText.setDisable(false);
			artistText.setDisable(false);
			albumText.setDisable(false);
			yearText.setDisable(false);
		}
		
		
		//Need to increase the max songs we can add
		//Do we want to store the songs in the text file in alphabetical order
		//How do we keep the session of the songs already added(user turns off then turns back on the program)
		//What data structure should we use to store Songs
		if(b == done) {
			//When user finished entering in info, create a song and add it to the list
			
			//Get info
			String songName = songText.getText();
			String artistName = artistText.getText();
			String albumName = albumText.getText();
			String year = yearText.getText();
			String fileName = "songs.txt";
			//Check if song already exists
			
			try {
				FileReader fr = new FileReader(fileName);
			}catch(FileNotFoundException ex){
				System.out.println("No file exists");
			}catch(IOException ex){
				System.out.println("error");
			}
			
			
			//Writing the song to the text file(in alphabetical order?)
			try {
				FileWriter fw = new FileWriter(fileName,true);
				fw.write(songName+" "+artistName+" "+albumName+" "+year+"\r\n");
				fw.close();
			}catch(FileNotFoundException ex){
				System.out.println("No file exists");
			}catch(IOException ex){
				System.out.println("error");
			}
			
			
			//Adding the song to the UI
			GridPane grid = new GridPane();
			grid.addRow(0, new Label("Album"));
			grid.addRow(1, new Label(albumName));
			grid.addRow(2, new Label("Year"));
			grid.addRow(3, new Label(year));
			TitledPane tp = new TitledPane("Song: "+songName+" Album:"+albumName, grid);
	        ac.getPanes().add(tp);
	        
	        
	        //reset fields
	        songText.setDisable(true);
			artistText.setDisable(true);
			albumText.setDisable(true);
			yearText.setDisable(true);
			
			songText.setText("");
			artistText.setText("");
			albumText.setText("");
			yearText.setText("");
		}
		
		
	}
	
	

}
