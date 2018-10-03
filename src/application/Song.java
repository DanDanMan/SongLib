package application;

//Daniel Varma dv262
//Adit Patel aip38


public class Song {
	
	private String name, artist, album, year;
	
	public Song (String n, String art, String al, String y) { //name and artist mandatory 
		this.name = n;
		this.artist = art;
		
		if (al != null) {
			this.album = al;
		}
		
		if (y!= null) {
			this.year = y;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		if (album != null) {
			return album;
		}
		return null;
	}
	
	public String getYear() {
		if (year != null) {
			return year;
		}
		return null;
	}

	
	public void edit(String n, String art, String al, String y) { //if name artist combo confilcts then do not allow 
		this.name = n;
		this.artist = art;
		
		if (al != null) {
			this.album = al;
		}
		
		if (y!= null) {
			this.year = y;
		}
		
	}
}
