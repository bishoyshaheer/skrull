/**
 * 
 * @author kyle
 *
 */

package skrull.game.model.rockpaperscissors;

import skrull.SkrullGameException;
import skrull.game.factory.IGameFactory.GameType;
import skrull.game.model.AbstractGameModel;
import skrull.game.model.IPlayer;
import skrull.game.view.IClientAction;
import skrull.rmi.server.IClientUpdater;


public class RockPaperScissors extends AbstractGameModel{


	private static final int PAPER = 1;
	private static final int SCISSORS = 2;
	private static final int ROCK = 0;
	private static final long serialVersionUID = 1870980829045671398L;
	private boolean gameStop;
	
	public RockPaperScissors(IPlayer startingPlayer, int gameId, IClientUpdater updater) {
		super(startingPlayer, gameId, GameType.ROCK_PAPER_SCISSORS, updater, 3, 2, 2);
		gameStop = true;   // block moves until second player joins.
		setMoveCount(0);
		setDraw(false);
		setActiveplayer(startingPlayer);
		startingPlayer.setPlayerToken("1");
		
	}

	@Override
	public void joinGame(IClientAction action) throws SkrullGameException {
		if (!needsPlayers()){
			throw new SkrullGameException("Game Full");
		}
		super.addPlayer(action.getPlayer());

		action.getPlayer().setPlayerToken("2");
		this.setLastAction(action);
		setBroadcastMessage("Player " + action.getPlayer().getPlayerId() + " joined");

		gameStop = false;
		super.updateListener();
		
	}

	@Override
	public void doProcessMove(IClientAction action) throws SkrullGameException {
		
		if (!finished){
			
			if (getActiveplayer().equals(action.getPlayer()) && !gameStop){
		
				// Store the moves.  Player1 in board[0], PLayer2 in board[1].  The moves contain the index of the choice.
				board.setBoard(action.getMove(), getMoveCount()); 
				
				// Announce that a move was made.
				setBroadcastMessage("Player " + action.getPlayer().getPlayerToken() + " has chosen. ");
				setBroadcastMessage("Player " + action.getPlayer() + " joined");
				
				setMoveCount(getMoveCount() + 1);
				if (getMoveCount() == getMaxMoves()){
					
					finished = true;
					
					// WINNER Check
					if(haveWinner()){
					
						int p1Choice = board.getBoardLoc(0).getMoveIndex();
						int p2Choice = board.getBoardLoc(1).getMoveIndex();
						
						if((p1Choice == ROCK && p2Choice == SCISSORS) || 
									(p1Choice == PAPER && p2Choice == ROCK) || 
									(p1Choice == SCISSORS && p2Choice == PAPER))
							winner = board.getBoardLoc(0).getPlayer();
						else {
							winner = board.getBoardLoc(1).getPlayer();
						}
						setBroadcastMessage("We have a winner:  " + winner);  // TODO: Comment me out before turn in, View side will take care of this.
					}else{
						setBroadcastMessage("We have a draw");  // TODO: Comment me out before turn in, View side will take care of this.
						setDraw(true);
					}
				}	
				// Switch Active player to allow them to move.
				setActiveplayer(getLastAction().getPlayer());
				this.setLastAction(action);
				
			}
			// Not valid player
			else throw new SkrullGameException("Please wait your turn."); 
		// Game Over
		}
		else throw new SkrullGameException("Game is Over, please return to main menu.");
	
	updateListener();
	
	}
	
	private boolean haveWinner() {
		
		int p1Choice = board.getBoardLoc(0).getMoveIndex();
		int p2Choice = board.getBoardLoc(1).getMoveIndex();
		
		boolean winnerDetected = false;
		if (p1Choice != p2Choice && getMoveCount()==getMaxMoves())
			winnerDetected =true;
		return winnerDetected;
	}
}
