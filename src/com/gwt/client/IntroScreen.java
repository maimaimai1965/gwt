package com.gwt.client;

import java.util.function.Consumer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IntroScreen {

	public static final int BUTTON_WIDTH = 90; 
	public static final int BUTTON_HEIGHT = 30; 
	public static final int GAP_X = 14; 
	public static final int GAP_Y = 7; 

	private Label questionLabel;
	private TextBox numberTextBox;
	private Label errorLabel;
	private Button enterButton;
	private VerticalPanel mainPanel; 

	private int number = 0;
	
	private Consumer<Integer> toSortScreen;
		

	public VerticalPanel getMainPanel() {
		return mainPanel;
	}

	public int getNumber() {
		return number;
	}

	public IntroScreen(Consumer<Integer> toSortScreen) {
		this.toSortScreen = toSortScreen; 

        questionLabel = new Label();
        questionLabel.addStyleName("bold");
		questionLabel.setText("How many numbers to display?");
		
		numberTextBox = new TextBox();
		numberTextBox.setWidth(Integer.valueOf(BUTTON_WIDTH).toString() + "px");

		errorLabel = new Label();
		errorLabel.setVisible(false);
		errorLabel.addStyleName("errorLabel");
		
		enterButton = new Button("Enter");
  		enterButton.setPixelSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		enterButton.addStyleName("blueButton");

		Label upPanel = new Label();
		upPanel.setPixelSize(0, 100);
	    mainPanel = new VerticalPanel();
	    
	    mainPanel.setSpacing(GAP_Y);
	    mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);	    
	    mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    mainPanel.add(upPanel);
	    mainPanel.add(questionLabel);
	    mainPanel.add(numberTextBox);
	    mainPanel.add(errorLabel);
	    mainPanel.add(enterButton);

		enterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
		    	  checkNumberAndGoToSortScreen();
			}
		});

		numberTextBox.addKeyUpHandler(new KeyUpHandler() {
		      @Override
		      public void onKeyUp(KeyUpEvent event) {
		    	  if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    		  checkNumberAndGoToSortScreen();
		    	  }
		      }
		    });
	}

	public void open() {
	    errorLabel.setVisible(false);
	    errorLabel.setText("");
	    numberTextBox.setText("");
	
		numberTextBox.setFocus(true);
		numberTextBox.selectAll();
	}

	public void close() {
		mainPanel.removeFromParent();
	}

    private void checkNumberAndGoToSortScreen() {
		if (isValidNumberField()) {
			toSortScreen.accept(number);
		}
    }
	
	private boolean isValidNumberField() {
	    String str = numberTextBox.getValue();
	    if (str == null) {
	        showError("Numbers must be entered!");
	        return false;
	    }
	    try {
	      number = Integer.valueOf(str);
	    }
	    catch (NumberFormatException e) {
	        showError("Must be entered number!");
	        return false;
	    }
	    if (number <= 0) {
	        showError("Numbers must be greater than zero!");
	        return false;
	    }
	    if (number > Gwt.MAX_NUMBERS) {
	        showError("Numbers cannot be more than " + Gwt.MAX_NUMBERS + "!");
	        return false;
	    }
	    
        return true;		
	}
	
	private void showError(String message) {
	   errorLabel.setText(message);
	   errorLabel.setVisible(true);
	}
	
}
