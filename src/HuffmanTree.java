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
		tree.travWrite(output, "");
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

		@Override
		public int compareTo(HuffmanNode that) {
			return this.freq - that.freq;
		}

		private void travWrite(PrintStream output, String code) {
			if (asciiVal != 0) {
				output.println(asciiVal);
				output.println(code);
			} else {
				if (left != null) {
					left.travWrite(output, code + "0");
				}
				if (right != null) {
					right.travWrite(output, code + "1");
				}
			}
		}

	}
}
