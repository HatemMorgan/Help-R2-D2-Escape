package Project_Problem;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.GenericSearchProblem;
import main.State;
import main.TreeNode;

public class HelpR2D2 extends GenericSearchProblem {

//	private Grid intialGrid;
	private GridObjects[][] grid;
	private int[] agentInitialPos;
	//private int[] teleportalPos;
    private int RocksNum;
	private int[] teleportalIndexes;
	
//	private TreeNode[][] stateSpaceLookup;
	
	public HelpR2D2() {
		this.setOperators(new String[] { "N", "S", "E", "W" });
		GenGrid();
		super.setInitialState(new R2D2State(agentInitialPos[0], agentInitialPos[1], RocksNum, grid,teleportalIndexes));
	}
	
	public HelpR2D2(GridObjects[][] grid, int[] initialPos, int[] telePortal, int numRocks){
		this.setOperators(new String[] { "S", "E", "W" , "N"});
		this.grid = grid;
		this.agentInitialPos = initialPos;
		this.teleportalIndexes = telePortal;
		this.RocksNum = numRocks;
		super.setInitialState(new R2D2State(agentInitialPos[0], agentInitialPos[1], RocksNum, grid,teleportalIndexes));
		
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

		int minX = 0;        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the upper most row
		// if in a row that there exist an obstacle above agent
		// if in a row that there exist a rock above it(agent) and there is no free space or pressure pad above it(rock)
		if (state.getX() == minX
				|| (state.getX() != minX && state.getGrid()[state.getX()-1][state.getY()] == GridObjects.Obstacle)
				||(state.getX() == 1 && (state.getGrid()[state.getX()-1][state.getY()] == GridObjects.Rock || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.RockPressurePad || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.RockTeleportal) )
				|| (state.getX() >= 2  &&  
						(state.getGrid()[state.getX()-1][state.getY()] == GridObjects.Rock || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.RockTeleportal || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.RockPressurePad) && 
							 (state.getGrid()[state.getX()-2][state.getY()] != GridObjects.FreeSpace && state.getGrid()[state.getX()-2][state.getY()] != GridObjects.PressurePad && state.getGrid()[state.getX()-2][state.getY()] != GridObjects.Teleportal ))) {
						return state;
		} else {
			
			// clone state.getGrid() to add upgrade it and add it to new state that will be returned
			GridObjects[][] newGrid = clone(state.getGrid());
			
			//======================================================================================
			// / above the agent there exist a rock and above the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getX() >= 2  
					&& (state.getGrid()[state.getX()-1][state.getY()] == GridObjects.Rock || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.RockPressurePad || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.RockTeleportal)){
				
					
						//======================================================================================
						// checking place of Agent and update it after the agent moved northly and left this place
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal :newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default: System.out.println("North first switch error"); break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent down it in the north direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()-1][state.getY()]){
						// rock was on a free space
						case Rock : newGrid[state.getX() - 1][state.getY()] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : 	newGrid[state.getX()-1][state.getY() ] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGrid[state.getX()-1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : System.out.println("North second switch error");  break;
					}
						
						//======================================================================================
						// checking place above the rock and update it after moving the rock in the north direction
						//======================================================================================
						switch(state.getGrid()[state.getX()-2][state.getY()]){
						// above the rock there exist a free space
						case FreeSpace : newGrid[state.getX()-2][state.getY()] = GridObjects.Rock; break;
						//  above the rock there exist a Pressure Pad
						case PressurePad : newGrid[state.getX()-2][state.getY()] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  above the rock there exist a  Teleprotal
						case Teleportal : newGrid[state.getX()-2][state.getY()] = GridObjects.RockTeleportal; break;
						
						default : System.out.println("North third switch error");break;
					}
						// return new state
						return new R2D2State(state.getX()-1, state.getY(), remainingRocks,newGrid,state.getTeleportalPos());
				
				//======================================================================================
				// above the agent their exist a free space or pressure pad or teleportal		 
				//======================================================================================
			}else if (state.getX() >= 1  &&
								(state.getGrid()[state.getX()-1][state.getY()] == GridObjects.FreeSpace || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.PressurePad || state.getGrid()[state.getX()-1][state.getY()] == GridObjects.Teleportal)){
				
						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the north direction
						//======================================================================================			
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : System.out.println("North fourth switch error"); break;
						}
						
