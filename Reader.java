import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;


public class Reader {
	
	private final int BLOCK_SIZE = 16;
	
	private SecureRandom numGenerator;
	private BitSet key;
	private BitSet key1;
	private BitSet key2;
	private BitSet random;
	private int messageLength = 0;
	private byte[] cipher;
	
	public static ArrayList<BitSet> egg = new ArrayList<BitSet>();
	
	
	public Reader() {
		try {
			this.numGenerator = SecureRandom.getInstance("SHA1PRNG");
			this.numGenerator.setSeed(System.nanoTime());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] reconstructMessage(boolean isPadded) {
		Iterator<BitSet> iterator = Reader.egg.iterator();
		ArrayList<Byte> list = new ArrayList<Byte>();
		
		while(iterator.hasNext()) {
			BitSet ciphertext = iterator.next();
			this.cipher = ciphertext.toByteArray();
			byte[] plaintext = decrypt(ciphertext, this.messageLength);
			
			if(!iterator.hasNext() && isPadded) {
				BitSet last = BitSet.valueOf(plaintext);
				int delimiter = last.length();
				for(int i = 0; i < delimiter / 8; i++) {
					list.add(plaintext[i]);
				}
			}
			else {
				for(int i = 0; i < plaintext.length; i++) {
					list.add( new Byte(plaintext[i]) );
				}
			}
		}
		
		byte[] ret = new byte[list.size()];
		for(int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}
	
	public static void storeMessage(BitSet bitSet) {
		egg.add(bitSet);
	}
	
	public void setRandomNumberGen(SecureRandom newGenerator) {
		if(newGenerator != null) {
			this.numGenerator = newGenerator;
		}
	}
	
	public void createNewRandomNumberGen() {
		try {
			this.numGenerator = SecureRandom.getInstance("SHA1PRNG");
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param length Length of key in bytes
	 */
	private void generateNewKey(int length) {
		
		this.key = createEvenKey(length * 8);
		this.key1 = Utils.copyFrom(this.key, 0, length / 2 * 8);
		this.key2 = Utils.copyFrom(this.key, length / 2 * 8, length* 8);
		
	}
	
	/**
	 * 
	 * @param length Length of random number in bytes
	 */
	public void generateRandomNumber(int length) {
		byte[] randomNumber = new byte[length];
		this.numGenerator.nextBytes(randomNumber);
		this.random = BitSet.valueOf(randomNumber);
	}
	
	public BitSet encrypt(byte[] plaintext) {
		
		BitSet pr = BitSet.valueOf(plaintext);
		BitSet a = (BitSet) pr.clone();
		BitSet b = (BitSet) pr.clone();
		a.xor(this.random);
		a.xor(this.key1);
		b.xor(this.key2);
		
		return Crypto.merge(a, this.random, b, key, plaintext.length * 8);
	}
	
	public byte[] decrypt(BitSet ciphertext, int len) {

		BitSet reverseKey = Utils.reverseOrder(key1, key2, len);
		
		BitSet[] arr = Crypto.separate(ciphertext, reverseKey, len * 3);
		BitSet pt = (BitSet)arr[1].clone();
		BitSet n = (BitSet)arr[0].clone();
		
		pt.xor(this.key1);
		n.xor(pt);
		n.xor(this.key2);
		
		if(n.equals(arr[2])) {
			return pt.toByteArray();
		}
		else {
			return null;			
		}
	}
	
	/**
	 * 
	 * @param length Length in bits
	 * @return
	 */
	public BitSet createEvenKey(int length) {
		
		////////////////////////////////////////  SHOULD THROW EXCEPTION, NOT RETURN NULL
		if(length < 0 || length % 2 != 0) {
			return null;
		}
		
		BitSet evenBits = new BitSet(length);
		int zeroCount = 0;
		int oneCount = 0;
		byte[] ran = new byte[length / 8];
		this.numGenerator.nextBytes(ran);
		BitSet random = BitSet.valueOf(ran);
		
		int i = 0;
		while(zeroCount < length / 2 && oneCount < length / 2) {
			if(random.get(i)) {
				evenBits.set(i);
				oneCount++;
			}
			else {
				zeroCount++;
			}
			i++;
		}
		
		if(zeroCount == length / 2) {
			while(i < length) {
				evenBits.set(i++);
			}
		}
		
		return evenBits;
	}
	
	public void distributeKeys(Tag tag) {
		tag.setKeys(this.key, this.key1, this.key2);
	}
	
	public void distributeRandomNumber(Tag tag) {
		tag.setRandom(this.random);
	}
	
	public static void egg() {
		BitSet a = new BitSet(24);
		a.set(0);
		a.set(2);
		a.set(4);
		a.set(5);
		a.set(6);
		a.set(7);
		a.set(8);
		a.set(11);
		a.set(12);
		a.set(13);
		a.set(15);
		a.set(17);
		a.set(18);
		a.set(19);
		a.set(20);
		a.set(21);
		a.set(23);
		
		BitSet b = new BitSet(24);
		b.set(2);
		b.set(4);
		b.set(5);
		b.set(6);
		b.set(7);
		b.set(8);
		b.set(11);
		b.set(12);
		b.set(13);
		b.set(14);
		b.set(15);
		b.set(16);
		b.set(17);
		b.set(21);
		b.set(22);
		b.set(23);
		
		BitSet c = new BitSet(24);
		c.set(1);
		c.set(2);
		c.set(3);
		c.set(4);
		c.set(7);
		c.set(8);
		c.set(11);
		c.set(13);
		c.set(14);
		c.set(15);
		c.set(16);
		c.set(20);
		c.set(21);
		c.set(22);
		c.set(23);
		
		BitSet k = new BitSet(48);
		k.set(2);
		k.set(8);
		k.set(11);
		k.set(14);
		k.set(16);
		k.set(17);
		k.set(18);
		k.set(20);
		k.set(22);
		k.set(24);
		k.set(25);
		k.set(27);
		k.set(28);
		k.set(31);
		k.set(37);
		k.set(38);
		k.set(39);
		k.set(40);
		k.set(41);
		k.set(43);
		k.set(44);
		k.set(45);
		k.set(46);
		k.set(47);
		
		BitSet k1 = new BitSet(24);
		k1.set(2);
		k1.set(8);
		k1.set(11);
		k1.set(14);
		k1.set(16);
		k1.set(17);
		k1.set(18);
		k1.set(20);
		k1.set(22);
		
		BitSet k2 = new BitSet(24);
		k2.set(24 - 24);
		k2.set(25 - 24);
		k2.set(27 - 24);
		k2.set(28 - 24);
		k2.set(31 - 24);
		k2.set(37 - 24);
		k2.set(38 - 24);
		k2.set(39 - 24);
		k2.set(40 - 24);
		k2.set(41 - 24);
		k2.set(43 - 24);
		k2.set(44 - 24);
		k2.set(45 - 24);
		k2.set(46 - 24);
		k2.set(47 - 24);
		
		Tag t = new Tag();
		t.setKey1((BitSet)k1.clone());
		t.setKey2((BitSet)k2.clone());
		
		BitSet m = Crypto.merge(a, b, c, k, 24);
		
		BitSet[] arr = Crypto.separate(m, k, 72);
		
		t.setKey((BitSet)k.clone());
		t.setRandom((BitSet)b.clone());
		BitSet s = BitSet.valueOf(t.decrypt(m, 72));
		
	}
	
	public void talk(Tag tag) {
		int length = Integer.BYTES;
		this.messageLength = length * 3 * 8;
		generateNewKey(length * 2);
		generateRandomNumber(length);
		distributeKeys(tag);
		distributeRandomNumber(tag);
		
		byte[] temp = ByteBuffer.allocate(length).putInt(this.numGenerator.nextInt()).array();
		BitSet ciphertext = encrypt( temp );
		tag.receiveMessage(ciphertext, length * 3 * 8);
		tag.getAuthor();
		byte[] plaintext = reconstructMessage(tag.respond());
		
		String author = new String(plaintext);
		System.out.println("Author: " + author);
	}
	
	public Book[] readAllInfo(Tag tag) {
		Book book = new Book();
		Book altBook = new Book();
		this.messageLength = BLOCK_SIZE * 8;
		int length = BLOCK_SIZE;
		generateNewKey(length * 2);
		generateRandomNumber(length);
		distributeKeys(tag);
		distributeRandomNumber(tag);
		
		byte[] temp = ByteBuffer.allocate(length).array();
		this.numGenerator.nextBytes(temp);
		
		BitSet ciphertext = encrypt( temp );
		tag.receiveMessage(ciphertext, length * 3 * 8);
		
		tag.getId();

		byte[] plaintext = reconstructMessage(tag.respond());
		ByteBuffer buffer = ByteBuffer.wrap(plaintext);
		book.setId(buffer.getInt());
		altBook.setId(ByteBuffer.wrap(this.cipher).getInt());
		
		Reader.egg.clear();
		tag.getAuthor();
		plaintext = reconstructMessage(tag.respond());
		book.setAuthor(new String(plaintext));
		altBook.setAuthor(new String(this.cipher));
		
		Reader.egg.clear();
		tag.getIsbn();
		plaintext = reconstructMessage(tag.respond());
		buffer = ByteBuffer.wrap(plaintext);
		book.setIsbn(buffer.getLong());
		altBook.setIsbn(ByteBuffer.wrap(cipher).getLong());
		
		Reader.egg.clear();
		tag.getTitle();
		plaintext = reconstructMessage(tag.respond());
		book.setTitle(new String(plaintext));
		altBook.setTitle(new String(cipher));
		
		Reader.egg.clear();
		tag.getLibraryId();
		plaintext = reconstructMessage(tag.respond());
		buffer = ByteBuffer.wrap(plaintext);
		book.setLibraryId(buffer.getInt());
		altBook.setLibraryId(ByteBuffer.wrap(cipher).getInt());
		
		Reader.egg.clear();
		tag.getLibraryMedia();
		plaintext = reconstructMessage(tag.respond());
		buffer = ByteBuffer.wrap(plaintext);
		book.setLibraryMedia(buffer.getInt());
		altBook.setLibraryMedia(ByteBuffer.wrap(this.cipher).getInt());
		
		Reader.egg.clear();
		tag.getPublishDate();
		plaintext = reconstructMessage(tag.respond());
		book.setPublishDate(new String(plaintext));
		altBook.setPublishDate(new String(cipher));
		
		Reader.egg.clear();
		
		return new Book[]{book, altBook};
	}
}
