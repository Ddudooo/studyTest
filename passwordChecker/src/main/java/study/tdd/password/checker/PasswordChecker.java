package study.tdd.password.checker;

public class PasswordChecker {

	public PasswordStrength check(String s) {
		if (s == null || s.isEmpty()) {
			return PasswordStrength.INVALID;
		}
		int cnt = getMatchingCount(s);
		if (cnt >= 3) {
			return PasswordStrength.STRONG;
		} else if (cnt >= 2) {
			return PasswordStrength.NORMAL;
		} else {
			return PasswordStrength.WEAK;
		}
	}

	private int getMatchingCount(String s) {
		int cnt = 0;
		boolean lengthEnough = isLengthEnough(s);
		boolean containNum = isContainNum(s);
		boolean containUpper = isContainUpper(s);

		if (lengthEnough) {
			cnt++;
		}
		if (containNum) {
			cnt++;
		}
		if (containUpper) {
			cnt++;
		}
		return cnt;
	}

	private boolean isLengthEnough(String s) {
		return s.length() >= 8;
	}

	private boolean isContainUpper(String s) {
		boolean containUpperFlag = false;
		for (char c : s.toCharArray()) {
			if (Character.isUpperCase(c)) {
				containUpperFlag = true;
				break;
			}
		}
		return containUpperFlag;
	}

	private boolean isContainNum(String s) {
		boolean containNumFlag = false;
		for (char ch : s.toCharArray()) {
			if (ch >= '0' && ch <= '9') {
				containNumFlag = true;
				break;
			}
		}
		return containNumFlag;
	}
}