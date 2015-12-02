package edu.wpi.off.by.one.errors.code.controller.customcontrols;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.*;
/**
 * Original code by Caleb Brinkman (floralvikings)
 * source: https://gist.github.com/floralvikings/10290131
 *
 */
public class AutoCompleteTextField extends TextField{
	private final SortedSet<String> entries;
	private ContextMenu entriesPopup;
	
	public AutoCompleteTextField(){
		super();
		entries = new TreeSet<>();
		entriesPopup = new ContextMenu();
		//TODO ADD MORE ENTRIES
		//TODO SMART SEARCH
		entries.add("CC");
        entries.add("Campus Center");
		textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				if(getText().length() == 0) entriesPopup.hide();
				else{
					LinkedList<String> result = new LinkedList<>();
					result.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
					if(entries.size() > 0){
						populatePopup(result);
						if(!entriesPopup.isShowing()) entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
					}
					else {
						entriesPopup.hide();
					}
				}
			}
		});
		
		focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				entriesPopup.hide();
			}
			
		});
	}
	
	public SortedSet<String> getEntries() { return entries; }
	
	private void populatePopup(List<String> result){
		List<CustomMenuItem> menuItems = new LinkedList<>();
		int maxEntries = 10;
		int count = Math.min(result.size(), maxEntries);
		for(int i = 0; i < count; i++){
			final String searchresult = result.get(i);
			Label entryLabel = new Label(searchresult);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			item.setOnAction(e -> {
				setText(searchresult);
				entriesPopup.hide();
			});
			menuItems.add(item);
		}
		entriesPopup.getItems().clear();
		entriesPopup.getItems().addAll(menuItems);
	}
	
}
