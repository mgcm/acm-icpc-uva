
import java.util.*;
import java.io.*;

class Main {

	static class CurrencyMatrix {
		public int[][][] pred;
		public float[][][] steps;
		private int size;

		public CurrencyMatrix(int size) {
			pred = new int[size][size][size];
			steps = new float[size][size][size];
			this.size = size;
		}

		public void print() {
			for (int s = 0; s < size; s++) {
				System.out.println("PRED #" + s);
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						System.out.print(pred[i][j][s] + " ");
					}
					System.out.println();
				}
				System.out.println();
			}
		}

		public void floydWarshall() {
			for (int i = 0; i < size; i ++)
				for (int j = 0; j < size; j ++)
					if (i != j) pred[i][j][0] = i;
					else pred[i][j][0] = -1;

			// floyd-warshal adapted
			for (int s = 1; s < size; s ++) 
				for (int k = 0; k < size; k ++)
					for (int i = 0; i < size; i ++)
						for (int j = 0; j < size; j ++) {
							float value = steps[i][k][s-1] * steps[k][j][0];
							if (value > steps[i][j][s]) {
								steps[i][j][s] = value;
								pred[i][j][s] = k;
							}
						}

			// find shortest exchange with profit over 1%
			int step = -1;
			int idx = -1;
			for (int s = 0; s < size; s++) {
				for (int i = 0; i < size; i++) {
					if (steps[i][i][s] > 1.01) {
						idx = i;
						step = s;
						break;
					}
				}
				if (idx != -1) break;
			}

			if (idx == -1) {
				System.out.println("no arbitrage sequence exists");
			} else {
				// construct the path backwords using the predecessor lookup
				Vector path = new Vector();

				int c = idx;
				path.add(idx);
				for (int s = step; s >= 0; s--) {
					c = pred[idx][c][s];
					path.add(c);
				}
				Collections.reverse(path);
				for (int i = 0; i < path.size(); i ++) {
					System.out.print(((int)path.get(i)+1));
					if (i < path.size() - 1) System.out.print(" ");
				}
				System.out.println();
			}
		}
	}

	public void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
				String[] tokens = cleanLine.split("\\s+");

				if (tokens.length == 1) {
					int size = Integer.parseInt(tokens[0]);
					CurrencyMatrix m = new CurrencyMatrix(size);
					for (int i = 0; i < size; i++) {
						String otherLine = reader.readLine().trim().toLowerCase().replaceAll("\\s+", " ");
						String[] values = otherLine.split("\\s+");
						int valueIndex = 0;
						for (int j = 0; j < size; j++) {
							if (i == j) {
								m.steps[i][j][0] = 1.0f;
							} else {
								m.steps[i][j][0] = Float.parseFloat(values[valueIndex]);
								valueIndex ++;
							}
						}
					}
					m.floydWarshall();
					//m.print();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.parseInput();
	}
}