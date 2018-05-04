package de.hsa.game.SquirrelGame.ui.consoletest;

import java.io.PrintStream;

public enum MyFavoriteCommandType implements CommandTypeInfo {

	HELP("help", "  * list all commands"), EXIT("exit", "  * exit program"), ADDI("addi",
			"<param1>  <param2>   * simple integer add ", int.class,
			int.class), ADDF("addf", "<param1>  <param2>   * simple float add ", float.class, float.class), ECHO("echo",
					"<param1>  <param2>   * echos param1 string param2 times ", String.class, int.class);

	private String name;
	private String helpText;
	private Class<?> paramType1;
	private Class<?> paramType2;

	private MyFavoriteCommandType(String name, String helpText) {
		this.name = name;
		this.helpText = helpText;
	}

	private MyFavoriteCommandType(String name, String helpText, Class<?> paramType1, Class<?> paramType2) {
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
	
	public void help(PrintStream outputStream) {
		for (MyFavoriteCommandType commandType : MyFavoriteCommandType.values()) {
			outputStream.println(commandType.getName() + ": " + commandType.getHelpText());
		}
	}
	
	public void exit(PrintStream outputStream) {
		System.exit(0);
	}
	public void addf(PrintStream outputStream, Float a, Float b) {
		outputStream.println(a+b);
	}
	public void addi(PrintStream outputStream, Integer a, Integer b) {
		outputStream.println(a+b);
	}
	public void echo(PrintStream outputStream, String echo, Integer a) {
		for (int i = 0; i < a; i++) {
			outputStream.println(echo);
		}
	}

}
