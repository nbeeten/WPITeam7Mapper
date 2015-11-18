package edu.wpi.off.by.one.errors.code;

import static org.junit.Assert.*;

import org.junit.Rule;
//import org.junit.Test;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class Test extends TestCase{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    
    //test coordinate getters
    public void testCo1(){
        Coordinate c = new Coordinate(100,200, -300);
        assertEquals(c.getX(), (float)100.0);
        assertEquals(c.getY(), (float)200.0);
        assertEquals(c.getZ(), (float)-300.0);
    }
    
    public void testCo2(){
        Coordinate c = new Coordinate(100,200);
        assertEquals(c.getX(), (float)100.0);
        assertEquals(c.getY(), (float)200.0);
        assertEquals(c.getZ(), (float)0.0);
    }
    
    public void testCo3(){
        Coordinate c = new Coordinate(400);
        assertEquals(c.getX(), (float)400.0);
        assertEquals(c.getY(), (float)400.0);
        assertEquals(c.getZ(), (float)400.0);
    }
    
    //test Graph
    public void testGraph(){
        Graph g = new Graph();
        Coordinate c1 = new Coordinate(400);
        Coordinate c2 = new Coordinate(100,0,0);
        g.addNode(c1);
        g.addNode(c2);
        g.addEdge(0, 1);
        Vector<Node> lon = g.getNodes();
        assertEquals(lon.get(0).getId(), 0);
        assertEquals(lon.get(1).getId(), 1);
        Vector<Edge> loe = g.getEdges();
        assertEquals(loe.get(0).getId(), 0);
        g.editNode(1, c1);
        assertEquals(lon.get(1).getCoordinate(), c1);
    }
    
    public void testAStar(){
    	String dir = System.getProperty("user.dir");
    	Display d = FileIO.load(dir + "/src/testmap.txt", null);
    	Graph g = d.getGraph();
    	
    	Path traversablePath = new Path(5, 12);
    	ArrayList<Integer> expTPath1 = new ArrayList<Integer>();
    	ArrayList<Integer> expTPath2 = new ArrayList<Integer>();
    	expTPath1.addAll(Arrays.asList(5, 4, 17, 6, 7, 10, 9, 11, 12));
    	expTPath2.addAll(Arrays.asList(5, 4, 17, 7, 10, 9, 11, 12));
    	traversablePath.runAStar(g.getNodes(), g.getEdges());
    	assertNotEquals(traversablePath.getRoute(), expTPath1);
    	assertEquals(traversablePath.getRoute(), expTPath2);
    	
    	Path nonTraversablePath = new Path(5, 0);
    	ArrayList<Integer> expNTPath = new ArrayList<Integer>();
    	nonTraversablePath.runAStar(g.getNodes(), g.getEdges());
    	assertEquals(nonTraversablePath.getRoute(), expNTPath);
    }
    
}
