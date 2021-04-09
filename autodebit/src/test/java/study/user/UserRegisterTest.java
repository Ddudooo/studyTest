package study.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRegisterTest {

	private UserRegister userRegister;
	private StubWeakPasswordChecker passwordChecker =
		new StubWeakPasswordChecker();

	private MemoryUserRepository repository = new MemoryUserRepository();
	private SpyEmailNotifier notifier = new SpyEmailNotifier();

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(passwordChecker, repository, notifier);
	}

	@Test
	void weakPasswrod() {
		passwordChecker.setWeak(true);

		assertThrows(WeakPasswordException.class,
			() -> userRegister.register("id", "pw", "email"));
	}

	@Test
	void dupIdExists() {
		// 이미 같은 ID 존재하는 상황 만들기
		repository.save(new User("id", "pw1", "email@email.com"));

		assertThrows(DupIdException.class, () -> {
			userRegister.register("id", "pw2", "email");
		});
	}

	@Test
	void noDupId_RegisterSuccess() {
		userRegister.register("id", "pw", "email");

		User savedUser = repository.findById("id");
		assertEquals("id", savedUser.getId());
		assertEquals("email", savedUser.getEmail());
	}

	@Test
	void whenRegisterThenSendMail() {
		userRegister.register("id", "pw", "email@email.com");

		assertTrue(notifier.isCalled());
		assertEquals(
			"email@email.com",
			notifier.getEmail());
	}
}
