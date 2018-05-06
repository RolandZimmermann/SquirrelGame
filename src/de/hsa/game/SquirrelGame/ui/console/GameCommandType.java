package de.hsa.game.SquirrelGame.ui.console;

import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.exceptions.WrongParamInputException;

public enum GameCommandType implements CommandTypeInfo, Executeable {

	HELP("help", " list all commands") {
		@Override
		public MoveCommand execute() {
			return MoveCommand.HELP;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	}, ALL("all", " output all entitys") {

		@Override
		public MoveCommand execute() {
			return MoveCommand.ALL;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	EXIT("exit", " exit programm") {

		@Override
		public MoveCommand execute() {
			System.exit(0);
			return null;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	LEFT("left", " move left") {
		public MoveCommand execute() {
			return MoveCommand.LEFT;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	LEFTDOWN("leftdown", " move leftdown") {
		public MoveCommand execute() {
			return MoveCommand.DOWNLEFT;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	LEFTUP("leftup", " move leftup") {
		public MoveCommand execute() {
			return MoveCommand.UPLEFT;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	RIGHTDOWN("rightdown", " move rightdown") {
		public MoveCommand execute() {
			return MoveCommand.DOWNRIGHT;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	RIGHTUP("rightup", " move rightup") {
		public MoveCommand execute() {
			return MoveCommand.UPRIGHT;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	RIGHT("right", " move right") {
		public MoveCommand execute() {
			return MoveCommand.RIGHT;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	DOWN("down", " move down") {
		public MoveCommand execute() {
			return MoveCommand.DOWN;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	UP("up", " move up") {
		public MoveCommand execute() {
			return MoveCommand.UP;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	W("w", " move up") {
		public MoveCommand execute() {
			return GameCommandType.UP.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	A("a", " move left") {
		public MoveCommand execute() {
			return GameCommandType.LEFT.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	S("s", " move down") {
		public MoveCommand execute() {
			return GameCommandType.DOWN.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	D("d", " move right") {
		public MoveCommand execute() {
			return GameCommandType.RIGHT.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	Q("q", " move upleft") {
		public MoveCommand execute() {
			return GameCommandType.LEFTUP.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	E("e", " move upright") {
		public MoveCommand execute() {
			return GameCommandType.RIGHTUP.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	X("x", " move downright") {
		public MoveCommand execute() {
			return GameCommandType.LEFTDOWN.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	Y("y", " move downleft") {
		public MoveCommand execute() {
			return GameCommandType.RIGHTDOWN.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	MASTER_ENERGY("master_energy", " output energy of mastersquirrel") {
		public MoveCommand execute() {
			return MoveCommand.MASTER;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	SPAWN_MINI("spawn_mini", " <param1> <param2> spawns new minisquirrel with energy param1 at postiton param2 [0-7] ",
			int.class, int.class) {

		public MoveCommand executeSpawn(Integer energy, Integer position) {
			MoveCommand returnCommand = MoveCommand.NON;
			if (position == 0) {

				returnCommand = MoveCommand.MINI_UPLEFT;
				returnCommand.setEnergy(energy);
			} else if (position == 1) {

				returnCommand = MoveCommand.MINI_UP;
				returnCommand.setEnergy(energy);
			} else if (position == 2) {

				returnCommand = MoveCommand.MINI_UPRIGHT;
				returnCommand.setEnergy(energy);
			} else if (position == 3) {

				returnCommand = MoveCommand.MINI_RIGHT;
				returnCommand.setEnergy(energy);
			} else if (position == 4) {

				returnCommand = MoveCommand.MINI_DOWNRIGHT;
				returnCommand.setEnergy(energy);
			} else if (position == 5) {

				returnCommand = MoveCommand.MINI_DOWNLEFT;
				returnCommand.setEnergy(energy);
			} else if (position == 6) {

				returnCommand = MoveCommand.MINI_DOWN;
				returnCommand.setEnergy(energy);
			} else if (position == 7) {

				returnCommand = MoveCommand.MINI_LEFT;
				returnCommand.setEnergy(energy);
			} else {
				try {
					throw new WrongParamInputException("No valid spawnLocation!");
				} catch (WrongParamInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return returnCommand;

		}

		@Override
		public MoveCommand execute() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			return executeSpawn((Integer) energy, (Integer) position);
		}
	},
	M("m", " output energy of mastersquirrel") {
		public MoveCommand execute() {
			return GameCommandType.MASTER_ENERGY.execute();
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	MINI("mini", " <param1> <param2> spawns new minisquirrel with energy param1 at postiton param2 [0-7]", int.class,
			int.class) {

		@Override
		public MoveCommand execute() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MoveCommand execute(Object energy, Object position) {
			return GameCommandType.SPAWN_MINI.execute(energy, position);
		}
	};

	private String name;
	private String helpText;
	private Class<?> paramType1;
	private Class<?> paramType2;

	private GameCommandType(String name, String helpText) {
		this.name = name;
		this.helpText = helpText;
	}

	private GameCommandType(String name, String helpText, Class<?> paramType1, Class<?> paramType2) {
		this.name = name;
		this.helpText = helpText;
		this.paramType1 = paramType1;
		this.paramType2 = paramType2;

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHelpText() {
		return helpText;
	}

	@Override
	public Class<?>[] getParamTypes() {
		Class<?>[] output = { paramType1, paramType2 };
		return output;
	}

}
