package study.baseball;

import java.util.Objects;

public class BaseballStatus {

	private int strike;
	private int ball;

	public BaseballStatus() {
		this.strike = 0;
		this.ball = 0;
	}

	public BaseballStatus(int strike, int ball) {
		this.strike = strike;
		this.ball = ball;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BaseballStatus that = (BaseballStatus) o;
		return strike == that.strike && ball == that.ball;
	}

	@Override
	public int hashCode() {
		return Objects.hash(strike, ball);
	}

	@Override
	public String toString() {
		return "String = " + strike + ", ball = " + ball;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private BaseballStatus status = new BaseballStatus();

		public Builder strike(int strike) {
			status.strike = strike;
			return this;
		}

		public Builder ball(int ball) {
			status.ball = ball;
			return this;
		}

		public BaseballStatus build() {
			return status;
		}
	}
}
