package edu.wpi.off.by.one.errors.code.controller;

import edu.wpi.off.by.one.errors.code.controller.menupanes.*;
import edu.wpi.off.by.one.errors.code.controller.menupanes.devtoolspanes.*;

public class ControllerSingleton {
	
	private MainPane mainPaneController;
	private MapRootPane mapRootPaneController;
	private MenuPane menuPaneController;
	private NavigationPane navigationController;
	
	private MapDevToolPane mapDevToolController;
	
	public void registerMainPane(MainPane m) { mainPaneController = m; }
	public void registerMapRootPane(MapRootPane m) { mapRootPaneController = m; }
	public void registerMenuPane(MenuPane m) { menuPaneController = m; }
	public void registerNavigationPane(NavigationPane m) { navigationController = m; }
	
	public void registerMapDevToolPane(MapDevToolPane m) { mapDevToolController = m; }
	
	public MainPane getMainPane() { return mainPaneController; }
	public MapRootPane getMapRootPane() { return mapRootPaneController; }
	public MenuPane getMenuPane() { return menuPaneController; }
	public NavigationPane getNavigationPane() { return navigationController; }
	
	public MapDevToolPane getMapDevToolPane() { return mapDevToolController; }
	
	private ControllerSingleton() {}
	
	public static ControllerSingleton getInstance() {
		return ControllerSingletonHolder.INSTANCE;
	}
	
	private static class ControllerSingletonHolder {
		private static final ControllerSingleton INSTANCE = new ControllerSingleton();
	}
}
