package de.hsa.games.fatsquirrel.botimpls;

import java.util.PriorityQueue;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class HalfRandomBot2 implements BotController, BotControllerFactory {

	@Override
	public void nextStep(ControllerContext view) {
		double nearest = 999999999;

		EntityType entity = EntityType.NONE;
		EntityType targetType = EntityType.NONE;

		XY target = XY.ZERO_ZERO;
		XY us = view.locate();
		XY topleft = new XY(view.getViewLowerLeft().x, view.getViewUpperRight().y);
		XY downright = new XY(view.getViewUpperRight().x, view.getViewLowerLeft().y);

		if (topleft.x < 0) {
			topleft = new XY(0, topleft.y);
		}
		if (topleft.y < 0) {
			topleft = new XY(topleft.x, 0);
		}

		boolean[][] entityTypeArray = new boolean[downright.y - topleft.y][downright.x - topleft.x];

		for (int i = topleft.y; i < downright.y; i++) {
			for (int j = topleft.x; j < downright.x; j++) {

				try {
					entity = view.getEntityAt(new XY(j, i));
					switch (entity) {
					case WALL:
					case MASTER_SQUIRREL:
					case MINI_SQUIRREL:
					case BAD_BEAST:
					case BAD_PLANT:
						if (i == us.y && j == us.x) {
							entityTypeArray[i - topleft.y][j - topleft.x] = true;
							break;
						}
						entityTypeArray[i - topleft.y][j - topleft.x] = false;
						break;
					case GOOD_BEAST:
					case GOOD_PLANT:
					case NONE:
						entityTypeArray[i - topleft.y][j - topleft.x] = true;
						break;
					}
				} catch (OutOfViewException e) {
					entity = EntityType.NONE;
					entityTypeArray[i - topleft.y][j - topleft.x] = false;
					continue;
				}
				if (entity == EntityType.GOOD_BEAST || entity == EntityType.GOOD_PLANT) {
					if (Math.sqrt(Math.pow(us.x - j, 2) + Math.pow(us.y - i, 2)) < nearest) {
						targetType = entity;
						nearest = Math.sqrt(Math.pow(us.x - j, 2) + Math.pow(us.y - i, 2));
						target = new XY(j, i);
					}
				}
			}
		}
		if (nearest != 999999999) {

			AStar aStar = new AStar(downright.y - topleft.y, downright.x - topleft.x, us.y - topleft.y,
					us.x - topleft.x, target.y - topleft.y, target.x - topleft.x, entityTypeArray);
			XY move = aStar.getPath();
			if (move.x == 2 || move.y == 2) {
				int moveX = Math.random() < 0.5 ? 1 : -1;
				int moveY = Math.random() < 0.5 ? 1 : -1;
				view.move(new XY(moveX, moveY));
				return;
			}
			view.move(move);
		} else {
			int moveX = Math.random() < 0.5 ? 1 : -1;
			int moveY = Math.random() < 0.5 ? 1 : -1;
			view.move(new XY(moveX, moveY));
			return;
		}
		// int moveX = 0;
		// int moveY = 0;
		//
		// if (target.x < us.x) {
		// moveX = -1;
		// } else if (target.x > us.x) {
		// moveX = 1;
		// }
		// if (target.y < us.y) {
		// moveY = -1;
		// } else if (target.y > us.y) {
		// moveY = 1;
		// }
		// if (view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) !=
		// EntityType.GOOD_BEAST
		// && view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) != EntityType.NONE) {
		// if (view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) !=
		// EntityType.GOOD_PLANT
		// && view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) != EntityType.NONE) {
		// moveX = Math.random() < 0.5 ? 1 : -1;
		// moveY = Math.random() < 0.5 ? 1 : -1;
		// view.move(new XY(moveX, moveY));
		// }
		// }
		// view.move(new XY(moveX, moveY));
		//
	}

	@Override
	public BotController createMasterBotController() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BotController createMiniBotController() {
		// TODO Auto-generated method stub
		return null;
	}

	class AStar {
		public final int DIAGONAL_COST = 1;
		public final int V_H_COST = 1;

		class Cell {
			int heuristicCost = 0; // Heuristic cost
			int finalCost = 0; // G+H
			int y, x;
			Cell parent;

			Cell(int y, int x) {
				this.y = y;
				this.x = x;
			}

			@Override
			public String toString() {
				return "[" + this.y + ", " + this.x + "]";
			}
		}

		// Blocked cells are just null Cell values in grid
		public AStar(int CELL_HEIGHT, int CELL_WIDTH, int startY, int startX, int endY, int endX, boolean[][] blocked) {
			this.CELL_HEIGHT = CELL_HEIGHT;
			this.CELL_WIDTH = CELL_WIDTH;
			this.startY = startY;
			this.startX = startX;
			this.endY = endY;
			this.endX = endX;
			this.blocked = blocked;
		}

		private PriorityQueue<Cell> open;

		private boolean[][] closed;
		private int startY, startX;
		private int endY, endX;

		private int CELL_HEIGHT;
		private int CELL_WIDTH;
		private boolean[][] blocked;
		private Cell[][] grid;

		public void setBlocked(int y, int x) {
			grid[y][x] = null;
		}

		public void setStartCell(int y, int x) {
			startY = y;
			startX = x;
		}

		public void setEndCell(int y, int x) {
			endY = y;
			endX = x;
		}

		void checkAndUpdateCost(Cell current, Cell t, int cost) {
			if (t == null || closed[t.y][t.x])
				return;
			int t_final_cost = t.heuristicCost + cost;

			boolean inOpen = open.contains(t);
			if (!inOpen || t_final_cost < t.finalCost) {
				t.finalCost = t_final_cost;
				t.parent = current;
				if (!inOpen)
					open.add(t);
			}
		}

		public void AStarcalc() {

			// add the start location to open list.
			open.add(grid[startY][startX]);

			Cell current;

			while (true) {
				current = open.poll();
				if (current == null)
					break;
				closed[current.y][current.x] = true;

				if (current.equals(grid[endY][endX])) {
					return;
				}

				Cell t;
				if (current.y - 1 >= 0) {
					t = grid[current.y - 1][current.x];
					checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

					if (current.x - 1 >= 0) {
						t = grid[current.y - 1][current.x - 1];
						checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
					}

					if (current.x + 1 < grid[0].length) {
						t = grid[current.y - 1][current.x + 1];
						checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
					}
				}

				if (current.x - 1 >= 0) {
					t = grid[current.y][current.x - 1];
					checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
				}

				if (current.x + 1 < grid[0].length) {
					t = grid[current.y][current.x + 1];
					checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
				}

				if (current.y + 1 < grid.length) {
					t = grid[current.y + 1][current.x];
					checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

					if (current.x - 1 >= 0) {
						t = grid[current.y + 1][current.x - 1];
						checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
					}

					if (current.x + 1 < grid[0].length) {
						t = grid[current.y + 1][current.x + 1];
						checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
					}
				}
			}
		}

		/*
		 * Params : tCase = test case No. x, y = Board's dimensions si, sj = start
		 * location's x and y coordinates ei, ej = end location's x and y coordinates
		 * int[][] blocked = array containing inaccessible cell coordinates
		 */
		public XY getPath() {
			// Reset
			grid = new Cell[CELL_HEIGHT][CELL_WIDTH];
			closed = new boolean[CELL_HEIGHT][CELL_WIDTH];
			open = new PriorityQueue<>((Object o1, Object o2) -> {
				Cell c1 = (Cell) o1;
				Cell c2 = (Cell) o2;

				return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
			});
			// Set start position
			setStartCell(startY, startX);

			// Set End Location
			setEndCell(endY, endX);

			for (int y = 0; y < CELL_HEIGHT; ++y) {
				for (int x = 0; x < CELL_WIDTH; ++x) { // CHANGED
					grid[y][x] = new Cell(y, x);
					grid[y][x].heuristicCost = (int) Math
							.sqrt(Math.pow(Math.abs(y - endY), 2) + Math.pow(Math.abs(x - endX), 2));
				}
				// System.out.println();
			}
			grid[startY][startX].finalCost = 0;

			/*
			 * Set blocked cells. Simply set the cell values to null for blocked cells.
			 */
			for (int y = 0; y < blocked.length; y++) {
				for (int x = 0; x < blocked[0].length; x++) {
					if (blocked[y][x] == false) {
						setBlocked(y, x);
					}
				}
			}

			AStarcalc();

			if (closed[endY][endX]) {
				Cell current = grid[endY][endX];
				while (current.parent != null) {
					if (current.parent.parent == null) {
						return new XY(current.x - startX, current.y - startY);
					}
					current = current.parent;
				}
			} else
				return new XY(2, 2);
			return new XY(2, 2);
		}
	}
}
