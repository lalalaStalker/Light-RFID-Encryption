import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ReaderGUI {

	private static int WIDTH = 700;
	private static int HEIGHT = 650;
	private static ArrayList<String> tagIDs = new ArrayList<String>();
	public static ArrayList<Tag> tagList = new ArrayList<Tag>();
	public static Reader reader = new Reader();
	
	
	
	/**
	 * Put the choices of tags to read in the ArrayList.
	 * 
	 * @param tags - String[] of the tag IDs that we can read from.
	 */
	public void setTagList( String[] tags){
		for( int i = 0; i < tags.length; i++){
			tagIDs.add( tags[i]);
		}
	}
	
	
	public ArrayList<String> getTagList(){
		return tagIDs;
	}
	
	public void addTag(Tag tag) {
		this.tagList.add(tag);
	}
	
	public void addId(String id) {
		this.tagIDs.add(id);
	}
	

	/**
	 * Get the unencrypted response from the tag, and output it to the screen.
	 * 
	 * @return - String (received message)
	 */
	public String getTextOutput(){
		return "hi peoples";
	}
	
	
	/**
	 * Create the GUI for the RFID Reader.
	 */
	public void setUp(){
		
		/*Set up window*/
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize( WIDTH, HEIGHT);
		frame.setTitle("RFID Reader");
//		frame.setDefaultCloseOperation( EXIT_ON_CLOSE);//////////////////// WTF, WHY DON'T YOU WORK??/////////
		Container pane = frame.getContentPane();
		pane.setLayout( new GridLayout( 2, 1));
		pane.setBackground( new Color( 232, 241, 250));
		Container upper = new Container();
		upper.setLayout( new GridBagLayout());
		GridBagConstraints upperGBC = new GridBagConstraints();
		Container lower = new Container();
		lower.setLayout( new GridBagLayout());
		
		GridBagConstraints lowerGBC = new GridBagConstraints();
		
		/*Put stuff in top portion of window*/
		JLabel instructions = new JLabel( "<html><p>Please select the RFID library tag you would like to read from, then " +
				"choose \"Read\" to request all information from that tag.</p></html>");
		instructions.setFont( new Font( "Book Antiqua Bold Italic", Font.BOLD, 18));
		upperGBC.gridx = 0;
		upperGBC.gridy = 0;
		upperGBC.fill = GridBagConstraints.HORIZONTAL;
		upperGBC.insets = new Insets( 10, 40, 20, 40); //top,left,bottom,right
		upperGBC.gridwidth = 4;
		upperGBC.weightx = 0.5;
		upper.add( instructions, upperGBC);

		/*List of tags to read*/
		final JComboBox<String> tagList = new JComboBox<String>( tagIDs.toArray( new String[ tagIDs.size()]));
//		tagList.setBackground( new Color( 250, 250, 250));
		upperGBC.insets = new Insets( 20, 50, 20, 5); //top,left,bottom,right
		upperGBC.fill = GridBagConstraints.VERTICAL;
		upperGBC.gridx = 2;
		upperGBC.gridy = 1;
		upperGBC.gridwidth = 1;
		upper.add( tagList, upperGBC);
		tagList.setSelectedItem( tagIDs.get(0));
		
		/*Output text*/
		final JTextArea outputArea = new JTextArea( "Message Received Area");
		outputArea.setEditable(false);
		outputArea.setPreferredSize( new Dimension( 550,250));
		lowerGBC.fill = GridBagConstraints.HORIZONTAL;
		lowerGBC.gridwidth = GridBagConstraints.REMAINDER;
		lowerGBC.weightx = 0.5;
		lowerGBC.weighty = 0.5;
		lower.add( outputArea);
		
		/*Read Button*/
		JButton readButton = new JButton( "Read");
		upperGBC.gridx = 3;
		upperGBC.insets = new Insets( 20, 5, 20, 50); //top,left,bottom,right
		upper.add( readButton, upperGBC);
		readButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e) {
				///////////////////////////// ADD CODE TO SEND THE REQUEST, RECEIVE, AND DECRYPT//////////////////
				Tag tag = ReaderGUI.tagList.get(tagList.getSelectedIndex());
				Book book[] = ReaderGUI.reader.readAllInfo(tag);
				outputArea.setText( book[0].toString() );
				outputArea.append("\n\n");
				outputArea.append(book[1].toString());
				outputArea.repaint();
			}
		});
		
		
		
		/*Add everything to the frame*/
		pane.add( upper);
		pane.add( lower);	
		frame.setVisible( true);
	}
}
