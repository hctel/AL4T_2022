package model.prize;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class Unit_test {
    @Test
    public void coinValue() {
    		int value = 10;
    		Coin coin = new Coin(10, 15, null, value);
    		assertEquals(value, coin.getPoint());
    }
}
