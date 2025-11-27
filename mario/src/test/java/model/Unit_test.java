package model;

import org.junit.Test;
import static org.junit.Assert.*;

import model.brick.OrdinaryBrick;

public class Unit_test {
	@Test
	public void objectMove() {
		GameObject obj = new OrdinaryBrick(0d, 0d, null);
		obj.setVelX(2);
		obj.updateLocation();
		double x = obj.getX();
		assertEquals(2, x, 0.1);
	}
	
	@Test
	public void objectFall() {
		GameObject obj = new OrdinaryBrick(0d, 0d, null);
		obj.setGravityAcc(1);
		obj.setFalling(true);
		obj.updateLocation();
		obj.updateLocation();
		double y = obj.getY();
		assertEquals(1, y, 0.1);
	}
}
