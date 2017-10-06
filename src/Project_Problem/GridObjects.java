package Project_Problem;

public enum GridObjects {

	Obstacle("O"),
	Rock("R"),
	Agent("A"),
	Teleportal("T"),
	PressurePad("P"),
	FreeSpace("F"),
	RockPressurePad("PR"),
	RockTeleportal("RT"),
	AgentTeleprotal("AT"),
	AgentPressurePad("AP");
	
	String alias;
	private GridObjects(String alias){
		this.alias = alias;
	}
	public String getAlias() {
		return alias;
	}
	
	
}
