package study.tdd.password.checker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PasswordCheckerTest {

	private PasswordChecker checker = new PasswordChecker();

	private void assertPasswordCheck(String password, PasswordStrength strong) {
		PasswordStrength result = checker.check(password);
		assertEquals(strong, result);
	}

	@Test
	@DisplayName("모든 조건을 만족하는 비밀번호")
	void allCriteria() {
		assertPasswordCheck("ab12!@AB", PasswordStrength.STRONG);

		assertPasswordCheck("abc1!Add", PasswordStrength.STRONG);
	}

	@Test
	@DisplayName("길이를 제외하고 모든 조건을 만족하는 비밀번호")
	void allCriteria_excludeLengthCriteria() {
		assertPasswordCheck("a1!AB", PasswordStrength.NORMAL);
	}

	@Test
	@DisplayName("숫자를 포함하지않고 모든 조건을 만족하는 비밀번호")
	void allCriteria_excludeNumberIncluded() {
		assertPasswordCheck("ab!@Abqwer", PasswordStrength.NORMAL);
	}

	@Test
	@DisplayName("값이 없는 경우")
	void nullPassword() {
		assertPasswordCheck(null, PasswordStrength.INVALID);
	}

	@Test
	@DisplayName("비밀번호가 비어있는 문자열일 경우")
	void emptyStrPassword() {
		assertPasswordCheck("", PasswordStrength.INVALID);
	}

	@Test
	@DisplayName("대문자를 포함하지 않고 모든 조건을 만족하는 비밀번호")
	void allCriteria_excludeUppercase() {
		assertPasswordCheck("abcd@1234", PasswordStrength.NORMAL);
	}

	@Test
	@DisplayName("길이가 8글자 이상인 조건만 충족하는 비밀번호")
	void onlyLengthCriteriaMatching() {
		assertPasswordCheck("abcdefgh", PasswordStrength.WEAK);
	}

	@Test
	@DisplayName("숫자만 포함하는 비밀번호")
	void onlyNumericCriteriaMatching() {
		assertPasswordCheck("1234", PasswordStrength.WEAK);
	}

	@Test
	@DisplayName("대문자 포함 조건만 만족하는 비밀번호")
	void onlyUpperCaseCriteriaMatching() {
		assertPasswordCheck("ABCD", PasswordStrength.WEAK);
	}

	@Test
	@DisplayName("아무 조건도 만족못하는 비밀번호")
	void notMatchingCriteria() {
		assertPasswordCheck("abc", PasswordStrength.WEAK);
	}
}