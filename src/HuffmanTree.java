import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanTree {
	private HuffmanNode tree;

	/**
	 * Constructs a Huffman tree using the given array of frequencies where count[i]
	 * is the number of occurrences of the character with ASCII value i.
	 */
	public HuffmanTree(int[] count) {

		Queue<HuffmanNode> nodeQ = new PriorityQueue<HuffmanNode>();

		// assign non zero frequency values from the count array to the priority queue
		for (int i = 0; i < count.length; i++) {
			if (count[i] != 0) {
				nodeQ.add(new HuffmanNode(i, count[i]));
			}
		}

		// add end of file char.
		nodeQ.add(new HuffmanNode(count.length, 1));

		// build tree
		while (nodeQ.size() > 1) {
			HuffmanNode leaf1 = nodeQ.remove();
			HuffmanNode leaf2 = nodeQ.remove();
			HuffmanNode branch = new HuffmanNode(0, leaf1.freq + leaf2.freq);

			if (leaf1.freq <= leaf2.freq) {
				branch.left = leaf1;
				branch.right = leaf2;
			} else {
				branch.left = leaf2;
				branch.right = leaf1;
			}

			nodeQ.add(branch);
		}

		// assign tree
		this.tree = nodeQ.remove();

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
