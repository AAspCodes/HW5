import java.io.PrintStream;

public class HuffmanTree {

	/**
	 * Constructs a Huffman tree using the given array of frequencies where count[i]
	 * is the number of occurrences of the character with ASCII value i.
	 */
	public HuffmanTree(int[] count) {
	}
	

	/**
	 * Writes the current tree to the given output stream in standard format.
	 */
	public void write(PrintStream output) {
	}

	private class HuffmanNode implements Comparable<HuffmanNode> {
		private HuffmanNode left, right;
		private int asciiVal, freq;

		private HuffmanNode(int asciiVal, int freq) {
			this.asciiVal = asciiVal;
			this.freq = freq;
		}

		public String toString() {
			return this.asciiVal + " : " + this.freq;
		}
	}
}
