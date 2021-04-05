package study.expiry.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.expiry.calculator.dto.PayData;

public class ExpiryDateCalculatorTest {

	private ExpiryDateCalculator calc = new ExpiryDateCalculator();

	@Test
	@DisplayName("납부시 한달뒤가 납부일로 됨")
	void paidAndExpiredDateAfterOneMonth() {
		PayData payData = PayData.builder()
			.billingDate(LocalDate.of(2021, 4, 1))
			.payAmount(10_000)
			.build();
		assertExpiryDate(
			payData,
			LocalDate.of(2021, 5, 1));
	}

	@Test
	@DisplayName("납부일의과 한날 뒤 일자가 같지 않음.")
	void paidDayNotEqualsBillingDay() {
		PayData payData = PayData.builder()
			.billingDate(LocalDate.of(2021, 1, 31))
			.payAmount(10_000)
			.build();
		assertExpiryDate(
			payData,
			LocalDate.of(2021, 2, 28));
	}

	@Test
	@DisplayName("첫 납부일과 만료일 일자가 다를때 납부.")
	void firstBillingDateNotEqualsExpiredDateThenBilling() {
		PayData payData = PayData.builder()
			.firstBillingData(LocalDate.of(2021, 1, 31))
			.billingDate(LocalDate.of(2021, 2, 28))
			.payAmount(10_000)
			.build();
		assertExpiryDate(payData, LocalDate.of(2021, 3, 31));
	}

	@Test
	@DisplayName("이만원 이상 납부시 돈에 비례하여 만료일 계산")
	void overTwoMonthBillingAmountThenCalculateExpiredDate() {
		assertExpiryDate(PayData.builder()
				.billingDate(LocalDate.of(2021, 3, 1))
				.payAmount(20_000)
				.build(),
			LocalDate.of(2021, 5, 1));

		assertExpiryDate(PayData.builder()
				.billingDate(LocalDate.of(2021, 3, 1))
				.payAmount(30_000)
				.build(),
			LocalDate.of(2021, 6, 1));
	}

	@Test
	@DisplayName("이만원 이상 납부시 돈에 비례하여 만료일 계산, 첫 납부일과 만료일이 다를 때")
	void overTwoMonthBillingAmountThenCalculateExpiredDateWithFirstBillingDate() {
		assertExpiryDate(PayData.builder()
				.firstBillingData(LocalDate.of(2021, 1, 31))
				.billingDate(LocalDate.of(2021, 2, 28))
				.payAmount(20_000)
				.build(),
			LocalDate.of(2021, 4, 30));
	}

	@Test
	@DisplayName("십만원 납부시 1년 제공")
	void paidAmountTenMonthOneTimeThenGivenOneYear() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2021, 1, 28))
				.payAmount(100_000)
				.build(),
			LocalDate.of(2022, 1, 28)
		);
	}

	@Test
	@DisplayName("십삼만원 납부시 1년 3개월 제공")
	void payAmount13ThenOneYearThreeMonth() {
		assertExpiryDate(
			PayData.builder()
				.billingDate(LocalDate.of(2021, 1, 28))
				.payAmount(130_000)
				.build(),
			LocalDate.of(2022, 4, 28)
		);
	}


	private void assertExpiryDate(PayData payData, LocalDate expectedDate) {
		LocalDate expiredDate = calc.calculateExpiryDate(payData);
		assertEquals(expectedDate, expiredDate);
	}
}