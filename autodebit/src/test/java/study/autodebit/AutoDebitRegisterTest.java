package study.autodebit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static study.autodebit.CardValidity.INVALID;
import static study.autodebit.CardValidity.THEFT;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoDebitRegisterTest {

	private AutoDebitRegister register;
	private StubCardNumberValidator validator;
	private MemoryAutoDebitInfoRepository repository;

	@BeforeEach
	void setUp() {
		validator = new StubCardNumberValidator();
		repository = new MemoryAutoDebitInfoRepository();
		register = new AutoDebitRegister(validator, repository);
	}

	@Test
	void inValidCard() {
		validator.setInvalidNo("1111122223333");

		AutoDebitReq req = new AutoDebitReq("user1", "1111122223333");
		RegisterResult result = register.register(req);

		assertEquals(INVALID, result.getValidity());
	}

	@Test
	void theftCard() {
		validator.setTheftNo("1234567890123456");

		AutoDebitReq req = new AutoDebitReq("user1", "1234567890123456");
		RegisterResult result = this.register.register(req);
		assertEquals(THEFT, result.getValidity());
	}

	@Test
	void alreadyRegistered_InfoUpdated() {
		repository.save(new AutoDebitInfo("user1", "111222333444", LocalDateTime.now()));

		AutoDebitReq req = new AutoDebitReq("user1", "123456789012");
		RegisterResult result = this.register.register(req);

		AutoDebitInfo saved = repository.findOne("user1");
		assertEquals("123456789012", saved.getCardNumber());
	}

	@Test
	void notYetRegistered_newInfoRegistered() {
		AutoDebitReq req = new AutoDebitReq("user1", "1234123412341234");
		RegisterResult result = this.register.register(req);

		AutoDebitInfo saved = repository.findOne("user1");
		assertEquals("1234123412341234", saved.getCardNumber());
	}
}