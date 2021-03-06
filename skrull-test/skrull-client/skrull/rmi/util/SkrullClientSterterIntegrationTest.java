package skrull.rmi.util;

import static org.junit.Assert.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import skrull.game.factory.IGameFactory.GameType;
import skrull.game.view.IClientAction;
import skrull.game.view.IClientAction.ActionType;
import skrull.rmi.PolicyFileLocater;
import skrull.rmi.server.IServerListener;
import skrull.util.SkrullClientStarter;
import skrull.util.SystemPropertyReader;

/**
 * Tests the startup config
 * and client-side initialization
 * 
 * @author jesse
 *
 */
public class SkrullClientSterterIntegrationTest {



	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	private MockServerListener listener;

	@Before
	public void setUp() throws Exception {
		
		//playerId = UUID.randomUUID();
		listener = new MockServerListener();
        
		
		System.setProperty("skrull.properties", "skrull.client.properties");
		SystemPropertyReader.readProperties();
        System.setProperty("java.rmi.server.codebase", IServerListener.class
            .getProtectionDomain().getCodeSource().getLocation().toString());

        System.setProperty("java.security.policy", PolicyFileLocater.getLocationOfPolicyFile());

        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		
        final Registry registry = LocateRegistry.getRegistry();
        Remote engineStub = UnicastRemoteObject.exportObject(listener, 0);
        registry.rebind(IServerListener.SERVICE_NAME, engineStub);
        
	}

	@Test
	public void viewToServer() throws Exception{

	
			@SuppressWarnings("unused")
			SkrullClientStarter starter = new SkrullClientStarter();
			assertTrue(listener.action.getActionType().equals(ActionType.JOIN_SERVER));
			assertTrue(listener.action.getGameType().equals(GameType.DEFAULT));
		
	}
	
	
	public class MockServerListener implements IServerListener {

		private IClientAction action;

		@Override
		public void processClientAction(IClientAction action)
				throws RemoteException {
			this.action = action;
		}

	}

}
