package skrull.game.controller;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import skrull.game.model.IGameModel;
import skrull.game.view.IClientAction;
import skrull.game.view.IClientAction.ActionType;

public class AbstractGameControllerTest {

	private IGameModel model;
	private AbstractGameController controller;
	private IClientAction action;
	private IMocksControl ctrl;

	@Before
	public void setUp() throws Exception {
		
	    ctrl = EasyMock.createControl();
	    model = ctrl.createMock(IGameModel.class);
		action  = ctrl.createMock(IClientAction.class);
		controller = new DefaultGameController(model);

	}

	@Test
	public void testCheckActivity() {

		model.checkActivity();
		
		ctrl.replay();
		controller.checkActivity();
		ctrl.verify();
	}

	@Test
	public void testProcessGameActionChat() {
		ActionType a = ActionType.CHAT;
		
		EasyMock.expect(action.getActionType()).andReturn(a);
		model.chatUpdate(action);

		ctrl.replay();
		controller.processGameAction(action);
		ctrl.verify();
	}

	@Test
	public void testProcessGameActionMove() {
		ActionType a = ActionType.MOVE;
		
		EasyMock.expect(action.getActionType()).andReturn(a);
		model.processMove(action);

		ctrl.replay();
		controller.processGameAction(action);
		ctrl.verify();
	}

	@Test
	public void testProcessGameActionJoinGame() {
		ActionType a = ActionType.JOIN_GAME;
		
		EasyMock.expect(action.getActionType()).andReturn(a);
		model.joinGame(action);

		ctrl.replay();
		controller.processGameAction(action);
		ctrl.verify();
	}
	
	@Test
	public void testProcessGameActionJoinServer() {
		ActionType a = ActionType.JOIN_SERVER;
		
		EasyMock.expect(action.getActionType()).andReturn(a);
		model.joinGame(action);

		ctrl.replay();
		controller.processGameAction(action);
		ctrl.verify();
	}
	
	@Test
	public void testProcessGameActionCreateGame() {
		ActionType a = ActionType.CREATE_GAME;
		
		EasyMock.expect(action.getActionType()).andReturn(a);
		model.joinGame(action);

		ctrl.replay();
		controller.processGameAction(action);
		ctrl.verify();
	}
	
	@Test
	public void testProcessGameActionQuit() {
		ActionType a = ActionType.QUIT;
		
		EasyMock.expect(action.getActionType()).andReturn(a);
		model.quit(action);

		ctrl.replay();
		controller.processGameAction(action);
		ctrl.verify();
	}
	
}