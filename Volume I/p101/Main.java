
import java.util.*;
import java.io.*;

class Main {
	private RobotArm robotArm;

	void parseInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line;
		try {
			line = reader.readLine();
			int blocks = Integer.parseInt(line);
			robotArm = new RobotArm(blocks);

			while ((line = reader.readLine()) != null) {
            	String cleanLine = line.trim().toLowerCase().replaceAll("\\s+", " ");
            	String[] tokens = cleanLine.split("\\s+");

            	if (!cleanLine.equals("quit") && tokens.length == 4) {
           			robotArm.addCommand(new Command(tokens));
            	} else {
            		robotArm.execute();
            		robotArm.printPiles();
            		return;
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

class RobotArm {
	private int numberOfBlocks;
	private Stack<Block>[] piles;
	private Block[] blocks;
	private LinkedList<Command> commands;

	public RobotArm(int b) {
		numberOfBlocks = b;
		commands = new LinkedList<Command>();

		blocks = new Block[numberOfBlocks];
		for (int i = 0; i < numberOfBlocks; i ++) {
			blocks[i] = new Block(i);
		}

		piles = new Stack[numberOfBlocks];
		for (int i = 0; i < numberOfBlocks; i ++) {
			piles[i] = new Stack<Block>();
			piles[i].push(blocks[i]);
		}
	}

	public void addCommand(Command cmd) {
		commands.add(cmd);
	}

	public void execute() {
		Command cmd;
		while ((cmd = (Command)commands.poll()) != null) {
			if (cmd.a == cmd.b) continue;

			Block a = blocks[cmd.a];
			Block b = blocks[cmd.b];
			if (a.getCurrentPosition() == b.getCurrentPosition()) continue;

			Stack s1 = piles[a.getCurrentPosition()];
			Stack s2 = piles[b.getCurrentPosition()];

			if (cmd.action == Action.MOVE && cmd.placement == Placement.ONTO) {
				// move all blocks atop A to their original positions
				Block current;
				while ((current = (Block)s1.peek()) != null) {
					if (current.getInitialPosition() == cmd.a) break;
					Stack dst = piles[current.getInitialPosition()];
					current.setCurrentPosition(current.getInitialPosition());
					dst.push(current);
					s1.pop();
 				}
 				// move all blocks atop B to their original positions
				while (!s2.empty() && (current = (Block)s2.peek()) != null) {
					if (current.getInitialPosition() == cmd.b) break;
					Stack dst = piles[current.getInitialPosition()];
					current.setCurrentPosition(current.getInitialPosition());
					dst.push(current);
					s2.pop();
 				}
 				// move A onto B
 				if (s1.empty()) break;
 				current = (Block)s1.pop();
 				current.setCurrentPosition(b.getCurrentPosition());
 				s2.push(current);
			} else if (cmd.action == Action.MOVE && cmd.placement == Placement.OVER) {
				// move all blocks atop A to their original positions
				Block current;
				while (!s1.empty() && (current = (Block)s1.peek()) != null) {
					if (current.getInitialPosition() == cmd.a) break;
					Stack dst = piles[current.getInitialPosition()];
					current.setCurrentPosition(current.getInitialPosition());
					dst.push(current);
					s1.pop();
 				}
 				// pot A on top of B
 				if (s1.empty()) break;
 				current = (Block)s1.pop();
 				current.setCurrentPosition(b.getCurrentPosition());
 				s2.push(current);
 			} else if (cmd.action == Action.PILE && cmd.placement == Placement.ONTO) {
				// move all blocks atop B to their original positions
				Block current;
				while (!s2.empty() && (current = (Block)s2.peek()) != null) {
					if (current.getInitialPosition() == cmd.b) break;
					Stack dst = piles[current.getInitialPosition()];
					current.setCurrentPosition(current.getInitialPosition());
					dst.push(current);
					s2.pop();
 				}
 				// add all blocks in A atop B
 				Stack<Block> tmp = new Stack<Block>();
				while (!s1.empty() && (current = (Block)s1.peek()) != null) {
					current.setCurrentPosition(b.getCurrentPosition());
					tmp.push(current);
					s1.pop();
 				}
 				while (!tmp.empty()) {
 					s2.push(tmp.pop());
 				}
			} else if (cmd.action == Action.PILE && cmd.placement == Placement.OVER) {
				// save all blocks atop A until we find A and pile them over B
				Block current;
				Stack<Block> tmp = new Stack<Block>();
				while ((current = (Block)s1.peek()) != null) {
					if (current.getInitialPosition() == cmd.a) {
						current.setCurrentPosition(b.getCurrentPosition());
						tmp.push(current);
						s1.pop();
						break;
					}
					current.setCurrentPosition(b.getCurrentPosition());
					tmp.push(current);
					s1.pop();
				}
				while (!tmp.empty()) {
					s2.push(tmp.pop());
				}
			}
			/*
			System.out.println("############");
			System.out.println(cmd);
			printPiles();*/
		}
	}

	public void printPiles() {
		for (int i = 0; i < numberOfBlocks; i ++) {
			System.out.print(i + ":");
			Stack s = (Stack) piles[i];
			Iterator it = s.iterator();
			while (it.hasNext()) {
				Block b = (Block)it.next();
				System.out.print(" " + b.getInitialPosition());
			}
			System.out.println();
		}
	}
}

class Command {
	public Action action;
	public Placement placement;
	public int a;
	public int b;

	public Command(String[] tokens) {
		this.action = tokens[0].equals("move") ? Action.MOVE : Action.PILE;
		this.a = Integer.parseInt(tokens[1]);
		this.placement = tokens[2].equals("onto") ? Placement.ONTO : Placement.OVER;
		this.b = Integer.parseInt(tokens[3]);
	}

	public String toString() {
		return new String(action + " " + a + " " + placement + " " + b);
	}
}

class Block {
	private int initialPosition;
	private int currentPosition;

	public Block(int pos) {
		initialPosition = pos;
		currentPosition = initialPosition;
	}

	public int getInitialPosition() {
		return initialPosition;
	}

	public void setCurrentPosition(int pos) {
		currentPosition = pos;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}
}

enum Action {
	MOVE,
	PILE
}

enum Placement {
	ONTO,
	OVER
}
