package skrull.rmi.client;

import skrull.game.model.IGameModel;
import skrull.game.view.IGameClientView;


public class ClientListener implements IClientListener {

	private IGameClientView gameClientView;

	public ClientListener(IGameClientView view) {
		this.gameClientView = view;
	}

	@Override
	public void modelChanged(IGameModel model) {
		// TODO this needs logging
		System.out.println("modelChanged in Client Listener...");
		gameClientView.modelChanged(model);
	}

	@Override
	public boolean isConnected() {
		return true;
	}



}
