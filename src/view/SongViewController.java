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
			
				if(ac.getPanes().size() == 0) {
					ac.getPanes().add(tp);
				}else {
					ObservableList<TitledPane> titledPanes = ac.getPanes();
					insertSong(titledPanes, tp);
				}
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
				
				//ac.getPanes().add(tp);

				//LEFT OFF HERE
		        for (int i = 0; i < titledPanes.size(); i++) {
		        	if (titledPanes.get(i).isExpanded() == true){
		        		ac.getPanes().remove(i);
		        	}
		        }
		        
		        if(ac.getPanes().size() == 0) {
					ac.getPanes().add(tp);
				}else {
					insertSong(titledPanes, tp);
				}
		        
				//tp.isExpanded()
			
			//
		        
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
			
			addBool = false;
			editBool = false;
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
	
	public void insertSong(ObservableList<TitledPane> tps, TitledPane toAdd) {
		//sort by song name, then by artist
		int size = tps.size();
		String songName1 = "";
		String artistName1 = "";
		String songArtist1 = "";
		
		//get the song and artist name of song we are adding
		String songArtist2 = toAdd.getText();
		String arr3[] = songArtist2.split(":");
		String arr4[] = arr3[1].split(" Artist");
		String songName2 = arr4[0];
		String artistName2 = arr3[2];
		
		
		
		
		if(size == 1) {
		//the case where we add the second song
		songArtist1 = tps.get(0).getText();
		String arr[] = songArtist1.split(":");
		String arr2[] = arr[1].split(" Artist");
		songName1 = arr2[0];
		artistName1 = arr[2];
		
		System.out.println("insert1");
		insert(tps, toAdd, songName1, songName2, artistName1, artistName2);
		
		}else {
			//need to go through list and find right index to insert
			for(int i = 0; i < size; i++) {
				songArtist1 = tps.get(i).getText();
				String arr5[] = songArtist1.split(":");
				String arr6[] = arr5[1].split(" Artist");
				songName1 = arr6[0];
				artistName1 = arr5[2];
				
				if(songName1.compareTo(songName2) > 0) {
					//songName2 comes first
					System.out.println("insert2");
					tps.add(i,toAdd);
					return;
				}else if(songName1.compareTo(songName2) == 0) {
					//same name so compare artists. loop through songs with the same
					while(i < size && songName1.compareTo(songName2) == 0) {
						
						//getting song and artist
						songArtist1 = tps.get(i).getText();
						String arr7[] = songArtist1.split(":");
						String arr8[] = arr7[1].split(" Artist");
						songName1 = arr8[0];
						artistName1 = arr7[2];
						
						//if they are not the same song name break out, so song belongs at the end of the list with the same name
						if(songName1.compareTo(songName2) != 0) {
							break;
						}
						//System.out.println(songName1+ songName2 + songName1.compareTo(songName2));
						
						//found a spot where new song comes before and old song
						if(artistName1.compareTo(artistName2) > 0) {
							System.out.println("insert3");
							tps.add(i, toAdd);
							return;
						}
						i++;
					}
					
					//belongs at the end of the list
					if(i == size) {
						tps.add(toAdd);
						return;
					}
					
					//case where you break out the loop 
					tps.add(i,toAdd);
					return;
				}
				
				//if you get to this point then the song has not been added
				
			}
			tps.add(toAdd);
			return;
		}
		
	}
	
	public void insert(ObservableList<TitledPane> tps, TitledPane toAdd, String sn1, String sn2, String an1, String an2) {
		if(sn1.compareTo(sn2) < 0) {
			//songName1 comes first
			tps.add(toAdd);
		}else if(sn1.compareTo(sn2) > 0) {
			//songName 2 comes first
			tps.add(0,toAdd);
		}else {
			//same name so compare artists
			if(an1.compareTo(an2) < 0) {
				//artistName1 comes first
				tps.add(toAdd);
			}else {
				//artistname2 comes first
				tps.add(0,toAdd);
			}
			
		}
	}
	
	

}
