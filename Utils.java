import java.util.BitSet;

public class Utils {
	
	public static boolean equalTest(BitSet a, BitSet b, int len) {
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.print("Correct Random:\t\t");
//		Utils.print(a, len);
//		System.out.print("Incorrect Random:\t");
//		Utils.print(b, len);
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
		a.xor(b);
		if(a.isEmpty()) {
			return true;
		}
		else {			
			return false;
		}
	}

	/**
	 * 
	 * @param bitSet The BitSet we want to print
	 * @param len Length of the BitSet in bits
	 */
	public static void print(BitSet bitSet, int len) {
		for(int i = 0; i < len; i++) {
			if(i % 4 == 0) {
				System.out.print("  ");
			}
			if(bitSet.get(i)) {
				System.out.print(1 + " ");
			}
			else {
				System.out.print(0 + " ");
			}
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param bitSet
	 * @param start
	 * @param end The length of the BitSet, the last index-thingy
	 * @return
	 */
	public static BitSet copyFrom(BitSet bitSet, int start, int end) {
		int j = start;
		BitSet ret = new BitSet();
		for(int i = 0; i < end; i++) {
			if(bitSet.get(j++)) {
				ret.set(i);
			}
			else {
				ret.clear(i);
			}
		}
		return ret;
	}
	
	public static long[] reverseOrder(long[] a, long[] b) {
		long[] k = new long[a.length + b.length];
		int i;
		for(i = 0; i < b.length; i++) {
			k[i] = b[i];
		}
		for(int j = 0; j < b.length; j++) {
			k[i++] = a[j];
		}
		
		return k;
	}
	
	public static BitSet reverseOrder(BitSet key1, BitSet key2, int len) {
		
		BitSet ret = new BitSet(len * 2);
		int i;
		for(i = 0; i < len; i++) {
			if(key2.get(i)) {
				ret.set(i);
			}
		}
		int j;
		for(j = 0; j < len; j++,i++) {
			if(key1.get(j)) {
				ret.set(i);
			}
		}
		return  ret;
	}

}
