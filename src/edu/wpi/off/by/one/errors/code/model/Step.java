package edu.wpi.off.by.one.errors.code.model;

import java.util.ArrayList;

public class Step {
	private Node startNode;
	private Node endNode;
	private String instruction;
	
	public Step(Node startNode, Node endNode){
		this.startNode = startNode;
		this.endNode = endNode;
		this.instruction = "";
	}
	
	public Node getStart(){
		return startNode;
	}
	
	public Node getEnd(){
		return endNode;
	}
	
	public void setStart(Node newStart){
		startNode = newStart;
	}
	
	public void setEnd(Node newEnd){
		endNode = newEnd;
	}
	
	public String getInstructions(){
		return instruction;
	}
	
	public void setInstructions(String newInstruct){
		instruction += newInstruct;
	}
	
	@Override
	public String toString(){
		return instruction;
	}
}
