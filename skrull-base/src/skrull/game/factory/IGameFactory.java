package skrull.game.factory;

import skrull.game.controller.IGameController;
import skrull.game.controller.IServerController;
import skrull.game.model.IPlayer;

public interface IGameFactory {

	public enum GameType {
		DEFAULT,TIC_TAC_TOE,ROCK_PAPER_SCISSORS;
	}

	public static final int DEFAULT_GAME_ID = 0;

	/**
	 * Should return a single GameController,
	 *  with attendant gameModel and all leaf objects instantiated (except for additional players)
	 *  
	 *  Should handle every type of GameType
	 */
	public IGameController setupGame(GameType type, IPlayer startingPlayer, int gameId);

}