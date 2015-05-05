import java.util.BitSet;


public class Crypto {
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param key
	 * @param len Length in bits
	 * @return
	 */
	public static BitSet merge(BitSet a, BitSet b, BitSet c, BitSet key, int len) {
		int keyLength = 2 * len;
		BitSet d = new BitSet(len * 3);
		
		int i = 0;
		int j = 0;
		int k = 0;
		int m = (keyLength / 2) - 1;
		int n = (keyLength / 2) - 1;
		
		for(int p = 0; p < keyLength; p++) {
			if(!key.get(p)) {
				if(!key.get((keyLength - 1) - p)) {
					d.set(k++, a.get(i));
					d.set(k++, b.get(i++));
				}
				else {
					d.set(k++, a.get(m));
					d.set(k++, b.get(m--));
				}
			}
			else {
				if(!key.get((keyLength - 1) - p)) {
					d.set(k++, c.get(j++));
				}
				else {
					d.set(k++, c.get(n--));
				}
			}
		}
		return d;
	}
	
	/**
	 * 
	 * @param d
	 * @param key
	 * @param len Length in bits
	 * @return
	 */
	public static BitSet[] separate(BitSet d, BitSet key, int len) {
		int i = 0;
		int j = 0;
		int k = 0;
		int keyLength = (len / 3) * 2;
		int m = (len / 3) - 1;
		int n = (len / 3) - 1;
		
		BitSet a = new BitSet(len / 3);
		BitSet b = new BitSet(len / 3);
		BitSet c = new BitSet(len / 3);
		
		for(int p = 0; p < keyLength; p++) {
			if(!key.get(p)) {
				if(!key.get((keyLength - 1) - p)) {
					a.set(i, d.get(k++));
					b.set(i++, d.get(k++));
				}
				else {
					a.set(m, d.get(k++));
					b.set(m--, d.get(k++));
				}
			}
			else {
				if(!key.get((keyLength - 1) - p)) {
					c.set(j++, d.get(k++));
				}
				else {
					c.set(n--, d.get(k++));
				}
			}
		}
		
		return new BitSet[] {a, b, c};
	}

}
