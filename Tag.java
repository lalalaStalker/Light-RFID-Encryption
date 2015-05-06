import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

/**
 * An object that receives encrypted messages from a Reader, and 
 * encrypts and sends messages to a Reader.
 */
public class Tag {
	
	private final String CHARSET = "UTF-8";
	
	private BitSet key;
	private BitSet key1;
	private BitSet key2;
	private BitSet random;
	private BitSet presevedRandomNumber;
	private byte[] message;
	private byte[] response;
	
	private int id;	
	private String title;
	private long isbn;
	private String author;
	private int libraryId;
	private int libraryMedia;
	private String publishDate;
	
	public Tag() {
		
	}
	
	public Tag( int id, String title, long isbn, String author, int libId, 
				int libMedia, String publishDate) {
		
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.libraryId = libId;
		this.libraryMedia = libMedia;
		this.publishDate = publishDate;
	}
	
	
	public void receiveMessage(BitSet ciphertext, int len) {
		byte[] message = decrypt(ciphertext, len);
		this.message = message;
	}
	
	/**
	 * Respond to message
	 * @param Boolean to signify if padded msg or not
	 */
	public boolean respond() {
		
		boolean padded = false;
		
		int blockSize = this.message.length;
		
		boolean first = true;
		for(int i = 0; i < this.response.length; i+= blockSize) {
			byte[] block;
			BitSet response;
			if(this.response.length - i >= blockSize) {
				block = Arrays.copyOfRange(this.response, i, i + blockSize);
				response = encrypt(block);
			}
			else {
				block = new byte[blockSize];
				int j;
				for(j = 0; i < this.response.length; i++, j++) {
					block[j] = this.response[i];
				}
				for(; j < blockSize; j++) {
					if(first) {
						block[j] = (byte)1;
						first = false;
					}
					else {
						block[j] = 0;						
					}
				}
				response = encrypt(block);
				padded = true;
			}
			Reader.storeMessage(response);
		}
		
		return padded;
	}

	
	/**
	 * 
	 * @param ciphertext
	 * @param len Length in bits
	 * @return
	 */
	public byte[] decrypt(BitSet ciphertext, int len) {
		
		BitSet[] arr = Crypto.separate(ciphertext, this.key, len);

		BitSet pr = (BitSet)arr[2].clone();
		BitSet n = (BitSet)arr[0].clone();
		
		pr.xor(this.key2);

		n.xor(pr);
		n.xor(this.key1);
		
		this.presevedRandomNumber = (BitSet)arr[1].clone();

		if(n.equals(arr[1])) {
			return pr.toByteArray();
		}
		else {
			return null;			
		}
	}
	
	/**
	 * Encrypts a plaintext byte array into a ciphertext BitSet
	 * @param plaintext byte array
	 * @return Encrypted BitSet
	 */
	public BitSet encrypt(byte[] plaintext) {
		
		BitSet d = BitSet.valueOf(plaintext);
		BitSet e = BitSet.valueOf(plaintext);
		BitSet reverse = Utils.reverseOrder(this.key1, this.key2, plaintext.length * 8);

		d.xor(this.presevedRandomNumber);
		d.xor(this.key2);
		e.xor(this.key1);
		
		return Crypto.merge(d, e, this.presevedRandomNumber, reverse, plaintext.length * 8);
	}
	
	public void setKeys(BitSet key, BitSet key1, BitSet key2) {
		this.key = key;
		this.key1 = key1;
		this.key2 = key2;
	}
	
	public void setRandom(BitSet random) {
		this.random = random;
	}
	
	public void setKey(BitSet newKey) {
		this.key = newKey;
	}
	
	public void setKey1(BitSet newKey) {
		this.key1 = newKey;
	}
	
	public void setKey2(BitSet newKey) {
		this.key2 = newKey;
	}
	
	//************************ REGULAR DATA BELOW ***************************
	
	public void getId() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(this.id);
		this.response = buffer.array();
	}

	public void getTitle() {
		this.response = this.title.getBytes(Charset.forName(CHARSET));
	}

	public void getIsbn() {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(this.isbn);
		this.response = buffer.array();
	}

	public void getAuthor() {
		this.response = this.author.getBytes(Charset.forName(CHARSET));
	}

	public void getLibraryId() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(this.libraryId);
		this.response = buffer.array();
	}

	public void getLibraryMedia() {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(this.libraryMedia);
		this.response = buffer.array();
	}
	
	public void getPublishDate() {
		this.response = this.publishDate.getBytes();
	}
	
	public int getID() {
		return this.id;
	}
}
