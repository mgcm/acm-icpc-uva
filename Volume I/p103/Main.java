
import java.util.*;
import java.io.*;

class Main {

	static class Box {
		private int dimensions;
		private int order;
		private int sum;
		private int[] sides;

		public Box(int d, int idx, String[] s) {
			dimensions = d;
			order = idx;
			sides = new int[dimensions];
			for (int i = 0; i < d; i++) {
				sides[i] = Integer.parseInt(s[i]);
				sum += sides[i];
			}

			Arrays.sort(sides);
		}

		public int getDimensions() {
			return dimensions;
		}

		public int getOrder() {
			return order + 1;
		}

		public int sum() {
			return sum;
		}

		public void printSides() {
			System.out.print((order+1) + ". ");
			for (int j = 0; j < dimensions; j++) {
				System.out.print(sides[j] + " ");
			}
		}

		public int side(int idx) {
			if (idx < dimensions) return sides[idx];
			else return -1;
		}
	}

	static class StackedBoxSolver {
		private int boxCounter;
		private Box[] boxes;
		private int bestNesting;
		private int currentNesting;
		private Box[] bestNest;
		private Box[] currentNest;

		public StackedBoxSolver(int numBoxes) {
			boxes = new Box[numBoxes];
			bestNest = new Box[numBoxes];
			currentNest = new Box[numBoxes];
			boxCounter = 0;
			bestNesting = 0;
			currentNesting = 0;
		}

		public void addBox(Box b) {
			boxes[boxCounter] = b;
			boxCounter ++;
		}

		public boolean fitsInside(Box a, Box b) {
			for (int d = 0; d < a.getDimensions(); d ++) {
				if (a.side(d) >= b.side(d)) return false;
			}

			return true;
		}

		public void sortBoxes() {
			for (int i = 1; i < boxes.length; i++) {
				 Box b = boxes[i];
				 int index = i;
				 int dim = 0;
				 //System.out.println(b.side(dim));
				 while (index > 0 && dim < b.getDimensions()) {
				 	while ( index-1 >= 0 && b.side(dim) < boxes[index-1].side(dim)) {
				 		//System.out.println(b.side(dim) + " swap " + boxes[index-1].side(dim));
				 		boxes[index] = boxes[index-1];
				 		index --;
				 	}
				 	boxes[index] = b;
				 	dim ++;
				 }
			}
/*
			for (int i = 0; i < boxes.length; i++) {
				Box b = boxes[i];
				b.printSides();
				System.out.println();
			}
*/
		}

		public void longestFit(int idx, int nesting) {
			currentNest[nesting-1] = boxes[idx];
			if (nesting > bestNesting) {
				bestNesting = nesting;
				for (int x = 0; x < bestNesting; x++)
					bestNest[x] = currentNest[x];
			}
			for (int i = idx + 1; i < boxes.length; i++) {
				if (fitsInside(boxes[idx], boxes[i])) {
					longestFit(i, nesting + 1);
				}
			}
		}

		public int getBestNesting() {
			return bestNesting;
		}

		public void printBestNest() {
			for (int i = 0; i < bestNesting; i++) {
				System.out.print(bestNest[i].getOrder() + " ");
			}
			System.out.println();
		}
	}

	void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			while ((line = reader.readLine()) != null) {
            	String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
            	String[] tokens = cleanLine.split("\\s+");

            	if (tokens.length == 2) {
					int numBoxes = Integer.parseInt(tokens[0]);
					int dim = Integer.parseInt(tokens[1]);
					StackedBoxSolver solver = new StackedBoxSolver(numBoxes);

					for (int i = 0; i < numBoxes; i++) {
						cleanLine = reader.readLine().trim().toLowerCase().replaceAll("\\s+", " ");
						tokens = cleanLine.split("\\s+");
						if (tokens.length == dim) {
							solver.addBox(new Box(dim, i, tokens));
						}
					}
					solver.sortBoxes();

					for (int i = 0; i < numBoxes; i++) {
						solver.longestFit(i, 1);
					}

					System.out.println(solver.getBestNesting());
					solver.printBestNest();
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