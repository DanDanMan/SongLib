package view;

//Daniel Varma dv262	
//Adit Patel aip38
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;



import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SongViewController {
	
	@FXML Button add, edit, delete, done, cancel;
	@FXML Accordion ac;
	@FXML TextField songText, artistText, albumText, yearText;
	@FXML AnchorPane ap;
	
	boolean addBool = false, editBool = false;
	
	public void buttons(ActionEvent e) {
		Button b = (Button)e.getSource();
		
		if (b == add) {
			//Make user able to fill in the text fields
			songText.setDisable(false);
			artistText.setDisable(false);
			albumText.setDisable(false);
			yearText.setDisable(false);
			done.setDisable(false);
			addBool = true;
		}
		
		if(b == done) {
			//When user finished entering in info, create a song and add it to the list
			boolean duplicate = false;
			
			//Get info
			String songName = songText.getText();
			String artistName = artistText.getText();
			String albumName = albumText.getText();
			String year = yearText.getText();
			String fileName = "SongList.txt";
			//Check if song already exists
			
			
			//making song and artist mandatory
			if (songName.equals("") || artistName.equals("")) {
				//popup showing error 
				//System.out.println("TESTING addbool1");
				//Optional <ButtonType> warning = 
				Alert warning = new Alert(AlertType.ERROR);
				warning.setTitle("ERROR");
				warning.setHeaderText("You must enter song name and artist name");
				warning.showAndWait();
				
				b = null;
				addBool = false;
				editBool = false;
			}
			
			//check if name artist exists already
			for(int i = 0; i < ac.getPanes().size(); i++) {
				String temp = ac.getPanes().get(i).getText();
				String temp2 = "Song:"+songName+" Artist:"+artistName;
				System.out.println(temp+" "+temp2);
				if(temp.compareTo(temp2) == 0) {
					Alert warning = new Alert(AlertType.ERROR);
					warning.setTitle("ERROR");
					warning.setHeaderText("Can't process request:Duplicate song");
					warning.showAndWait();
					
					duplicate = true;
					b = null;
					addBool = false;
					editBool = false;
					break;
					
				}
			}
			
			//Add the edited song to the UI if its not a duplicate
			if (editBool == true && !duplicate) {
				
				ObservableList<TitledPane> titledPanes = ac.getPanes();
		        TitledPane selected = new TitledPane();
		        
		        GridPane grid = new GridPane();
				grid.addRow(0, new Label("* Album *"));
				grid.addRow(1, new Label(albumName));
				grid.addRow(2, new Label("* Year *"));
				grid.addRow(3, new Label(year));
				TitledPane tp = new TitledPane("Song:"+songName+" Artist:"+artistName, grid);
				
				//ac.getPanes().add(tp);

				//LEFT OFF HERE
				TitledPane removed = null;
		        for (int i = 0; i < titledPanes.size(); i++) {
		        	if (titledPanes.get(i).isExpanded() == true){
		        		removed = ac.getPanes().get(i);
		        		ac.getPanes().remove(i);
		        		
		        	}
		        }
		        
		        //remove
		        String txt = removed.getText();
		        //System.out.println(txt);
		        //songText.setText(selected.getT);
		        String txtarr[] = txt.split(":");
				String arr2[] = txtarr[1].split(" Artist");
				String songTxt = arr2[0];
				String artistTxt = txtarr[2];
		        
				//System.out.println("songTxt: " + songTxt);
				//System.out.println("artistTxt: " + artistTxt);

		        GridPane gri = (GridPane) removed.getContent(); //getContent
		        
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
		        
				String lineToRemove = songTxt+"/"+artistTxt+"/"+albumTxt+"/"+yearTxt+"\r\n";
				File f = new File("SongList.txt");
				try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				removeLine(br,f,lineToRemove );
				}catch(FileNotFoundException ex){
					System.out.println("No file exists");
				}catch(IOException ex){
					System.out.println("error");
				}
		        
		        if(ac.getPanes().size() == 0) {
					ac.getPanes().add(tp);
				}else {
					insertSong(titledPanes, tp);
				}
		        
		        ac.setExpandedPane(tp);
			}
			
			
			
			//if the edited or added song is not a duplicate then write it to the textfile
			if(!duplicate) {
				if (!songName.equals("") && !artistName.equals("")) {
					//Writing the song to the text file(in alphabetical order?)
					try {
						//FileWriter fw = new FileWriter(fileName,true);
						FileWriter fw = new FileWriter("SongList.txt",true);
						BufferedWriter bw = new BufferedWriter(fw);
						//System.out.println(songName);
						//bw.write("test");
						fw.write(songName+"/"+artistName+"/"+albumName+"/"+year+"\r\n");
						fw.close();
						bw.close();
					}catch(FileNotFoundException ex){
						System.out.println("No file exists");
					}catch(IOException ex){
						System.out.println("error");
					}
				}
			}
			
			
			//Add the song to the UI
			if (addBool == true && !duplicate) {
				
				//Adding the song to the UI
				GridPane grid = new GridPane();
				grid.addRow(0, new Label("* Album *"));
				grid.addRow(1, new Label(albumName));
				grid.addRow(2, new Label("* Year *"));
				grid.addRow(3, new Label(year));
				TitledPane tp = new TitledPane("Song:"+songName+" Artist:"+artistName, grid);
				
			
				if(ac.getPanes().size() == 0) {
					ac.getPanes().add(tp);
				}else {
					ObservableList<TitledPane> titledPanes = ac.getPanes();
					insertSong(titledPanes, tp);
				}
				ac.setExpandedPane(tp);

			}
			
	        //reset fields
	        songText.setDisable(true);
			artistText.setDisable(true);
			albumText.setDisable(true);
			yearText.setDisable(true);
		
			add.setDisable(false);
			delete.setDisable(false);
			edit.setDisable(false);
			done.setDisable(true);
			
			songText.setText("");
			artistText.setText("");
			albumText.setText("");
			yearText.setText("");
			
			addBool = false;
			editBool = false;
			ac.setDisable(false);

		}
		
		
		if (b == cancel) {
			songText.setDisable(true);
			artistText.setDisable(true);
			albumText.setDisable(true);
			yearText.setDisable(true);
			
			ac.setDisable(false);
			add.setDisable(false);
			delete.setDisable(false);
			edit.setDisable(false);
			done.setDisable(true);
			
			songText.setText("");
			artistText.setText("");
			albumText.setText("");
			yearText.setText("");
			
			addBool = false;
			editBool = false;
		}
		
		if (b == edit) {
			editBool = true;
			ac.setDisable(true);
			add.setDisable(true);
			delete.setDisable(true);
			edit.setDisable(true);
			done.setDisable(false);
			songText.setDisable(false);
			artistText.setDisable(false);
			albumText.setDisable(false);
			yearText.setDisable(false);
			
			
			
			
			//need to set all the text boxes with values of the selected titled pane 
	        ObservableList<TitledPane> titledPanes = ac.getPanes(); //was .sorted
	        TitledPane selected = new TitledPane();
	        
	        //get the pane user clicked on
	        for (int i = 0; i < titledPanes.size(); i++) {
	        	if (titledPanes.get(i).isExpanded() == true){
	        		selected = titledPanes.get(i);
	        	}
	        	
	        
	        }
	      //initial check to see if something is selected to edit
	        boolean empty = false;
			if (selected == null || selected.getText().equals("")) {
	        	//System.out.println("ITs null");
	        	Alert warning = new Alert(AlertType.ERROR);
				warning.setTitle("ERROR");
				warning.setHeaderText("You must select a song before you edit");
				warning.showAndWait();
				empty = true;
	        }
			
			
	       if (empty == false) {
		        String txt = selected.getText();
		        System.out.println("Txt: " + txt);
		        String txtarr[] = txt.split(":");
				String arr2[] = txtarr[1].split(" Artist");
				String songTxt = arr2[0];
				String artistTxt = txtarr[2];
	
		        GridPane gri = (GridPane) selected.getContent(); //getContent
		        
		        
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
		        
		  
		        String albumNodeTxt = albumNode.toString();
		        String arr [] = albumNodeTxt.split("]'");
		        String albumTxt = arr[1].substring(0, arr[1].length() - 1);
		        
		        
		        String yearNodeTxt = yearNode.toString();
		        String yearArr[] = yearNodeTxt.split("]'");
		        String yearTxt = yearArr[1].substring(0, yearArr[1].length() - 1);
		       
		        
		        songText.setText(songTxt);
				artistText.setText(artistTxt);
				albumText.setText(albumTxt);
				yearText.setText(yearTxt);
			
	       } else {
		        songText.setDisable(true);
				artistText.setDisable(true);
				albumText.setDisable(true);
				yearText.setDisable(true);
				
				ac.setDisable(false);
				add.setDisable(false);
				delete.setDisable(false);
				edit.setDisable(false);
				done.setDisable(true);
				
				songText.setText("");
				artistText.setText("");
				albumText.setText("");
				yearText.setText("");

	       }
		}
		
		if(b == delete) {
			//delete the song from the UI
			ObservableList<TitledPane> titledPanes = ac.getPanes();
			TitledPane selected = new TitledPane();
			int index = 0;
		    for (int i = 0; i < titledPanes.size(); i++) {
		    	if (titledPanes.get(i).isExpanded() == true){
		    		selected = titledPanes.get(i);
		    		index = i;
		    		break;
		        }
		    }
		    boolean empty = false;
			if (selected == null || selected.getText().equals("")) {
	        	Alert warning = new Alert(AlertType.ERROR);
				warning.setTitle("ERROR");
				warning.setHeaderText("You must select a song before you delete");
				warning.showAndWait();
				empty = true;
	        }
			
			if (empty == false) {
			    String txt = selected.getText();
		        String txtarr[] = txt.split(":");
				String arr2[] = txtarr[1].split(" Artist");
				String songTxt = arr2[0];
				String artistTxt = txtarr[2];    
			   
	
				
				//making next song visible on deletion w/ details displayed. if there is no next song, prev song should be displayed w/ details
				if (titledPanes.size() > 1) {
					if (index == (titledPanes.size() -1)) {
						ac.setExpandedPane(titledPanes.get(index-1));
					} else { //if (index == titledPanes.size()){
						ac.setExpandedPane(titledPanes.get(index+1));
					}
				}
				
				
			    titledPanes.remove(selected);
			    
	
			    
			    GridPane gri = (GridPane) selected.getContent(); //getContent
		        
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
		        
		        String albumNodeTxt = albumNode.toString();
		        String arr [] = albumNodeTxt.split("]'");
		       // System.out.println(arr[1]);
		        String albumTxt = arr[1].substring(0, arr[1].length() - 1);
		        
		        String yearNodeTxt = yearNode.toString();
		        String yearArr[] = yearNodeTxt.split("]'");
		        String yearTxt = yearArr[1].substring(0, yearArr[1].length() - 1);
			    
			    
				//delete the song from the text file, index is the line number in the text file to delete from
			    String fileName = "SongList.txt";
				String lineToRemove = songTxt+"/"+artistTxt+"/"+albumTxt+"/"+yearTxt+"\r\n";
				File f = new File("SongList.txt");
				try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				removeLine(br,f,lineToRemove );
				}catch(FileNotFoundException ex){
					System.out.println("No file exists");
				}catch(IOException ex){
					System.out.println("error");
				}
			
			}
			
		
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
		
		
		insert(tps, toAdd, songName1, songName2, artistName1, artistName2);
		
		}else {
			//need to go through list and find right index to insert
			for(int i = 0; i < size; i++) {
				songArtist1 = tps.get(i).getText();
				String arr5[] = songArtist1.split(":");
				String arr6[] = arr5[1].split(" Artist");
				songName1 = arr6[0];
				artistName1 = arr5[2];
				
				if(songName1.compareToIgnoreCase(songName2) > 0) {
					//songName2 comes first
					tps.add(i,toAdd);
					return;
				}else if(songName1.compareToIgnoreCase(songName2) == 0) {
					//same name so compare artists. loop through songs with the same
					while(i < size && songName1.compareToIgnoreCase(songName2) == 0) {
						
						//getting song and artist
						songArtist1 = tps.get(i).getText();
						String arr7[] = songArtist1.split(":");
						String arr8[] = arr7[1].split(" Artist");
						songName1 = arr8[0];
						artistName1 = arr7[2];
						
						//if they are not the same song name break out, so song belongs at the end of the list with the same name
						if(songName1.compareToIgnoreCase(songName2) != 0) {
							break;
						}
						//System.out.println(songName1+ songName2 + songName1.compareTo(songName2));
						
						//found a spot where new song comes before and old song
						if(artistName1.compareToIgnoreCase(artistName2) > 0) {
							System.out.println("insert3");
							tps.add(i, toAdd);
							return;
						}
						i++;
					}
					
					//belongs at the end of the list
					if(i == size) {
						System.out.println("insert4");
						tps.add(toAdd);
						return;
					}
					
					//case where you break out the loop
					System.out.println("insert5");
					tps.add(i,toAdd);
					return;
				}
				
				
			}
			tps.add(toAdd);
			return;
		}
		
	}
	
	public void insert(ObservableList<TitledPane> tps, TitledPane toAdd, String sn1, String sn2, String an1, String an2) {
		if(sn1.compareToIgnoreCase(sn2) < 0) {
			//songName1 comes first
			tps.add(toAdd);
		}else if(sn1.compareToIgnoreCase(sn2) > 0) {
			//songName 2 comes first
			tps.add(0,toAdd);
		}else {
			//same name so compare artists
			if(an1.compareToIgnoreCase(an2) < 0) {
				//artistName1 comes first
				tps.add(toAdd);
			}else {
				//artistname2 comes first
				tps.add(0,toAdd);
			}
			
		}
	}
	
	public static void removeLine(BufferedReader br,File f, String Line) throws FileNotFoundException, UnsupportedEncodingException {
		try {
		  File tempFile = new File(f.getAbsolutePath() + "2.txt");
			
	      BufferedReader br2 = new BufferedReader(new FileReader(f));
	      PrintWriter pw = new PrintWriter(new FileWriter(tempFile,true));

	      String line = null;
	      
	      while ((line = br.readLine()) != null) {

	          if (!line.trim().equals(Line.trim())) {
	            pw.println(line);
	            pw.flush();
	          }
	        }
	        pw.close();
	        br.close();
	        br2.close();
	        //Delete the original file
	        if (!f.delete()) {
	          System.out.println("Could not delete file");
	          return;
	        }

	        //Rename the new file to the filename the original file had.
	        if (!tempFile.renameTo(f)) {
	          System.out.println("Could not rename file");

	      }

		}catch(FileNotFoundException ex2){
			System.out.println("No file exists");
		}catch(IOException ex){
			System.out.println("error");
		}
		
	
	}
	
	//preloader. Reads data from SongList.txt line by line, and adds it to accordion using insertSong method
		@FXML public void initialize() {
			// TODO Auto-generated method stub
			File file = new File ("SongList.txt");
			
			
			try {
				Scanner scn = new Scanner (file);
				while (scn.hasNextLine()) {
					String line = scn.nextLine();
					
					//delminating it based on /
					
					String [] delminated = line.split("/", 4);
					String song = delminated[0];
					String artist = delminated[1];
					String album = delminated[2];
					String year = delminated[3];
					
					
					
					GridPane grid = new GridPane();
					grid.addRow(0, new Label("* Album *"));
					grid.addRow(1, new Label(album));
					grid.addRow(2, new Label("* Year *"));
					grid.addRow(3, new Label(year));
					TitledPane tp = new TitledPane("Song:"+song+" Artist:"+artist, grid);
					//tp.isExpanded()
				
					if(ac.getPanes().size() == 0) {
						ac.getPanes().add(tp);
					}else {
						ObservableList<TitledPane> titledPanes = ac.getPanes();
						insertSong(titledPanes, tp);
					}
					
					
				}
				//wow
				if(ac.getPanes().size() > 0) {
					ac.setExpandedPane(ac.getPanes().get(0));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("File not found");
			}
			
		}
		
		
}
