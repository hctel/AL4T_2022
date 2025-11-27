package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import model.brick.OrdinaryBrick;

public class Unit_test {
	@Test
	public void objectMove() {
		GameObject obj = new OrdinaryBrick(0d, 0d, null);
		obj.setVelX(2);
		obj.updateLocation();
		double x = obj.getX();
		assertEquals(2, x);
	}
	
	@Test
	public void objectFall() {
		GameObject obj = new OrdinaryBrick(0d, 0d, null);
		obj.setGravityAcc(10);
		obj.updateLocation();
		double y = obj.getY();
		assertEquals(10, y);
	}
}
