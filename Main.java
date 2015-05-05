
public class Main {
	
	public static void main(String[] args) {
		ReaderGUI gui = new ReaderGUI();
		Tag[] tags = new Tag[] {
				new Tag(1, "Cryptography and Network Security: Principles and Practice (6th Edition)",
						9780133354690L, "William Stallings", 1, 2,"March 16, 2013"),
				new Tag(2, "Cryptography: Theory and Practice, Third Edition (Discrete Mathematics and Its Applications)",
						9781584885085L, "Douglas R. Stinson", 2, 2, "November 1, 2005"),
				new Tag(3, "Introduction to Cryptography with Coding Theory (2nd Edition)",
						9780131862395L, "Wade Trappe; Lawrence C. Washington", 3, 2, "July 25, 2005"),
				new Tag(4, "Introduction to Algorithms, 3rd Edition", 9780262033848L, 
						"Thomas H. Cormen; Charles E. Leiserson; Ronald L. Rivest; Clifford Stein", 4, 2, "July 31, 2009"),
				new Tag(5, "The C Programming Language", 9780131103627L, "Brian W. Kernighan; Dennis M. Ritchie", 
						4, 2, "April 1, 1988"),
				new Tag(5, "Discrete Mathematics and Its Applications", 9780073229720L, 
						"Kenneth Rosen", 5, 2, "July 26, 2006"),
				new Tag(6, "The Pragmatic Programmer: From Journeyman to Master", 785342616224L, 
						"Andrew Hunt; David Thomas", 6, 2, "October 30, 1999"),
				new Tag(7, "Design Patterns: Elements of Reusable Object-Oriented Software ", 9780201633610L,
						"Erich Gamma; Richard Helm; Ralph Johnson; John Vlissides", 7, 2, "November 10, 1994"),
				new Tag(8, "TCP/IP Illustrated, Volume 1: The Protocols (2nd Edition)", 9780321336316L, 
						"Kevin R. Fall; W. Richard Stevens", 8, 2, "November 25, 2011"),
				new Tag(9, "Concrete Mathematics: A Foundation for Computer Science ", 785342558029L, 
						"Ronald L. Graham; Donald E. Knuth; Oren Patashnik", 9, 2, "March 10, 1994"),
				new Tag(10, "Modern Operating Systems (4th Edition)", 9780133591620L, 
						"Andrew S. Tanenbaum; Herbert Bos", 10, 2, "March 20, 2014")
		};
		for(int i = 0; i < tags.length; i++) {
			gui.addTag(tags[i]);
			gui.addId(Integer.toString(tags[i].getID()));
		}
		gui.setUp();
		
	}

}
