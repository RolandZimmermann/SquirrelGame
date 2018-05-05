package de.hsa.game.SquirrelGame.ui.console;

import java.io.PrintStream;

import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.consoletest.MyFavoriteCommandType;

public enum GameCommandType implements CommandTypeInfo {
	
	HELP("help", " list all commands"),
	EXIT("exit", " exit programm"),
	ALL("all", " print all entitys"),
	LEFT("left", " move left"),
	LEFTDOWN("leftdown", " move leftdown"),
	LEFTUP("leftup", " move leftup"),
	RIGHTDOWN("rightdown", " move rightdown"),
	RIGHTUP("rightup", " move rightup"),
	RIGHT("right", " move right"),
	DOWN("down", " move down"),
	UP("up", " move up"),
	W("w", " move up"),
	A("a", " move left"),
	S("w", " move down"),
	D("d", " move right"),
	Q("q", " move upleft"),
	E("e", " move upright"),
	X("x", " move downright"),
	Y("y", " move downleft"),
	MASTER_ENERGY("master_energy", " output energy of mastersquirrel"),
	SPAWN_MINI("spawn_mini", " <param1> <param2> spawns new minisquirrel with energy param1 at postiton param2 ", int.class, int.class),
	M("m", " output energy of mastersquirrel"),
	MINI("mini", " <param1> <param2> spawns new minisquirrel with energy param1 at postiton param2 ", int.class, int.class);	
	
	
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
		Class<?>[] output = {paramType1,paramType2};
		return output;
	}

	@Override
	public void help(PrintStream outputStream) {
	for (GameCommandType commandType : GameCommandType.values()) {
			outputStream.println(commandType.getName() + ": " + commandType.getHelpText());
		}
		
	}

	@Override
	public void exit(PrintStream outputStream) {
		System.exit(0);
		
	}

	@Override
	public void all(PrintStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MoveCommand up(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.UP;
	}

	@Override
	public MoveCommand down(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.DOWN;
	}

	@Override
	public MoveCommand upleft(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.UPLEFT;
	}

	@Override
	public MoveCommand upright(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.UPRIGHT;
	}

	@Override
	public MoveCommand downleft(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.DOWNLEFT;
	}

	@Override
	public MoveCommand downright(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.DOWNRIGHT;
	}

	@Override
	public MoveCommand w(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return up(outputStream);
	}

	@Override
	public MoveCommand a(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return left(outputStream);
	}

	@Override
	public MoveCommand s(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return down(outputStream);
	}

	@Override
	public MoveCommand d(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return right(outputStream);
	}

	@Override
	public MoveCommand q(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return upleft(outputStream);
	}

	@Override
	public MoveCommand e(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return upright(outputStream);
	}

	@Override
	public MoveCommand y(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return downleft(outputStream);
	}

	@Override
	public MoveCommand x(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return downright(outputStream);
	}

	@Override
	public MoveCommand master_squirrel(PrintStream outputStream) {
		return MoveCommand.MASTER;
		
	}

	@Override
	public MoveCommand mini_squirrel(PrintStream outputStream, Integer energy, Integer position) {
		if (position == 0) {
			
		}
		return null;
		
	}

	@Override
	public MoveCommand m(PrintStream outputStream) {
		
		return MoveCommand.MASTER;
		
	}

	@Override
	public MoveCommand mini(PrintStream outputStream, Integer energy, Integer position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveCommand right(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.RIGHT;
	}

	@Override
	public MoveCommand left(PrintStream outputStream) {
		// TODO Auto-generated method stub
		return MoveCommand.LEFT;
	}

	
	
	
}
