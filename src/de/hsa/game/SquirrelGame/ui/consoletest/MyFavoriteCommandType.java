package de.hsa.game.SquirrelGame.ui.consoletest;

public enum MyFavoriteCommandType implements CommandTypeInfo {
	
	HELP("help", "  * list all commands"),
	 EXIT("exit", "  * exit program"),
	 ADDI("addi", "<param1>  <param2>   * simple integer add ",int.class, int.class),
	 ADDF("addf", "<param1>  <param2>   * simple float add ",float.class, float.class ),
	 ECHO("echo", "<param1>  <param2>   * echos param1 string param2 times ",String.class, int.class );
	

	
	private MyFavoriteCommandType(String name, String helpText) {
		
	}
	private MyFavoriteCommandType(String name, String helpText, Class<?>paramType1, Class<?>paramType2) {
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelpText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> [] getParamTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
