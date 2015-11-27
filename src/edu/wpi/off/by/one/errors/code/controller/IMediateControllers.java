package edu.wpi.off.by.one.errors.code.controller;

import edu.wpi.off.by.one.errors.code.controller.menucontrollers.menupanecontrollers.devToolsController;

/**
 * Interface for the controller mediator
 *
 */
public interface IMediateControllers {
	void registerEditorController(devToolsController ec);
	void registerMainController(MainController mc);
}
