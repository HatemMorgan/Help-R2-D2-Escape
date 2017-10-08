package Project_Problem;


import main.GenericSearchProblem;
import main.State;
import main.TreeNode;

public class HelpR2D2 extends GenericSearchProblem {

	private Grid intialGrid;
	public Grid getIntialGrid() {
		return intialGrid;
	}

	private int[] teleportalIndexes;
	
	public HelpR2D2() {
		this.setOperators(new String[] { "N", "S", "E", "W" });
		intialGrid = new Grid();
		teleportalIndexes = intialGrid.getTeleportalPos();
		super.setInitialState(new R2D2State(intialGrid.getAgentInitialPos()[0], intialGrid.getAgentInitialPos()[1], intialGrid.getRocks(), intialGrid));
	}

	@Override
	public State transitionFunction(State currState, String operator) throws CloneNotSupportedException {
		R2D2State state = (R2D2State) currState;

		boolean rockMoved = false;
		int remainingRocks = state.getRemainingRocks();
	
		switch (operator) {
		case "N": return updateWhenMovedInNorthDirection(state, operator);
		case "S":  return updateWhenMovedInSouthDirection(state, operator); 
		case "E":	 return updateWhenMovedInEastDirection(state, operator);	
		case "W": return updateWhenMovedInWestDirection(state, operator);		
		default: return state;
		}
	}
	
