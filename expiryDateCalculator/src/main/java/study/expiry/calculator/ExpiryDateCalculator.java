package study.expiry.calculator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;
import study.expiry.calculator.dto.PayData;

public class ExpiryDateCalculator {

	public LocalDate calculateExpiryDate(PayData payData) {
		int addedMonth = getAddMonth(payData.getPayAmount());
		if (!Objects.isNull(payData.getFirstBillingDate())) {
			return expiryDateUsingFirstBillingDate(payData, addedMonth);
		}
		return payData.getBillingDate().plusMonths(addedMonth);
	}

	private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonth) {
		LocalDate candidateExpiredDate = payData.getBillingDate().plusMonths(addedMonth);
		final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
		if (isSameDayOfMonth(candidateExpiredDate, dayOfFirstBilling)) {
			final int dayLenOfCandiMon = YearMonth.from(candidateExpiredDate).lengthOfMonth();
			if (lastDayOfMonth(dayOfFirstBilling, dayLenOfCandiMon)) {
				return candidateExpiredDate
					.withDayOfMonth(dayLenOfCandiMon);
			}
			return candidateExpiredDate
				.withDayOfMonth(dayOfFirstBilling);
		} else {
			return candidateExpiredDate;
		}
	}

	private int getAddMonth(int payAmount) {
		int addMonth = 0;
		if (payAmount >= 100_000) {
			int yearCnt = payAmount / 100_000;
			addMonth += yearCnt * 12;
			payAmount -= yearCnt * 100_000;
		}
		addMonth += payAmount / 10_000;
		return addMonth;
	}

	private boolean lastDayOfMonth(int dayOfFirstBilling, int dayLenOfCandiMon) {
		return dayLenOfCandiMon < dayOfFirstBilling;
	}

	private boolean isSameDayOfMonth(LocalDate candidateExpiredDate, int dayOfFirstBilling) {
		return dayOfFirstBilling != candidateExpiredDate
			.getDayOfMonth();
	}
}