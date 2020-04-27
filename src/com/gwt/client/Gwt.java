package com.gwt.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt implements EntryPoint {

	Logger logger = java.util.logging.Logger.getLogger(SortScreen.class.getName());

	
    public static final int MAX_NUMBERS = 1000;
	public static final int BUTTONS_IN_COLUMN = 10; 
	public static final int MAGIC_NUMBER = 30; 

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";


	private IntroScreen introScreen;  
	private SortScreen sortScreen;  

	public IntroScreen getIntroScreen() {
		if (introScreen == null) {
			introScreen = new IntroScreen((number) -> { this.toSortScreen(number); });
		}
		return introScreen;
	}
	
    public SortScreen getSortScreen() {
		if (sortScreen == null) {
			sortScreen = new SortScreen(this::toIntroScreen);
		}
		return sortScreen;
	}

	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
//	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		toIntroScreen();
	}

	private AbsolutePanel getBasePanel() {
		return RootPanel.get("base");
	}
	
	public void toIntroScreen() {
//logger.log(Level.SEVERE, "getBasePanel()=" + getBasePanel().getClass().getName());
		getBasePanel().clear();
		getBasePanel().add(getIntroScreen().getMainPanel());
		getIntroScreen().open();
	}

	public void toSortScreen(int number) {
		getBasePanel().clear();
		getBasePanel().add(getSortScreen().getMainPanel());
		getSortScreen().open(number);
	}

}
