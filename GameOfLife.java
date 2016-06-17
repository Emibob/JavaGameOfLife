import java.util.Scanner;
import java.io.*;

public class GameOfLife {

	private static final int M = 25;
	private static final int N = 75;
	
	public static void main (String args[]) {

		int generation = 0;

		char[][] oldGeneration = new char[(M+2)][(N+2)];
		char[][] newGeneration = new char[(M+2)][(N+2)];

		Scanner consoleReader = new Scanner(System.in);
		
		System.out.println("Which file do you want to open?");
		
		String filename = consoleReader.next();
		File file = new File(filename);
		Scanner fileReader = null;

		try {
			fileReader = new Scanner(file);
		} catch (Exception e) {
			System.out.println("file " + file + " does not exist");
			System.exit(0);
		}

		//Transfer file info to matrix & Leave a border of empty cells
		System.out.println("The file contains the following initial world:");
		for(int i = 1; i <= M; i++) {
			String line = fileReader.nextLine();

			for(int j = 1; j < line.length(); j++) {
				char cell = line.charAt(j);
				oldGeneration[i][j] = cell;
				System.out.print(cell);
			}

			System.out.println();
		}

		while(!worldIsEmpty(oldGeneration)) {

			advanceGenerations(oldGeneration, newGeneration);

			//PRINT WORLD:
			System.out.println("New generation " + generation + ":");
			generation++;
			printWorld(newGeneration);

			if((!worldIsEmpty(newGeneration)) && (!worldsAreEqual(newGeneration, oldGeneration))) {
				//TRIGGER CREATION OF NEXT WORLD:
				System.out.println("Press Y to see the next generation");
				String userInput = consoleReader.next();

				if(userInput.equals("Y")) {
					oldGetsNewValues(oldGeneration, newGeneration);
				} else {
					System.out.println("ERROR: " + userInput + " is not valid, quitting the program");
					System.exit(0);
				}
			} else {
				if(worldIsEmpty(newGeneration)){
					System.out.println("The world is empty");
				} else if (worldsAreEqual(newGeneration, oldGeneration)) {
					System.out.println("This world is the same as the previous");
				}
				System.out.println("The Game of Life Is Over");
				System.exit(0);
			}
		}
	}

	public static void oldGetsNewValues(char[][] oldGeneration, char[][] newGeneration) {
		for(int c = 1; c <= M; c++) {
			for(int d = 1; d <= N; d++) {
				oldGeneration[c][d] = newGeneration[c][d];
			}
		}
	}

	public static void advanceGenerations(char[][] oldGeneration, char[][] newGeneration) {
			for(int c = 1; c <= M; c++) {
				for(int d = 1; d <= N; d++) {
					
					int[] coords= new int[2];
					coords[0] = c;
					coords[1] = d;

					if(oldGeneration[c][d] == 'X') {
						if(getNumberOfNeighbors(oldGeneration, coords) == 2 || getNumberOfNeighbors(oldGeneration, coords) == 3) {
							newGeneration[c][d] = 'X';
						} else {
							newGeneration[c][d] = '.';
						}
					} else {
						if(getNumberOfNeighbors(oldGeneration, coords) == 3) {
							newGeneration[c][d] = 'X';
						} else {
							newGeneration[c][d] = '.';
						}
					}

				}
			}
	}

	//Should take a world and the coordinates of a cell 
	//and return the number of neighbors (organisms in neighboring cells) that the cell has.
	public static int getNumberOfNeighbors(char[][] world, int[] cellCoordinates) {
		int neighbors = 0;

		int xPosition = cellCoordinates[0];
		int yPosition = cellCoordinates[1];

		char selectedCharacter = world[xPosition][yPosition];
		
		int n1 = (world[xPosition-1][yPosition-1] == 'X') ? 1 : 0;
		int n2 = (world[xPosition][yPosition-1] == 'X') ? 1 : 0;
		int n3 = (world[xPosition+1][yPosition-1] == 'X') ? 1 : 0;
		
		int n4 = (world[xPosition-1][yPosition] == 'X') ? 1 : 0;
		int n5 = (world[xPosition+1][yPosition] == 'X') ? 1 : 0;

		int n6 = (world[xPosition-1][yPosition+1] == 'X') ? 1 : 0;
		int n7 = (world[xPosition][yPosition+1] == 'X') ? 1 : 0;
		int n8 = (world[xPosition+1][yPosition+1] == 'X') ? 1 : 0;

		neighbors = (n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8);

		return neighbors;
	}

	public static Boolean worldIsEmpty(char[][] world) {
		
		for(int a = 1; a <= M; a++) {
			for(int b = 1; b <= N; b++) {
				if(world[a][b] == 'X') {
					return false;
				}
			}
		}
		return true;
	}

	public static void printWorld(char[][] world) {
		for(int a = 1; a <= M; a++) {
			for(int b = 1; b <= N; b++) {
				System.out.print(world[a][b]);
			}
			System.out.println();
		}
	}

	public static Boolean worldsAreEqual(char[][] worldOne, char[][] worldTwo) {

		Boolean areEqual = true;

		for(int a = 1; a <= M; a++) {
			for(int b = 1; b <= N; b++) {
				if(worldOne[a][b] == worldTwo[a][b]) {
					areEqual = true;
				} else {
					return false;
				}
			}
		}
		return areEqual;
	}
}
