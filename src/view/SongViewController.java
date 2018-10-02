package view;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
	
	boolean addBool = false, editBool = false;
	
	public void buttons(ActionEvent e) {
		Button b = (Button)e.getSource();
		
		if (b == add) {
			//Make user able to fill in the text fields
			songText.setDisable(false);
			artistText.setDisable(false);
			albumText.setDisable(false);
			yearText.setDisable(false);
			addBool = true;
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
				//FileWriter fw = new FileWriter(fileName,true);
				FileWriter fw = new FileWriter("SongList.txt",true);
				BufferedWriter bw = new BufferedWriter(fw);
				//System.out.println(songName);
				//bw.write("test");
				fw.write(songName+"/"+artistName+"/"+albumName+"/"+year+"\r\n");
				fw.close();
			}catch(FileNotFoundException ex){
				System.out.println("No file exists");
			}catch(IOException ex){
				System.out.println("error");
			}
			
			
			if (addBool == true) {
				//Adding the song to the UI
				GridPane grid = new GridPane();
				grid.addRow(0, new Label("* Album *"));
				grid.addRow(1, new Label(albumName));
				grid.addRow(2, new Label("* Year *"));
				grid.addRow(3, new Label(year));
				TitledPane tp = new TitledPane("Song: "+songName+" Artist: "+artistName, grid);
				//tp.isExpanded()
			
			
				ac.getPanes().add(tp);
			}
			
			if (editBool == true) {
				ObservableList<TitledPane> titledPanes = ac.getPanes();
		        TitledPane selected = new TitledPane();
		        
		        GridPane grid = new GridPane();
				grid.addRow(0, new Label("* Album *"));
				grid.addRow(1, new Label(albumName));
				grid.addRow(2, new Label("* Year *"));
				grid.addRow(3, new Label(year));
				TitledPane tp = new TitledPane("Song: "+songName+" Artist: "+artistName, grid);
				
				ac.getPanes().add(tp);

				//LEFT OFF HERE
		        for (int i = 0; i < titledPanes.size(); i++) {
		        	if (titledPanes.get(i).isExpanded() == true){
		        		//selected = titledPanes.get(i);
		        		//titledPanes.remove(i);
		        		//titledPanes.re
		        		ac.getPanes().remove(i);
		        	}
		        }
		        
				//tp.isExpanded()
			
			//
		        
			}
	        
	        ObservableList<TitledPane> titledPanes = ac.getPanes().sorted();
			//System.out.println(titledPanes);
			//titledPanes.sort(null);
	        for (int i = 0; i < titledPanes.size(); i++) {
	        	
	        }
	        
	        //reset fields
	        songText.setDisable(true);
			artistText.setDisable(true);
			albumText.setDisable(true);
			yearText.setDisable(true);
			
			songText.setText("");
			artistText.setText("");
			albumText.setText("");
			yearText.setText("");
			
			addBool = false;
			editBool = false;
		}
		
		
		if (b == cancel) {
			songText.setDisable(true);
			artistText.setDisable(true);
			albumText.setDisable(true);
			yearText.setDisable(true);
			
			songText.setText("");
			artistText.setText("");
			albumText.setText("");
			yearText.setText("");
		}
		
		if (b == edit) {
			editBool = true;
			songText.setDisable(false);
			artistText.setDisable(false);
			albumText.setDisable(false);
			yearText.setDisable(false);
			
			
			//need to set all the text boxes with values of the selected titled pane 
	        ObservableList<TitledPane> titledPanes = ac.getPanes(); //was .sorted
	        TitledPane selected = new TitledPane();
	        for (int i = 0; i < titledPanes.size(); i++) {
	        	if (titledPanes.get(i).isExpanded() == true){
	        		selected = titledPanes.get(i);
	        	}
	        	
	        	//set it if rest of titledpanes isn't expanded, they cant be expanded 
	        	if (titledPanes.get(i).isExpanded() == false) {
	        		//titledPanes.get(i).setCollapsible(false);
	        	}
	        }
	        
	        
	        /*System.out.println("Selected:");
	        System.out.println(selected);
	        */
	       
	        String txt = selected.getText();
	        //System.out.println(txt);
	        //songText.setText(selected.getT);
	        String txtarr[] = txt.split(":");
			String arr2[] = txtarr[1].split(" Artist");
			String songTxt = arr2[0];
			String artistTxt = txtarr[2];
	        
			//System.out.println("songTxt: " + songTxt);
			//System.out.println("artistTxt: " + artistTxt);

	        GridPane gri = (GridPane) selected.getContent(); //getContent
	        
	        /*System.out.println("gri \n" + gri);
	        System.out.println(gri.getChildren());
	        */
	        Node albumNode = null, yearNode = null;
	        ObservableList<Node> children = gri.getChildren();
	        for (Node node : children) {
	        	if (GridPane.getRowIndex(node) == 1) {
	        		albumNode = node;
	        	}
	        	
	        	if (GridPane.getRowIndex(node) == 3) {
	        		yearNode = node;
	        	}
	        }
	        
	        /*System.out.println("albumNode: " + albumNode);
	        System.out.println("yearNode: " + yearNode);
	        //System.out.println(gri.getRowI);
	        */
	        String albumNodeTxt = albumNode.toString();
	        String arr [] = albumNodeTxt.split("]'");
	       // System.out.println(arr[1]);
	        String albumTxt = arr[1].substring(0, arr[1].length() - 1);
	        
	        
	        String yearNodeTxt = yearNode.toString();
	        String yearArr[] = yearNodeTxt.split("]'");
	        String yearTxt = yearArr[1].substring(0, yearArr[1].length() - 1);
	        //System.out.println("album txt: " + albumTxt);
	        //System.out.println("year txt: " + yearTxt);
	        
	        songText.setText(songTxt);
			artistText.setText(artistTxt);
			albumText.setText(albumTxt);
			yearText.setText(yearTxt);

		}
		
		
	}
	
	

}
