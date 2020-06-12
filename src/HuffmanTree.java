import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

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
			if (count[i] > 0) {
				nodeQ.add(new HuffmanNode(i, count[i]));
			}
		}

		// add end of file char.
		nodeQ.add(new HuffmanNode(count.length, 1));

		// build tree
		while (nodeQ.size() > 1) {
			HuffmanNode leaf1 = nodeQ.remove();
			HuffmanNode leaf2 = nodeQ.remove();
			HuffmanNode branch = new HuffmanNode(-1, leaf1.freq + leaf2.freq);

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
	 * Constructs a Huffman tree from the Scanner.
	 * Assumes the Scanner contains a tree description in standard format.
	 * @param codeInput
	 */
	public HuffmanTree(Scanner codeInput) {
		
		HuffmanNode root = new HuffmanNode();
		HuffmanNode node;
		while ( codeInput.hasNextLine()) {
			int asciiVal = Integer.parseInt(codeInput.nextLine());
			char[] code = codeInput.nextLine().toCharArray();
			node = root;
			
			for (char direction: code) {
				if (direction == '0') {
					// go left
					if (node.left == null) {
						// make a new branch and continue
						node.left = new HuffmanNode();
					} 
					// traverse to that branch
					node = node.left;
						
				} else {
					// go right
					
					if (node.right == null) {
						// make a new branch
						node.right = new HuffmanNode();
					}
					// traverse to the right branch
					node = node.right;
					
				}
			}
			node.asciiVal = asciiVal;
		}
	
		tree = root;
	}
	
	
	/**
	 * Reads bits from the given input stream and writes the corresponding
	 * characters to the output.  Stops reading when it encounters a character
	 * with value equal to eof.  This is a pseudo-eof character, so it should
	 * not be written to the output file.  Assumes the input stream contains
	 * a legal encoding of characters for this tree’s Huffman code.
	 * @param input
	 * @param output
	 * @param eof
	 */
	public void decode(BitInputStream input, PrintStream output, int eof) {
		
		// I think this works, but I can't test it just yet.
		int bit;
		HuffmanNode node = tree;
		while ((bit = input.readBit()) >= 0) {
			// check if at branch
			if (node.asciiVal == -1) {
				if (bit == 0) {
					node = node.left;
					continue;
				} else {
					node = node.right;
					continue;
				}
			} else {
				if (node.asciiVal == eof) {
					// you found the end of the file
					break;
				} else {
					output.write(node.asciiVal);
				}
			}
		}
		
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
		
		private HuffmanNode() {
			this(-1,0);
		}

		public String toString() {
			return this.asciiVal + " : " + this.freq;
		}

		@Override
		public int compareTo(HuffmanNode that) {
			if ( code != null) {
				
			}
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
		
		public void add(String code, HuffmanNode leaf) {
			if ( code.substring(0,1).equals("0")) {
				if (left == null) {
					left = leaf;
				} else {
					left.add(code.substring(1,code.length()),leaf);
				}
			} else {
				if (right == null) {
					right = leaf;
				} else {
					right.add(code.substring(1,code.length()),leaf);
				}
			}
		}
	}

}
