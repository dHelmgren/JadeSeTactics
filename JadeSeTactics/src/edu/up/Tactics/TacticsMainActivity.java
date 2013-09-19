package edu.up.Tactics;

import edu.up.game.GameConfig;
import edu.up.game.GameMainActivity;
import edu.up.game.GamePlayerType;
import edu.up.game.LocalGame;
import edu.up.game.ProxyGame;
import edu.up.game.ProxyPlayer;

/**
 * -- Tactics Main Acticity --
 * 
 * The activity that configures the game and holds the main activity
 * 
 * @author Seth Schneider
 * @author Janel Rab
 * @author Devin Helmgren
 * 
 * @date 11/16/2012
 * @version 1.2
 * 
 */

public class TacticsMainActivity extends GameMainActivity 
{
	/**
	 * createDefaultConfig()
	 * 
	 * creates the default game configuration
	 * 
	 * @return GameConfig
	 */
	@Override
	public GameConfig createDefaultConfig() 
	{
		// Define the allowed player types
		GamePlayerType[] playerTypes = new GamePlayerType[3];
		playerTypes[0] = new GamePlayerType("Local Human Player", false,
				"edu.up.Tactics.TacticsHumanPlayer");
		playerTypes[1] = new GamePlayerType("RandoCalrissian", false,
				"edu.up.Tactics.TacticsRandoCalrissian");
		playerTypes[2] = new GamePlayerType("AdmiralHackbar", false,
				"edu.up.Tactics.TacticsAdmiralHackbar");

		// Create a game configuration class for Counter
		GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2,
				"JaDeSe Tactics Game");

		// Add the default players
		defaultConfig.addPlayer("Human", 0);
		defaultConfig.addPlayer("Computer", 1);

		// done!
		return defaultConfig;
	}// createDefaultConfig

	@Override
	public LocalGame createLocalGame(GameConfig config)
	{
		return new TacticsGame(config, 0);
	}// LocalGame()

	@Override
	public ProxyPlayer createRemotePlayer() 
	{
		// TODO Auto-generated method stub
		return null;
	}// ProxyPlay()

	@Override
	public ProxyGame createRemoteGame(String hostName) 
	{
		// TODO Auto-generated method stub
		return null;
	}// ProxyGame()

}// TacticsMainActivity
