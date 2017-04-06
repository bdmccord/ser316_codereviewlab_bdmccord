package banking.primitive.core;

public class Savings extends Account {
	private static final long serialVersionUID = 111L;
	
	private static float DEPOSIT_FEE                         = 0.50f;
	private static float WITHDRAW_FEE                        = 1.0f;
	private static int   MAX_FREE_WITHDRAWS                  = 3;
	private static float ZERO 								 = 0.0f;

	private int numWithdraws = 0;

	public Savings(String name) {
		super(name);
	}

	public Savings(String name, float balance) throws IllegalArgumentException {
		super(name, balance);
	}

	/**
	 * A deposit comes with a fee of 50 cents per deposit
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > ZERO) {
			balance = balance + amount - DEPOSIT_FEE;
			if (balance >= ZERO) {
				setState(State.OPEN);
			}
		}
		return false;
	}

	/**
	 * A withdrawal. After 3 withdrawals a fee of $1 is added to each withdrawal.
	 * An account whose balance dips below 0 is in an OVERDRAWN state
	 */
	public boolean withdraw(float amount) {
		if (getState() == State.OPEN && amount > ZERO) {
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > MAX_FREE_WITHDRAWS)
				balance = balance - WITHDRAW_FEE;
			// KG BVA: should be < 0
			if (balance <= ZERO) {
				setState(State.OVERDRAWN);
			}
			return true;
		}
		return false;
	}
	
	public String getType() { return "Checking"; }

	public String toString() {
		return "Savings: " + getName() + ": " + getBalance();
	}
}
