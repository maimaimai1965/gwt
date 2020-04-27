package com.gwt.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SortScreen extends HorizontalPanel  {

	static Logger logger = java.util.logging.Logger.getLogger(SortScreen.class.getName());
	
	private static final int BUTTON_WIDTH = IntroScreen.BUTTON_WIDTH; 
	private static final int BUTTON_HEIGHT = IntroScreen.BUTTON_HEIGHT; 
	private static final int GAP_X = IntroScreen.GAP_X; 
	private static final int GAP_Y = IntroScreen.GAP_Y; 

	private AbsolutePanel collectionPanel;
	private Button sortButton;
	private Button resetButton;
	private VerticalPanel buttonsPanel;
	private ScrollPanel scrollPanel;
	private HorizontalPanel detailsPanel;
	private VerticalPanel mainPanel;

	private Runnable toIntroScreen;

	private int number;
	private List<Integer> numberList; 

	private boolean sortAsc = true;

	public SortScreen(Runnable toIntroScreen) {
		this.toIntroScreen = toIntroScreen; 

		collectionPanel = new AbsolutePanel();

		scrollPanel = new ScrollPanel();
		scrollPanel.setPixelSize(800, Gwt.BUTTONS_IN_COLUMN*(BUTTON_HEIGHT + GAP_Y) + 20);
		scrollPanel.add(collectionPanel);
		
		sortButton = new Button("Sort");
		sortButton.setPixelSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		sortButton.addStyleName("greenButton");

		resetButton = new Button("Reset");
		resetButton.setPixelSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		resetButton.addStyleName("greenButton");

		buttonsPanel = new VerticalPanel();
		buttonsPanel.addStyleName("buttonsPanel");		
		buttonsPanel.add(sortButton);
		Label label2 = new Label();
		label2.setPixelSize(0, GAP_Y);
		buttonsPanel.add(label2);
		buttonsPanel.add(resetButton);

		detailsPanel = new HorizontalPanel();
		detailsPanel.setHorizontalAlignment(ALIGN_LEFT);
		detailsPanel.add(scrollPanel);
		Label label3 = new Label();
		label3.setPixelSize(GAP_X*4, 0);
		detailsPanel.add(label3);
		detailsPanel.add(buttonsPanel);

		Label upLabel = new Label();
		upLabel.setPixelSize(0, 20);
		mainPanel = new VerticalPanel();
		mainPanel.add(upLabel);
		mainPanel.add(detailsPanel);
		
		sortButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sort();
			}
		});

		resetButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toIntroScreen.run();
			}
		});

	}
	
	public Panel getMainPanel() {
		return mainPanel;
	}


	public void open(int number) {
//logger.log(Level.SEVERE, "!!! number =" + number);		
		this.number = number;
		sortAsc = true;
		numberList = buildCollection(number);
		fillCollectionPanel();
		sortButton.setFocus(true);
	}

	public void close() {
		numberList.clear();
		collectionPanel.clear();
	}

	public void fillCollectionPanel() {
		collectionPanel.clear();
		int columns = numberList.size()/Gwt.BUTTONS_IN_COLUMN + ((numberList.size() % Gwt.BUTTONS_IN_COLUMN > 0) ? 1 : 0);
		
		collectionPanel.setPixelSize(columns*BUTTON_WIDTH + (columns-1)*GAP_X,
				                     Gwt.BUTTONS_IN_COLUMN*BUTTON_HEIGHT + (Gwt.BUTTONS_IN_COLUMN-1)*GAP_Y);

        int i = 0;
        int j = 0;
        int x = 0;
		for (Integer num: numberList) {
    		Button button = new Button(num.toString());
			button.setPixelSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			button.addStyleName("blueButton");

			int y = j*(BUTTON_HEIGHT + GAP_Y);
			collectionPanel.add(button, x, y);
			
			button.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
                    if (Integer.valueOf(((Button)event.getSource()).getText()) <= Gwt.MAGIC_NUMBER) {
					  toIntroScreen.run();
                    }
				}
			});

			if (++j >= Gwt.BUTTONS_IN_COLUMN) {
				j = 0;
				i++;
				x = i*(BUTTON_WIDTH + GAP_X);
			}
		}
	}

	public static List<Integer> buildCollection(int number) {
		List<Integer> sourselist = new ArrayList<>(Gwt.MAX_NUMBERS);
		for (int i = 0; i < Gwt.MAX_NUMBERS; i++) {
			sourselist.add(i+1);
		}
		List<Integer> list = new ArrayList<>(number);
		Random random = new Random();
		for (int i = 0; i < number; i++) {
			int index = 0;
			if (sourselist.size() > 1) {
			  index = random.nextInt(sourselist.size() - 1);
			}
			list.add(sourselist.remove(index));
		}

		boolean exists = false;
		for (int i: list) {
			if (i <= Gwt.MAGIC_NUMBER) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			int index = 0;
			if (list.size() > 1) {
			  index = random.nextInt(list.size() - 1);
			}
			int i = random.nextInt(Gwt.MAGIC_NUMBER-1) + 1;
            list.set(index, i);
		}
				
		return list;
	}

	private void sort() {
		sortAsc = !sortAsc;
		if (sortAsc) {
			numberList.sort(Comparator.naturalOrder());
		}
		else {
			numberList.sort(Comparator.reverseOrder());
		}

		for (int i = 0; i < collectionPanel.getWidgetCount(); i++) {
			((Button)collectionPanel.getWidget(i)).setText(numberList.get(i).toString());
		}
	}
	
}
