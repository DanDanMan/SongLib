package application;
	
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.SplitPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException{
		//FXMLLoader loader = new FXMLLoader();
		//loader.setLocation(getClass().getResource("/f2c/view/F2C.fxml"));
		
		//PrintWriter writer = new PrintWriter("songs.txt", "UTF-8");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SongView.fxml"));
		
		//AnchorPane root = (AnchorPane)loader.load();
		SplitPane root = (SplitPane)loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SongView");
		primaryStage.setResizable(true);  
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
