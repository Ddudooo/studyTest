package study.baseball;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BaseballGame {

	private int[] answer;

	public BaseballGame(int... numbers) {
		this.answer = numbers;
	}

	public BaseballStatus match(String s) {
		validation(s);
		int strike = 0;
		int ball = 0;
		for (int i = 0; i < 3; i++) {
			int num = getNumeric(s, i);
			if (isStrike(num, i)) {
				strike++;
			} else if (isBall(num, i)) {
				ball++;
			}
		}

		return BaseballStatus.builder()
			.strike(strike)
			.ball(ball)
			.build();
	}

	private void validation(String input) {
		if (input == null) {
			throw new NullPointerException("입력 값은 Null이 될 수 없습니다!");
		}
		if (input.length() < 3) {
			throw new IllegalArgumentException("입력 자리 수가 모자릅니다!");
		}
		if (input.length() > 3) {
			throw new IllegalArgumentException("입력값이 너무 깁니다!");
		}
		if (isOverlapNumeric(input)) {
			throw new IllegalArgumentException("각 자리 숫자는 모두 달라야 합니다!");
		}
		if (!isNumeric(input)) {
			throw new IllegalArgumentException("입력값은 숫자만 가능합니다!");
		}
	}

	private boolean isNumeric(String input) {
		if (input == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean isOverlapNumeric(String input) {
		Set<Character> unique = new HashSet<>();
		for (char c : input.toCharArray()) {
			if (!unique.add(c)) {
				return true;
			}
		}
		return false;
	}

	private int getNumeric(String s, int i) {
		return s.charAt(i) - '0';
	}

	private boolean isStrike(int num, int idx) {
		return num == answer[idx];
	}

	private boolean isBall(int num, int idx) {
		return num != answer[idx] && isContains(num);
	}

	private boolean isContains(int num) {
		return Arrays.stream(answer).anyMatch(i -> i == num);
	}
}