package de.hsa.game.SquirrelGame.ui.console;


public interface CommandTypeInfo {


	public String getName ();
	public String getHelpText();
	public Class<?> [] getParamTypes();
	
//	public void help(PrintStream outputStream);
//	public void exit(PrintStream outputStream);
//	//public void all(PrintStream outputStream);
//	public MoveCommand up(PrintStream outputStream);
//	public MoveCommand down(PrintStream outputStream);
//	public MoveCommand upleft(PrintStream outputStream);
//	public MoveCommand upright(PrintStream outputStream);
//	public MoveCommand downleft(PrintStream outputStream);
//	public MoveCommand downright(PrintStream outputStream);
//	public MoveCommand right(PrintStream outputStream);
//	public MoveCommand left(PrintStream outputStream);
//	public MoveCommand w(PrintStream outputStream);
//	public MoveCommand a(PrintStream outputStream);
//	public MoveCommand s(PrintStream outputStream);
//	public MoveCommand d(PrintStream outputStream);
//	public MoveCommand q(PrintStream outputStream);
//	public MoveCommand e(PrintStream outputStream);
//	public MoveCommand y(PrintStream outputStream);
//	public MoveCommand x(PrintStream outputStream);
//	public MoveCommand master_squirrel(PrintStream outputStream);
//	public MoveCommand mini_squirrel(PrintStream outputStream, Integer energy, Integer position);
//	public MoveCommand m(PrintStream outputStream);
//	public MoveCommand mini(PrintStream outputStream, Integer energy, Integer position);
	
	
}

