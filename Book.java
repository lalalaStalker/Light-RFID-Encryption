
public class Book {
	
	private int id;	
	private String title;
	private long isbn;
	private String author;
	private int libraryId;
	private int libraryMedia;
	private String publishDate;
	
	public Book() {
		
	}
	
	public Book(int id, String title, long isbn, String author, int libId, int libMed, String pubDate) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.libraryId = libId;
		this.libraryMedia = libMed;
		this.publishDate = pubDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getIsbn() {
		return isbn;
	}
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(int libraryId) {
		this.libraryId = libraryId;
	}
	public int getLibraryMedia() {
		return libraryMedia;
	}
	public void setLibraryMedia(int libraryMedia) {
		this.libraryMedia = libraryMedia;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
	public String toString() {
		String ret = "";
		ret += id + "\n";
		ret += title + "\n";
		ret += isbn  + "\n";
		ret += author + "\n";
		ret += publishDate + "\n";
		ret += libraryId + "\n";
		ret += libraryMedia;
		return ret;
	}

}
