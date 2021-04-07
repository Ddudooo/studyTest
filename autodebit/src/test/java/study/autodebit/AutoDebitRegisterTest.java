package study.autodebit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static study.autodebit.CardValidity.THEFT;
import static study.autodebit.CardValidity.VALID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoDebitRegisterTest {

	private AutoDebitRegister register;

	@BeforeEach
	void setUp() {
		CardNumberValidator validator = new CardNumberValidator();
		AutoDebitInfoRepository repository = new JpaAutoDebitInfoRepository();
		register = new AutoDebitRegister(validator, repository);
	}

	@Test
	void validCard() {
		AutoDebitReq req = new AutoDebitReq("user1", "1234123412341234");
		RegisterResult result = this.register.register(req);
		assertEquals(VALID, result.getValidity());
	}

	@Test
	void theftCard() {
		AutoDebitReq req = new AutoDebitReq("user1", "1234567890123456");
		RegisterResult result = this.register.register(req);
		assertEquals(THEFT, result.getValidity());
	}
}