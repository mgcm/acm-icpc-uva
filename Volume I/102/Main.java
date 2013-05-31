
import java.util.*;
import java.io.*;

class Main {

	static class Bin {
		private int brown;
		private int green;
		private int clear;

		public Bin(int b, int g, int c) {
			brown = b;
			green = g;
			clear = c;
		}

		public int getMovesForGlass(int g) {
			switch (g) {
				case 0:
					return green + clear;
				case 1:
					return brown + clear;
				case 2:
					return brown + green;
				default:
					return -1;
			}
		}
	}

	void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
            	String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
            	String[] tokens = cleanLine.split("\\s+");

            	if (tokens.length == 9) {
					Bin a = new Bin(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
					Bin b = new Bin(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
					Bin c = new Bin(Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));
					System.out.println(minimizeMovements(a, b, c));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public String minimizeMovements(Bin a, Bin b, Bin c) {
		int moves = Integer.MAX_VALUE;
		String[] solutions = new String[] { "_", "_", "_", "_", "_", "_" };
		String[] binNames = new String[] { "B", "G", "C"};
		int s = 1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					if (i != j && j != k && i != k) {
						int value = a.getMovesForGlass(i) + b.getMovesForGlass(j) + c.getMovesForGlass(k);
						if (value < moves) {
							String solution = binNames[i] + binNames[j] + binNames[k];
							solutions = new String[] { "_", "_", "_", "_", "_", "_" };
							moves = value;
							solutions[0] = solution;
							//System.out.println("!! " + solution + ": " + value);
						} else if (value == moves) {
							String solution = binNames[i] + binNames[j] + binNames[k];
							solutions[s] = solution;
							//System.out.println(">> " + solution + ": " + value);
							s++;
						}
					}
				}
			}
		}
		Arrays.sort(solutions);

		return solutions[0] + " " + moves;
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.parseInput();
	}
}