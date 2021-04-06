package study.baseball;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("숫자 야구 게임 테스트")
public class BaseballTest {

	private BaseballGame game = new BaseballGame(4, 1, 3);

	@Test
	@DisplayName("숫자가 모두 맞았을 때 테스트")
	void homeRunConditionTest() {
		assertBaseBallTest("413", BaseballStatus.builder().strike(3).build());
	}

	@Nested
	@DisplayName("2 스트라이크가 나오는 경우")
	class ContainsTwoStrikeTest {

		@Test
		@DisplayName("숫자 두개만 맞을 경우 테스트 - 앞 숫자 두개")
		void twoStrikeTest() {
			assertBaseBallTest("419", BaseballStatus.builder().strike(2).build());
		}

		@Test
		@DisplayName("숫자 두개만 맞을 경우 테스트 - 뒷 숫자 두개")
		void twoStrikeTest2() {
			assertBaseBallTest("213", BaseballStatus.builder().strike(2).build());
		}
	}

	@Nested
	@DisplayName("1 스트라이크가 나오는 경우")
	class ContainOneStrikeTest {

		@Nested
		@DisplayName("1 스트라이크만 나오는 경우")
		class OnlyOneStrikeCaseTest {

			@Test
			@DisplayName("하나만 나와야함!")
			void OneStrikeCaseTest() {
				assertBaseBallTest("498", BaseballStatus.builder().strike(1).build());
				assertBaseBallTest("718", BaseballStatus.builder().strike(1).build());
				assertBaseBallTest("793", BaseballStatus.builder().strike(1).build());
			}
		}

		@Nested
		@DisplayName("1 스트라이크 1볼이 나오는 경우")
		class OneStrikeOneBallTest {

			@Test
			void OneStrikeOneBallCaseTest() {
				assertBaseBallTest("439", BaseballStatus.builder().strike(1).ball(1).build());
				assertBaseBallTest("914", BaseballStatus.builder().strike(1).ball(1).build());
				assertBaseBallTest("943", BaseballStatus.builder().strike(1).ball(1).build());
			}
		}

		@Nested
		@DisplayName("1 스트라이크 2 볼이 나오는 경우")
		class OneStrikeTwoBallTest {

			@Test
			void OneStrikeTwoBallCaseTest() {
				assertBaseBallTest("431", BaseballStatus.builder().strike(1).ball(2).build());
				assertBaseBallTest("143", BaseballStatus.builder().strike(1).ball(2).build());
				assertBaseBallTest("314", BaseballStatus.builder().strike(1).ball(2).build());
			}
		}
	}

	@Nested
	@DisplayName("0 스트라이크의 경우")
	class ZeroStrikeTest {

		@Nested
		@DisplayName("3 볼일 경우.")
		class ThreeBallTest {

			@Test
			@DisplayName("3 볼의 경우 자리수 만 틀려야 한다.")
			void ThreeBallCaseTest() {
				assertBaseBallTest("341", BaseballStatus.builder().strike(0).ball(3).build());
				assertBaseBallTest("134", BaseballStatus.builder().strike(0).ball(3).build());
			}
		}


		@Test//413
		@DisplayName("아웃의 경우, 입력된 숫자가 정답에 포함되지 않아야 한다")
		void outTest() {
			assertBaseBallTest("256", BaseballStatus.builder().build());
			assertBaseBallTest("789", BaseballStatus.builder().build());
			assertBaseBallTest("692", BaseballStatus.builder().build());
		}
	}

	@Nested
	@DisplayName("입력값 오류 테스트")
	class WrongNumberTest {

		@Test
		@DisplayName("입력 자릿 수가 모자를 경우")
		void inputNumberLenLtThreeTest() {
			assertBaseBallExceptionTest("1",
				IllegalArgumentException.class,
				"입력 자리 수가 모자릅니다!");
		}

		@Test
		@DisplayName("입력 자릿 수가 초과할 경우")
		void inputNumberLenGtThreeTest() {
			assertBaseBallExceptionTest("1234",
				IllegalArgumentException.class,
				"입력값이 너무 깁니다!");
		}

		@Test
		@DisplayName("중복된 숫자를 입력할 경우")
		void overlapInputNumberTest() {
			assertBaseBallExceptionTest("111",
				IllegalArgumentException.class,
				"각 자리 숫자는 모두 달라야 합니다!");
		}

		@Test
		@DisplayName("숫자 외에 입력될 경우")
		void inputValueIsNotNumericTest() {
			assertBaseBallExceptionTest("abc",
				IllegalArgumentException.class,
				"입력값은 숫자만 가능합니다!");
			assertBaseBallExceptionTest("가나다",
				IllegalArgumentException.class,
				"입력값은 숫자만 가능합니다!");
		}
	}

	private void assertBaseBallExceptionTest(String input,
		Class<? extends Exception> expectedExceptionType, String expectedMsg) {
		Exception exception = assertThrows(expectedExceptionType,
			() -> game.match(input));
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);
	}

	private void assertBaseBallTest(String number, BaseballStatus expect) {
		BaseballStatus answer = game.match(number);
		assertEquals(expect, answer);
	}
}