						//======================================================================================
						// checking place of  above the agent and update it after moving the agent in the north direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()-1][state.getY()]){
						// place above agent was on a free space
						case FreeSpace : newGrid[state.getX()-1][state.getY()] = GridObjects.Agent; break;
						// place above agent was on pressure pad
						case PressurePad : newGrid[state.getX()-1][state.getY()] = GridObjects.AgentPressurePad;  	break;
						// place above agent was on a teleportal
						case Teleportal : newGrid[state.getX()-1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default :System.out.println("North fifth switch error"); break;
					}
						
					// return new state
					return new R2D2State(state.getX()-1, state.getY(),remainingRocks,newGrid,state.getTeleportalPos());
			}
		}
		return null;
	}
	
	
	private R2D2State updateWhenMovedInSouthDirection(R2D2State state, String operator) throws CloneNotSupportedException{

		int maxX = state.getGrid().length - 1;
        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the down most row
		// if in a row that there exist an obstacle down the agent
		// if in a row that there exist a rock down it(agent) and there is no free space or pressure pad or teleportal down it(rock)
		if (state.getX() == maxX
				|| (state.getX() != maxX && state.getGrid()[state.getX()+1][state.getY()] == GridObjects.Obstacle)
				||(state.getX() == maxX-1 && (state.getGrid()[state.getX()+1][state.getY()] == GridObjects.Rock || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.RockPressurePad || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.RockTeleportal) )
				|| (state.getX() <= maxX -2  &&  
						(state.getGrid()[state.getX()+1][state.getY()] == GridObjects.Rock || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.RockTeleportal || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.RockPressurePad) && 
							 (state.getGrid()[state.getX()+2][state.getY()] != GridObjects.FreeSpace && state.getGrid()[state.getX()+2][state.getY() ] != GridObjects.PressurePad && state.getGrid()[state.getX()+2][state.getY()] != GridObjects.Teleportal ))) {
						return state;
		} else {
			
			// clone state.getGrid() to add upgrade it and add it to new state that will be returned
			GridObjects[][] newGrid = clone(state.getGrid());
			
			//======================================================================================
			// / down the agent there exist a rock and down the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getX() <= maxX-2  
					&& (state.getGrid()[state.getX()+1][state.getY()] == GridObjects.Rock || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.RockPressurePad || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.RockTeleportal)){
				

						//======================================================================================
						// checking place of Agent and Upgrade it after the agent moved southly and left this place
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : printGrid(state.getGrid()); System.out.println(state.getX()+" "+state.getY()); System.out.println("South first switch error " + state.getGrid()[state.getX()][state.getY()]);  break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent above it in the south direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()+1][state.getY()]){
						// rock was on a free space
						case Rock : newGrid[state.getX()+1][state.getY()] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGrid[state.getX()+1][state.getY()] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGrid[state.getX()+1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : System.out.println("South second switch error"); break;
					}
						
						//======================================================================================
						// checking place down the rock and update it after moving the rock in the south direction
						//======================================================================================
						switch(state.getGrid()[state.getX()+2][state.getY()]){
						// down the rock there exist a free space
						case FreeSpace : newGrid[state.getX()+2][state.getY()] = GridObjects.Rock; break;
						//  down the rock there exist a Pressure Pad
						case PressurePad : newGrid[state.getX()+2][state.getY()] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  down the rock there exist a  Teleportal
						case Teleportal : newGrid[state.getX()+2][state.getY()] = GridObjects.RockTeleportal; break;
						
						default : System.out.println("South third switch error"); break;
					}
						// return new state
						return new R2D2State(state.getX()+1, state.getY(),remainingRocks,newGrid,state.getTeleportalPos());
				
				//======================================================================================
				// down the agent their exist a free space or pressure pad or teleportal		 
				//======================================================================================
			}else if (state.getX() <= maxX -1  &&
								(state.getGrid()[state.getX()+1][state.getY()] == GridObjects.FreeSpace || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.PressurePad || state.getGrid()[state.getX()+1][state.getY()] == GridObjects.Teleportal)){
//					System.out.println("heree");
						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the south direction
						//======================================================================================			
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : System.out.println("South fourth switch error"); break;
						}
						
						//======================================================================================
						// checking place of  down the agent and update it after moving the agent in the south direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()+1][state.getY()]){
						// place down agent was on a free space
						case FreeSpace : newGrid[state.getX()+1][state.getY()] = GridObjects.Agent; break;
						// place down agent was on pressure pad
						case PressurePad : newGrid[state.getX()+1][state.getY()] = GridObjects.AgentPressurePad;  	break;
						// place down agent was on a teleportal
						case Teleportal : newGrid[state.getX()+1][state.getY()] = GridObjects.AgentTeleprotal; break;
						
						default : System.out.println("South fifth switch error"); break;
					}
						
					// return new state
					return new R2D2State(state.getX()+1, state.getY(),remainingRocks,newGrid,state.getTeleportalPos());
			}
		}
		return null;
	}
	
	
	private R2D2State updateWhenMovedInEastDirection(R2D2State state, String operator) throws CloneNotSupportedException{

		int maxY = state.getGrid()[0].length - 1;
        
		int remainingRocks = state.getRemainingRocks();
		
		
		// if in the right most row
		// if in a row that there exist an obstacle right to the agent
		// if in a row that there exist a rock right to it(agent) and there is no free space or pressure pad or teleportal right to rock
		if (state.getY() == maxY
				|| (state.getY() != maxY && state.getGrid()[state.getX()][state.getY()+1] == GridObjects.Obstacle)
				||(state.getY() == maxY-1 && (state.getGrid()[state.getX()][state.getY()+1] == GridObjects.Rock || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.RockPressurePad || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.RockTeleportal) )
				|| (state.getY() <= maxY -2  &&  
						(state.getGrid()[state.getX()][state.getY()+1] == GridObjects.Rock || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.RockTeleportal || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.RockPressurePad) && 
							 (state.getGrid()[state.getX()][state.getY()+2] != GridObjects.FreeSpace && state.getGrid()[state.getX()][state.getY()+2] != GridObjects.PressurePad && state.getGrid()[state.getX()][state.getY()+2] != GridObjects.Teleportal ))) {

						return state;
		} else {
			
			// clone state.getGrid() to add upgrade it and add it to new state that will be returned
			GridObjects[][] newGrid = clone(state.getGrid());
			
			//======================================================================================
			// / right the agent there exist a rock and right to the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getY() <= maxY-2  
					&& (state.getGrid()[state.getX()][state.getY()+1] == GridObjects.Rock || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.RockPressurePad || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.RockTeleportal)){

						//======================================================================================
						// checking place of Agent and Upgrade it after the agent moved in the east direction 
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : System.out.println("East first switch error");  break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent above it in the east direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()+1]){
						// rock was on a free space
						case Rock : newGrid[state.getX()][state.getY()+1] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGrid[state.getX()][state.getY()+1] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGrid[state.getX()][state.getY()+1] = GridObjects.AgentTeleprotal; break;
						
						default : System.out.println("East Second switch error"); break;
					}
						
						//======================================================================================
						// checking place right the rock and update it after moving the rock in the east direction
						//======================================================================================
						switch(state.getGrid()[state.getX()][state.getY()+2]){
						// right the rock there exist a free space
						case FreeSpace : newGrid[state.getX()][state.getY()+2] = GridObjects.Rock; break;
						//  right the rock there exist a Pressure Pad
						case PressurePad : newGrid[state.getX()][state.getY()+2] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  right the rock there exist a  Teleportal
						case Teleportal : newGrid[state.getX()][state.getY()+2] = GridObjects.RockTeleportal; break;
						
						default : System.out.println("East Third switch error"); break;
					}
						// return new state
						return new R2D2State(state.getX(), state.getY()+1,remainingRocks,newGrid,state.getTeleportalPos());
				
				//======================================================================================
				// right the agent their exist a free space or pressure pad or teleportal		 
				//======================================================================================
			}else if (state.getY() <= maxY -1  &&
								(state.getGrid()[state.getX()][state.getY()+1] == GridObjects.FreeSpace || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.PressurePad || state.getGrid()[state.getX()][state.getY()+1] == GridObjects.Teleportal)){

						//======================================================================================
						// checking place of Agent and update its current place after moving the agent in the east direction
						//======================================================================================			
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : System.out.println("East Fourth switch error");  break;
						}
						
						//======================================================================================
						// checking place of  right the agent and update it after moving the agent in the east direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()+1]){
						// place right to agent was on a free space
						case FreeSpace : newGrid[state.getX()][state.getY()+1] = GridObjects.Agent; break;
						// place right to agent was on pressure pad
						case PressurePad : newGrid[state.getX()][state.getY()+1] = GridObjects.AgentPressurePad;  	break;
						// place right to agent was on a teleportal
						case Teleportal : newGrid[state.getX()][state.getY()+1] = GridObjects.AgentTeleprotal; break;
						
						default :System.out.println("East Fifth switch error " + state.getGrid()[state.getX()][state.getY()] ); break;
					}
						
					// return new state
					return new R2D2State(state.getX(), state.getY()+1,remainingRocks,newGrid,state.getTeleportalPos());
			}
		}
		return null;
	}
	
	
	private R2D2State updateWhenMovedInWestDirection(R2D2State state, String operator) throws CloneNotSupportedException{

		int minY = 0;
        
		int remainingRocks = state.getRemainingRocks();
//		System.out.println(state);
		// if in the left most row
		// if in a row that there exist an obstacle left to the agent
		// if in a row that there exist a rock left to it(agent) and there is no free space or pressure pad or teleprotal left to rock
		if (state.getY() == minY
				|| (state.getY() != minY && state.getGrid()[state.getX()][state.getY()-1] == GridObjects.Obstacle)
				||(state.getY() == 1 && (state.getGrid()[state.getX()][state.getY()-1] == GridObjects.Rock || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.RockPressurePad || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.RockTeleportal) )
				|| (state.getY() >= 2 &&  
						(state.getGrid()[state.getX()][state.getY()-1] == GridObjects.Rock || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.RockTeleportal || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.RockPressurePad) && 
							 (state.getGrid()[state.getX()][state.getY()-2] != GridObjects.FreeSpace && state.getGrid()[state.getX()][state.getY()-2] != GridObjects.PressurePad && state.getGrid()[state.getX()][state.getY()-2] != GridObjects.Teleportal ))) {

						return state;
		} else {
			
			// clone state.getGrid() to add upgrade it and add it to new state that will be returned
			GridObjects[][] newGrid = clone(state.getGrid());
			
			//======================================================================================
			// / left the agent there exist a rock and right to the rock their exist free space or pressure pad or teleportal	 
			//======================================================================================
			if(state.getY() >= 2 
					&& (state.getGrid()[state.getX()][state.getY()-1] == GridObjects.Rock || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.RockPressurePad || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.RockTeleportal)){

						//======================================================================================
						// checking place of Agent and Upgrade it after the agent moved in the west direction 
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : System.out.println("West First switch error"); break;
						}
						
						//======================================================================================
						// checking place of rock and update it by moving the agent above it in the west direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()-1]){
						// rock was on a free space
						case Rock : newGrid[state.getX()][state.getY()-1] = GridObjects.Agent; break;
						// rock was on pressure pad
						case RockPressurePad : newGrid[state.getX()][state.getY()-1] = GridObjects.AgentPressurePad; remainingRocks++; 	break;
						// rock was on a teleportal
						case RockTeleportal : newGrid[state.getX()][state.getY()-1] = GridObjects.AgentTeleprotal; break;
						
						default : System.out.println("West Second switch error"); break;
					}
						
						//======================================================================================
						// checking place left the rock and update it after moving the rock in the west direction
						//======================================================================================
						switch(state.getGrid()[state.getX()][state.getY()-2]){
						// left the rock there exist a free space
						case FreeSpace : newGrid[state.getX()][state.getY()-2] = GridObjects.Rock; break;
						//  left the rock there exist a Pressure Pad
						case PressurePad : newGrid[state.getX()][state.getY()-2] = GridObjects.RockPressurePad; remainingRocks--;	break;
						//  left the rock there exist a  Teleportal
						case Teleportal : newGrid[state.getX()][state.getY()-2] = GridObjects.RockTeleportal; break;
						
						default : System.out.println("West Third switch error"); break;
					}
						// return new state
						return new R2D2State(state.getX(), state.getY()-1,remainingRocks,newGrid,state.getTeleportalPos());
				
					//======================================================================================
					// left the agent their exist a free space or pressure pad or teleportal		 
					//======================================================================================
				}else if (state.getY() >= 1  &&
								(state.getGrid()[state.getX()][state.getY()-1] == GridObjects.FreeSpace || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.PressurePad || state.getGrid()[state.getX()][state.getY()-1] == GridObjects.Teleportal)){

					    //======================================================================================
						// checking place of Agent and update its current place after moving the agent in the west direction
						//======================================================================================			
						switch(state.getGrid()[state.getX()][state.getY()]){
							// agent was on a free space
							case Agent : newGrid[state.getX()][state.getY()] = GridObjects.FreeSpace; break;
							// agent was on teleportal
							case AgentTeleprotal : newGrid[state.getX()][state.getY()] = GridObjects.Teleportal;	break;
							// agent was on a pressure pad
							case AgentPressurePad : newGrid[state.getX()][state.getY()] = GridObjects.PressurePad; break;
							
							default : System.out.println("West Fourth switch error"); break;
						}
						
						//======================================================================================
						// checking place of  right the agent and update it after moving the agent in the west direction
						//======================================================================================
						
						switch(state.getGrid()[state.getX()][state.getY()-1]){
						// place left to agent was on a free space
						case FreeSpace : newGrid[state.getX()][state.getY()-1] = GridObjects.Agent; break;
						// place left to agent was on pressure pad
						case PressurePad : newGrid[state.getX()][state.getY()-1] = GridObjects.AgentPressurePad;  	break;
						// place left to agent was on a teleportal
						case Teleportal : newGrid[state.getX()][state.getY()-1] = GridObjects.AgentTeleprotal; break;
						
						default : System.out.println("West Fifth switch error"); break;
					}
						
					// return new state
					return new R2D2State(state.getX(), state.getY()-1,remainingRocks,newGrid,state.getTeleportalPos());
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
			R2D2State newState = (R2D2State)this.transitionFunction(node.getState(),operator);

			// skip if newState is the same as current state if node(parameter)
			if(newState.getX()== ((R2D2State)node.getState()).getX() && newState.getY()== ((R2D2State)node.getState()).getY())
				continue;   //repeated states
			
			// create a new Tree node
			// cost and depth are incremented by 1
			 TreeNode  newNode = new TreeNode(node, newState, node.getDepth()+1, node.getCost()+1, operator);
	
			expandedNodes[index] =  newNode;
			index++;
		}
		return expandedNodes;
	}

	public void printGoalPath(TreeNode goal) throws InterruptedException, CloneNotSupportedException{
		// get list of actions from initial state to goal
		List<String> actions = new ArrayList<String>();
		printGoalPathHelp(goal, actions);
		System.out.println(actions);

		R2D2State state = new R2D2State(agentInitialPos[0], agentInitialPos[1], RocksNum, grid, teleportalIndexes);
		printGrid(grid);
		Thread.sleep(500);
		System.out.println();
		
		// print grid for each action
		for(int i=actions.size()-1; i >= 0; i--){
			state = (R2D2State) transitionFunction(state, actions.get(i));
			printGrid(state.getGrid());
			Thread.sleep(500);
			System.out.println();
		}
		
	}
	
	private void printGoalPathHelp(TreeNode node, List<String> actions) {
		// if its the root then stop
		if(node.getParent() == null){
			return;
		}else{
			actions.add(node.getOperator());
			printGoalPathHelp(node.getParent(), actions);
		}
}
	
	@Override
	public int pathCost() {
		// TODO Auto-generated method stub
		return 0;
	}


	private void GenGrid() {

		int m = RandomNumber(3, 14);
		int n = RandomNumber(3, 14);
		grid = new GridObjects[n][m];
//		stateSpaceLookup = new TreeNode[n][m];
		int totalCells = (m * n) - 2;
		int Teleportal = 1; // check if correct
		int FreeSpaces = RandomNumber(1, totalCells - 3 + 1);
		totalCells -= FreeSpaces;
		int Pressure = RandomNumber(1, (totalCells - 1) / 2 + 1);
	    int Rocks = Pressure;
	    RocksNum = Rocks;
		totalCells -= Pressure * 2;
		int Obstacles = RandomNumber(1, totalCells + 1);
		FreeSpaces += totalCells - Obstacles;
		int Agent = 1;

		ArrayList<GridObjects> gridObjects = new ArrayList<GridObjects>();
		gridObjects.add(GridObjects.PressurePad);
		gridObjects.add(GridObjects.FreeSpace);
		gridObjects.add(GridObjects.Teleportal);
		gridObjects.add(GridObjects.Agent);
		gridObjects.add(GridObjects.Rock);
		gridObjects.add(GridObjects.Obstacle);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int Object = RandomNumber(0, gridObjects.size());
				grid[i][j] = gridObjects.get(Object);
				switch (gridObjects.get(Object)) {
				case PressurePad:
					Pressure--;
					if (Pressure == 0)
						gridObjects.remove(GridObjects.PressurePad);
					break;
				case FreeSpace:
					FreeSpaces--;
					if (FreeSpaces == 0)
						gridObjects.remove(GridObjects.FreeSpace);
					break;
				case Rock:
					Rocks--;
					if (Rocks == 0)
						gridObjects.remove(GridObjects.Rock);
					break;
				case Obstacle:
					Obstacles--;
					if (Obstacles == 0)
						gridObjects.remove(GridObjects.Obstacle);
					break;
				case Agent:
					Agent--;
					if (Agent == 0) {
						gridObjects.remove(GridObjects.Agent);
						agentInitialPos = new int[] { i, j };
					}
					break;
				case Teleportal:
					Teleportal--;
					if (Teleportal == 0) {
						gridObjects.remove(GridObjects.Teleportal);
						teleportalIndexes = new int[] { i, j };
					}
					break;
				}

			}
		}
	}
	
	private int RandomNumber(int low, int high) {
		Random r = new Random();
		return (r.nextInt(high - low) + low);
	}

	public void printGrid(GridObjects[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j].getAlias() + " | ");
			}
			System.out.println();

			for (int j = 0; j < grid[i].length; j++) {
				System.out.print("----");
			}
			System.out.println();

		}
	}
	

	private static GridObjects[][]  clone(GridObjects[][] grid) throws CloneNotSupportedException {
		GridObjects[][] newGrid = new GridObjects[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			newGrid[i] = (GridObjects[]) grid[i].clone();
		}
		return newGrid;
	}

	public GridObjects[][] getGrid() {
		return grid;
	}
	
	
	
}
