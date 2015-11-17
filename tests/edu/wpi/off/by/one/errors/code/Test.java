package edu.wpi.off.by.one.errors.code;

import static org.junit.Assert.*;

import org.junit.Rule;
//import org.junit.Test;
import java.lang.String;
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
        g.addNode(c1, 1);
        g.addNode(c2, 2);
        g.addEdge(1, 2, 1);
        Vector<Node> lon = g.getNodes();
        assertEquals(lon.get(0).getId(), 1);
        assertEquals(lon.get(1).getId(), 2);
        Vector<Edge> loe = g.getEdges();
        assertEquals(loe.get(0).getId(), 1);
        g.editNode(2, c1);
        assertEquals(lon.get(1).getCoordinate(), c1);
    }
    
    
    
}
