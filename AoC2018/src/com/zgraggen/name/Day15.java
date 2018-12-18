package com.zgraggen.name;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class Cell {
	public final Boolean isWall;
	private Creature creature = null;
	
	public Cell(boolean isWall) {
		this.isWall = isWall;
	}
	
	public Cell(char creatureName) {
		this.isWall = false;
		creature = new Creature(creatureName);
	}
	
	public boolean isFree() {
		return !isWall && creature == null;
	}
	
	public void add(Creature c) {
		creature = c;
	}
	
	public Creature remove() {
		Creature tmp = creature;
		creature = null;
		return tmp;
	}

	@Override
	public String toString() {
		if(isWall) {
			return "#";
		}
		if(creature == null) {
			return ".";
		}
		return creature.toString();
	}

	public boolean nextTurn(Grid grid, Point p, int round) {
		if(creature!=null) {
			if(!creature.hasMoved(round)) {				
				return creature.turn(grid, p);
			}
		}
		return true;
	}


	public boolean isEnemy(char name) {
		if(creature != null) {
			if(creature.getEnemyName() == name) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEnemy(Cell cell) {
		if(cell.creature != null ) {
			return this.isEnemy(cell.creature.name);
		}
		return false;
	}

	public void attack(int attack) {
		if(creature != null) {
			creature.hit(attack);
		}
	}

	public boolean isDead() {
		return creature.getHealth()<0;
	}

	public int getHealth() {
		return creature.getHealth();
	}

}

class Creature{
	public static final HashMap<Integer, Creature> elves = new HashMap<>();
	public static final HashMap<Integer, Creature> goblins = new HashMap<>();
			
	public final char name;
	private int health;
	private int turn;
	
	public Creature(char name) {
		this.name = name;
		this.health = 200;
		this.turn = 0;
		
		if(name=='E') {
			elves.put(this.hashCode(), this);
		} else if (name == 'G') {
			goblins.put(this.hashCode(), this);
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public void hit(int attack) {
		this.health -= attack;
		if(this.health<0) {
			if(name=='E') {
				elves.remove(this.hashCode());
			} else if (name == 'G') {
				goblins.remove(this.hashCode());
			}
		}
	}
	
	@Override
	public String toString() {
		return String.valueOf(name); // + "(" + this.health + ")";
	}
	
	public boolean hasMoved(int turn) {
		return this.turn == turn;
	}
	
	public boolean turn(Grid grid, Point currentP) {
		this.turn++;
		//Are there enemies left?
		if(name=='E') {
			if(goblins.size()==0) {
				return false;
			}
		} else if (name == 'G') {
			if(elves.size()==0) {
				return false;
			}
		}
		
		//adjacent to Enemy?
		boolean attacked = attackAdjacentEnemy(grid, currentP);
		
		if(!attacked) {
			//Next Move
			ArrayList<ArrayList<Point>> paths = new ArrayList<>();
			Point nextP = nextMoves(grid, currentP, paths);
			
			//Move
			if(nextP != null) {
				grid.move(currentP, nextP);
				currentP = nextP;
				attackAdjacentEnemy(grid, currentP);
			}
			
		}		
		return true;
	}
	
	public char getEnemyName() {
		if(this.name == 'E') {
			return 'G';
		} else if(this.name == 'G') {
			return 'E';
		} else {
			return '@';
		}
	}

	private Point up(Point p) {
		return new Point(p.x, p.y-1);
	}
	private Point left(Point p) {
		return new Point(p.x-1, p.y);
	}
	private Point right(Point p) {
		return new Point(p.x+1, p.y);
	}
	private Point down(Point p) {
		return new Point(p.x, p.y+1);
	}
	
	private boolean attackAdjacentEnemy(Grid grid, Point p) {
		int lowestHits=205;
		Point lowestEnemy = null; 
		
		Point directionOfAttack = up(p);
		if(Grid.isInside(directionOfAttack) && grid.get(directionOfAttack).isEnemy(name)) {
			if(lowestHits>Grid.getHealth(directionOfAttack)) {
				lowestHits = Grid.getHealth(directionOfAttack);
				lowestEnemy = directionOfAttack;
			}
		}
		directionOfAttack = left(p);
		if(Grid.isInside(directionOfAttack) && grid.get(directionOfAttack).isEnemy(name)) {
			if(lowestHits>Grid.getHealth(directionOfAttack)) {
				lowestHits = Grid.getHealth(directionOfAttack);
				lowestEnemy = directionOfAttack;
			}
		}
		directionOfAttack = right(p);
		if(Grid.isInside(directionOfAttack) && grid.get(directionOfAttack).isEnemy(name)) {
			if(lowestHits>Grid.getHealth(directionOfAttack)) {
				lowestHits = Grid.getHealth(directionOfAttack);
				lowestEnemy = directionOfAttack;
			}
		}
		directionOfAttack = down(p);
		if(Grid.isInside(directionOfAttack) && grid.get(directionOfAttack).isEnemy(name)) {
			if(lowestHits>Grid.getHealth(directionOfAttack)) {
				lowestHits = Grid.getHealth(directionOfAttack);
				lowestEnemy = directionOfAttack;
			}
		}
		
		if(lowestEnemy != null) {
			Grid.attack(lowestEnemy,3);
			return true;
		}
		return false;
	}	
	
	private Point nextMoves(Grid grid, Point currentP, ArrayList<ArrayList<Point>> paths) {
		createNewPath(grid, paths, up(currentP));
		createNewPath(grid, paths, left(currentP));
		createNewPath(grid, paths, right(currentP));
		createNewPath(grid, paths, down(currentP));
		
		while(!paths.isEmpty()) {
			ArrayList<Point> path = paths.remove(0);
			Point lastPoint = path.get(path.size()-1);
			
			if(isEnemy(grid, currentP, up(lastPoint))) {
				return path.get(0);
			} else if(isEnemy(grid, currentP, left(lastPoint))) {
				return path.get(0);
			} else if(isEnemy(grid, currentP, right(lastPoint))) {
				return path.get(0);
			} else if(isEnemy(grid, currentP, down(lastPoint))) {
				return path.get(0);
			}else {
				addToExistingPath(grid, paths, path, up(lastPoint));
				addToExistingPath(grid, paths, path, left(lastPoint));
				addToExistingPath(grid, paths, path, right(lastPoint));
				addToExistingPath(grid, paths, path, down(lastPoint));
			}			
		}
		return null;
	}

	private boolean isEnemy(Grid grid, Point currentP, Point nextP) {
		if(grid.get(currentP).isEnemy(grid.get(nextP))){
			return true;
		}
		return false;
	}

	private void createNewPath(Grid grid, ArrayList<ArrayList<Point>> paths, Point p) {
		if(Grid.isInside(p)) {
			if(grid.get(p).isFree()) {
				ArrayList<Point> dirs = new ArrayList<>();
				dirs.add(p);
				paths.add(dirs);
			}
		}
		
	}

	private void addToExistingPath(Grid grid, ArrayList<ArrayList<Point>> paths, ArrayList<Point> path, Point p) {
		if(Grid.isInside(p)) {
			if(grid.get(p).isFree()) {
				if(!contains(path,p)) {
					@SuppressWarnings("unchecked")
					ArrayList<Point> newPath = (ArrayList<Point>) path.clone();
					newPath.add(p);
					paths.add(newPath);
				}
			}
		}

	}

	private boolean contains(ArrayList<Point> path, Point p) {
		for(Point tmpP : path) {
			if(tmpP.equals(p)) {
				return true;
			}
		}
		return false;
	}
	
}

class Grid{
	public static HashMap<Point, Cell> grid;
	public static int maxY;
	public static int maxX;
	
	public Grid(ArrayList<String> lines){
		grid = new HashMap<>();
		initBoard(lines);
	}
	
	public static int getHealth(Point directionOfAttack) {
		return grid.get(directionOfAttack).getHealth();
	}

	public static void attack(Point directionOfAttack, int value) {
		Cell c = grid.get(directionOfAttack);
		c.attack(value);
		if(c.isDead()) {
			grid.put(directionOfAttack, new Cell(false));
		}
	}

	public void move(Point currentP, Point nextP) {
		grid.put(nextP, grid.get(currentP));
		grid.put(currentP, new Cell(false));
	}

	private static void initBoard(ArrayList<String> lines) {
		maxX=lines.get(0).trim().length();
		maxY=lines.size();
		
		int x = 0;
		int y = 0;
		for(String line : lines) {
			for(char c : line.trim().toCharArray()) {
				Cell cell = null;
				if(c=='E' || c=='G') {
					cell = new Cell(c);
				} else if (c == '.'){
					cell = new Cell(false);
				} else {
					cell = new Cell(true);
				}
				grid.put(new Point(x,y), cell);
				x++;
			}
			y++;
			x=0;
		}
	}

	public static boolean isInside(Point p) {
		if(p.getX() < 0 || p.getY() < 0 || p.getX() > maxX || p.getY() > maxY) {
			return false;
		}
		return true;
	}

	public void print() {
		for(int y=0;y<maxY;y++) {
			for(int x=0;x<maxX;x++) {
				System.out.print(grid.get(new Point(x,y)));
			}
			System.out.println();
		}
		
	}

	public Cell get(Point point) {
		return grid.get(point);
	}
}

public class Day15 {
	public static String INPUT_FILE = "Day15_Input.txt";
//	public static String INPUT_FILE = "Day15_Example.txt";
//	public static String INPUT_FILE = "Day15_Example3.txt";
	
	public static void main(String[] args) {
		ArrayList<String> lines = FileHelper.readFile(INPUT_FILE);
		Grid grid = new Grid(lines);
		
		grid.print();
		
		part1(grid);
	}
	
	private static void part1(Grid grid) {
		int round = 1;
		boolean hasMoved=true;
		
		while(hasMoved) {
			for(int y=0;y<Grid.maxY;y++) {
				for(int x=0;x<Grid.maxX;x++) {
					if(hasMoved) {
						hasMoved = grid.get(new Point(x,y)).nextTurn(grid, new Point(x,y), round);
					}
				}
			}
			System.out.println("Turn " + round);
			grid.print();
			round++;
		}
		
		int sum=0;
		if(!Creature.elves.isEmpty()) {
			for(Entry<Integer, Creature> c : Creature.elves.entrySet()) {
				sum+=c.getValue().getHealth();
			}
		} else {
			for(Entry<Integer, Creature> c : Creature.goblins.entrySet()) {
				sum+=c.getValue().getHealth();
			}	
		}
		System.out.println("ROUND: " + round);
		System.out.println("HEALTH: " + sum);
		
		System.out.println("RESULT: " + (sum*(round-2)));

		
	}

}
