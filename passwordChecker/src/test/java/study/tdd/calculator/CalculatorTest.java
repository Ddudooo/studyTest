package study.tdd.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTest {
	@Test
	void plus() {
		int answer = Calculator.plus(1,2);
		assertEquals(3, answer);
	}
}