	private R2D2State updateWhenMovedInNorthDirection(R2D2State state, String operator) throws CloneNotSupportedException{
		GridObjects[][] grid = state.getGridObj().getGrid();

		int minX = 0;        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the upper most row
		// if in a row that there exist an obstacle above agent
		// if in a row that there exist a rock above it(agent) and there is no free space or pressure pad above it(rock)
		if (state.getX() == minX
				|| (state.getX() != minX && grid[state.getX()-1][state.getY()] == GridObjects.Obstacle)
				||(state.getX() == 1 && (grid[state.getX()-1][state.getY()] == GridObjects.Rock || grid[state.getX()-1][state.getY()] == GridObjects.RockPressurePad || grid[state.getX()-1][state.getY()] == GridObjects.RockTeleportal) )
				|| (state.getX() >= 2  &&  
						(grid[state.getX()-1][state.getY()] == GridObjects.Rock || grid[state.getX()-1][state.getY()] == GridObjects.RockTeleportal || grid[state.getX()-1][state.getY()] == GridObjects.RockPressurePad) && 
							 (grid[state.getX()-2][state.getY()] != GridObjects.FreeSpace || grid[state.getX()-2][state.getY()] != GridObjects.PressurePad || grid[state.getX()-2][state.getY()] != GridObjects.Teleportal ))) {
						return state;
		} else {
			
			// clone grid to add upgrade it and add it to new state that will be returned
			Grid newGridObj = (Grid)state.getGridObj().clone();
			
			//======================================================================================
			// / above the agent there exist a rock and above the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getX() >= 2  
					&& (grid[state.getX()-1][state.getY()] == GridObjects.Rock || grid[state.getX()-1][state.getY()] == GridObjects.RockPressurePad || grid[state.getX()-1][state.getY()] == GridObjects.RockTeleportal)){
				
						//======================================================================================
						// checking place of Agent and update it after the agent moved northly and left this place
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent down it in the north direction
						//======================================================================================
						
						switch(grid[state.getX()-1][state.getY()]){
						// rock was on a free space
						case Rock : newGridObj.getGrid()[state.getX()][state.getY() -1] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGridObj.getGrid()[state.getX()-1][state.getY() ] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGridObj.getGrid()[state.getX()-1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
						//======================================================================================
						// checking place above the rock and update it after moving the rock in the north direction
						//======================================================================================
						switch(grid[state.getX()-2][state.getY()]){
						// above the rock there exist a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()-2][state.getY()] = GridObjects.Rock; break;
						//  above the rock there exist a Pressure Pad
						case PressurePad : newGridObj.getGrid()[state.getX()-2][state.getY()] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  above the rock there exist a  Teleprotal
						case Teleportal : newGridObj.getGrid()[state.getX()-2][state.getY()] = GridObjects.RockTeleportal; break;
						
						default : break;
					}
						// return new state
						return new R2D2State(state.getX()-1, state.getY(),state.getRemainingRocks(),newGridObj);
				
				//======================================================================================
				// above the agent their exist a free space or pressure pad or teleportal		 
				//======================================================================================
			}else if (state.getX() >= 1  &&
								(grid[state.getX()-1][state.getY()] == GridObjects.FreeSpace || grid[state.getX()-1][state.getY()] == GridObjects.PressurePad || grid[state.getX()-1][state.getY()] == GridObjects.Teleportal)){
				
						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the north direction
						//======================================================================================			
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of  above the agent and update it after moving the agent in the north direction
						//======================================================================================
						
						switch(grid[state.getX()-1][state.getY()]){
						// place above agent was on a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()-1][state.getY()] = GridObjects.Agent; break;
						// place above agent was on pressure pad
						case PressurePad : newGridObj.getGrid()[state.getX()-1][state.getY()] = GridObjects.AgentPressurePad;  	break;
						// place above agent was on a teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()-1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
					// return new state
					return new R2D2State(state.getX()-1, state.getY(),state.getRemainingRocks(),newGridObj);
			}
		}
		
		return null;
	}
	
	
	private R2D2State updateWhenMovedInSouthDirection(R2D2State state, String operator) throws CloneNotSupportedException{
		GridObjects[][] grid = state.getGridObj().getGrid();

		int maxX = grid.length - 1;
        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the down most row
		// if in a row that there exist an obstacle down the agent
		// if in a row that there exist a rock down it(agent) and there is no free space or pressure pad or teleportal down it(rock)
		if (state.getX() == maxX
				|| (state.getX() != maxX && grid[state.getX()+1][state.getY()] == GridObjects.Obstacle)
				||(state.getX() == maxX-1 && (grid[state.getX()+1][state.getY()] == GridObjects.Rock || grid[state.getX()+1][state.getY()] == GridObjects.RockPressurePad || grid[state.getX()+1][state.getY()] == GridObjects.RockTeleportal) )
				|| (state.getX() <= maxX -2  &&  
						(grid[state.getX()+1][state.getY()] == GridObjects.Rock || grid[state.getX()+1][state.getY()] == GridObjects.RockTeleportal || grid[state.getX()+1][state.getY()] == GridObjects.RockPressurePad) && 
							 (grid[state.getX()+2][state.getY()] != GridObjects.FreeSpace || grid[state.getX()+2][state.getY() ] != GridObjects.PressurePad || grid[state.getX()+2][state.getY()] != GridObjects.Teleportal ))) {
						return state;
		} else {
			
			// clone grid to add upgrade it and add it to new state that will be returned
			Grid newGridObj = (Grid)state.getGridObj().clone();
			
			//======================================================================================
			// / down the agent there exist a rock and down the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getX() <= maxX-2  
					&& (grid[state.getX()+1][state.getY()] == GridObjects.Rock || grid[state.getX()+1][state.getY()] == GridObjects.RockPressurePad || grid[state.getX()+1][state.getY()] == GridObjects.RockTeleportal)){
				
						//======================================================================================
						// checking place of Agent and Upgrade it after the agent moved southly and left this place
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent above it in the south direction
						//======================================================================================
						
						switch(grid[state.getX()+1][state.getY()]){
						// rock was on a free space
						case Rock : newGridObj.getGrid()[state.getX()+1][state.getY()] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGridObj.getGrid()[state.getX()+1][state.getY()] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGridObj.getGrid()[state.getX()+1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
						//======================================================================================
						// checking place down the rock and update it after moving the rock in the south direction
						//======================================================================================
						switch(grid[state.getX()+2][state.getY()]){
						// down the rock there exist a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()+2][state.getY()] = GridObjects.Rock; break;
						//  down the rock there exist a Pressure Pad
						case PressurePad : newGridObj.getGrid()[state.getX()+2][state.getY()] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  down the rock there exist a  Teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()+2][state.getY()] = GridObjects.RockTeleportal; break;
						
						default : break;
					}
						// return new state
						return new R2D2State(state.getX()+1, state.getY(),state.getRemainingRocks(),newGridObj);
				
				//======================================================================================
				// down the agent their exist a free space or pressure pad or teleportal		 
				//======================================================================================
			}else if (state.getX() <= maxX -1  &&
								(grid[state.getX()+1][state.getY()] == GridObjects.FreeSpace || grid[state.getX()+1][state.getY()] == GridObjects.PressurePad || grid[state.getX()+1][state.getY()] == GridObjects.Teleportal)){
				
						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the south direction
						//======================================================================================			
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of  down the agent and update it after moving the agent in the south direction
						//======================================================================================
						
						switch(grid[state.getX()+1][state.getY()]){
						// place down agent was on a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()+1][state.getY()] = GridObjects.Agent; break;
						// place down agent was on pressure pad
						case PressurePad : newGridObj.getGrid()[state.getX()+1][state.getY()] = GridObjects.AgentPressurePad;  	break;
						// place down agent was on a teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()+1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
					// return new state
					return new R2D2State(state.getX()+1, state.getY(),state.getRemainingRocks(),newGridObj);
			}
		}
		return null;
	}
	
	
	private R2D2State updateWhenMovedInEastDirection(R2D2State state, String operator) throws CloneNotSupportedException{
		GridObjects[][] grid = state.getGridObj().getGrid();

		int maxY = grid[0].length - 1;
        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the right most row
		// if in a row that there exist an obstacle right to the agent
		// if in a row that there exist a rock right to it(agent) and there is no free space or pressure pad or teleportal right to rock
		if (state.getY() == maxY
				|| (state.getY() != maxY && grid[state.getX()][state.getY()+1] == GridObjects.Obstacle)
				||(state.getY() == maxY-1 && (grid[state.getX()][state.getY()+1] == GridObjects.Rock || grid[state.getX()][state.getY()+1] == GridObjects.RockPressurePad || grid[state.getX()][state.getY()+1] == GridObjects.RockTeleportal) )
				|| (state.getY() <= maxY -2  &&  
						(grid[state.getX()][state.getY()+1] == GridObjects.Rock || grid[state.getX()][state.getY()+1] == GridObjects.RockTeleportal || grid[state.getX()][state.getY()+1] == GridObjects.RockPressurePad) && 
							 (grid[state.getX()][state.getY()+2] != GridObjects.FreeSpace || grid[state.getX()][state.getY()+2] != GridObjects.PressurePad || grid[state.getX()][state.getY()+2] != GridObjects.Teleportal ))) {

						return state;
		} else {
			
			// clone grid to add upgrade it and add it to new state that will be returned
			Grid newGridObj = (Grid)state.getGridObj().clone();
			
			//======================================================================================
			// / right the agent there exist a rock and right to the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getY() <= maxY-2  
					&& (grid[state.getX()][state.getY()+1] == GridObjects.Rock || grid[state.getX()][state.getY()+1] == GridObjects.RockPressurePad || grid[state.getX()][state.getY()+1] == GridObjects.RockTeleportal)){

						//======================================================================================
						// checking place of Agent and Upgrade it after the agent moved in the east direction 
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent above it in the east direction
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()+1]){
						// rock was on a free space
						case Rock : newGridObj.getGrid()[state.getX()][state.getY()+1] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGridObj.getGrid()[state.getX()][state.getY()+1] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGridObj.getGrid()[state.getX()][state.getY()+1] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
						//======================================================================================
						// checking place right the rock and update it after moving the rock in the east direction
						//======================================================================================
						switch(grid[state.getX()][state.getY()+2]){
						// right the rock there exist a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()][state.getY()+2] = GridObjects.Rock; break;
						//  right the rock there exist a Pressure Pad
						case PressurePad : newGridObj.getGrid()[state.getX()][state.getY()+2] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  right the rock there exist a  Teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()][state.getY()+2] = GridObjects.RockTeleportal; break;
						
						default : break;
					}
						// return new state
						return new R2D2State(state.getX(), state.getY()+1,state.getRemainingRocks(),newGridObj);
				
				//======================================================================================
				// right the agent their exist a free space or pressure pad or teleportal		 
				//======================================================================================
			}else if (state.getY() <= maxY -1  &&
								(grid[state.getX()][state.getY()+1] == GridObjects.FreeSpace || grid[state.getX()][state.getY()+1] == GridObjects.PressurePad || grid[state.getX()][state.getY()+1] == GridObjects.Teleportal)){

						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the east direction
						//======================================================================================			
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of  right the agent and update it after moving the agent in the east direction
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()]){
						// place right to agent was on a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()][state.getY()+1] = GridObjects.Agent; break;
						// place right to agent was on pressure pad
						case PressurePad : newGridObj.getGrid()[state.getX()][state.getY()+1] = GridObjects.AgentPressurePad;  	break;
						// place right to agent was on a teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()][state.getY()+1] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
					// return new state
					return new R2D2State(state.getX(), state.getY()+1,state.getRemainingRocks(),newGridObj);
			}
		}
		return null;
	}
	
	
	private R2D2State updateWhenMovedInWestDirection(R2D2State state, String operator) throws CloneNotSupportedException{
		GridObjects[][] grid = state.getGridObj().getGrid();

		int minY = 0;
        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the left most row
		// if in a row that there exist an obstacle left to the agent
		// if in a row that there exist a rock left to it(agent) and there is no free space or pressure pad or teleprotal left to rock
		if (state.getY() == minY
				|| (state.getY() != minY && grid[state.getX()][state.getY()-1] == GridObjects.Obstacle)
				||(state.getY() == 1 && (grid[state.getX()][state.getY()-1] == GridObjects.Rock || grid[state.getX()][state.getY()-1] == GridObjects.RockPressurePad || grid[state.getX()][state.getY()-1] == GridObjects.RockTeleportal) )
				|| (state.getY() >= 2 &&  
						(grid[state.getX()][state.getY()-1] == GridObjects.Rock || grid[state.getX()][state.getY()-1] == GridObjects.RockTeleportal || grid[state.getX()][state.getY()-1] == GridObjects.RockPressurePad) && 
							 (grid[state.getX()][state.getY()-2] != GridObjects.FreeSpace || grid[state.getX()][state.getY()-2] != GridObjects.PressurePad || grid[state.getX()][state.getY()-2] != GridObjects.Teleportal ))) {

						return state;
		} else {
			
			// clone grid to add upgrade it and add it to new state that will be returned
			Grid newGridObj = (Grid)state.getGridObj().clone();
			
			//======================================================================================
			// / left the agent there exist a rock and right to the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getY() >= 2 
					&& (grid[state.getX()][state.getY()-1] == GridObjects.Rock || grid[state.getX()][state.getY()-1] == GridObjects.RockPressurePad || grid[state.getX()][state.getY()-1] == GridObjects.RockTeleportal)){

						//======================================================================================
						// checking place of Agent and Upgrade it after the agent moved in the west direction 
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent above it in the west direction
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()-1]){
						// rock was on a free space
						case Rock : newGridObj.getGrid()[state.getX()][state.getY()-1] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGridObj.getGrid()[state.getX()][state.getY()-1] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGridObj.getGrid()[state.getX()][state.getY()-1] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
						//======================================================================================
						// checking place left the rock and update it after moving the rock in the west direction
						//======================================================================================
						switch(grid[state.getX()][state.getY()-2]){
						// left the rock there exist a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()][state.getY()-2] = GridObjects.Rock; break;
						//  left the rock there exist a Pressure Pad
						case PressurePad : newGridObj.getGrid()[state.getX()][state.getY()-2] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  left the rock there exist a  Teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()][state.getY()-2] = GridObjects.RockTeleportal; break;
						
						default : break;
					}
						// return new state
						return new R2D2State(state.getX(), state.getY()-2,state.getRemainingRocks(),newGridObj);
				
					//======================================================================================
					// left the agent their exist a free space or pressure pad or teleportal		 
					//======================================================================================
				}else if (state.getY() >= 1  &&
								(grid[state.getX()][state.getY()-1] == GridObjects.FreeSpace || grid[state.getX()][state.getY()-1] == GridObjects.PressurePad || grid[state.getX()][state.getY()-1] == GridObjects.Teleportal)){
						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the west direction
						//======================================================================================			
						switch(grid[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGridObj.getGrid()[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : break;
						}
						
						//======================================================================================
						// checking place of  right the agent and update it after moving the agent in the west direction
						//======================================================================================
						
						switch(grid[state.getX()][state.getY()-1]){
						// place left to agent was on a free space
						case FreeSpace : newGridObj.getGrid()[state.getX()][state.getY()-1] = GridObjects.Agent; break;
						// place left to agent was on pressure pad
						case PressurePad : newGridObj.getGrid()[state.getX()][state.getY()-1] = GridObjects.AgentPressurePad;  	break;
						// place left to agent was on a teleportal
						case Teleportal : newGridObj.getGrid()[state.getX()][state.getY()-1] = GridObjects.AgentTeleprotal; break;
						
						default : break;
					}
						
					// return new state
					return new R2D2State(state.getX(), state.getY()-1,state.getRemainingRocks(),newGridObj);
			}
		}
		return null;
	}


	@Override
	public boolean goalTest(State s) {
		R2D2State state = (R2D2State) s;
		if(state.getRemainingRocks() == 0 && state.getX() == teleportalIndexes[0] && state.getY() == teleportalIndexes[1])
			return true;
		return false;
	}



	@Override
	public TreeNode[] expand(TreeNode node) throws CloneNotSupportedException {
		TreeNode[] expandedNodes = new TreeNode[4];
		int index = 0;
		// loop on the operators
		// get new state from transition function
		// create a new Tree node
		for(String operator : this.getOperators()){
			State newState = this.transitionFunction(node.getState(),operator);
			if(newState == null)
				System.out.println(operator);
			// cost and depth are incremented by 1
			TreeNode newNode = new TreeNode(node, newState, node.getDepth()+1, node.getCost()+1, operator);
			expandedNodes[index] =  newNode;
			index++;
		}
		return expandedNodes;
	}

	@Override
	public int pathCost() {
		// TODO Auto-generated method stub
		return 0;
	}



	
}
