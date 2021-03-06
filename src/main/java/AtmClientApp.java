import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;


public class AtmClientApp
{
	private DecimalFormat hundredthPrecision = new DecimalFormat(); 
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		//DecimalFormat paddedFormat = new DecimalFormat(".00");

		// Instantiate the new class
		ATM atm = new ATM("1234", new BigDecimal(100.00));
		
		// Display ATM menu
		final String ATM_MENU = "Press 1 to deposit funds\nPress 2 to withdrawl funds\nPress 3 to check current balance\nPress 4 to exit";
		
		final int MAX_ATTEMPTS_OF_PIN_ENTRY = 3;
		int pinEntryAttempts = 1;
		
		// Declare user response string
		String userMenuResponse = "";
		
		
		// Pin entry
		System.out.println("Please enter your pin.");
		String userPin = input.nextLine();
		
		while (!atm.isAllowedAccess(userPin) && pinEntryAttempts < MAX_ATTEMPTS_OF_PIN_ENTRY)
		{
			System.out.println("Incorrect. Please try again.");
			++pinEntryAttempts;
			userPin = input.nextLine();
		}

		if (atm.isAllowedAccess(userPin))
		{
			System.out.println("Thank you for signing in. Please select an option: ");
			System.out.println(ATM_MENU);
			userMenuResponse = input.nextLine();

			while (userMenuResponse.equals("1") || userMenuResponse.equals("2") || userMenuResponse.equals("3"))
			{
				if (userMenuResponse.equals("1"))
				{
					System.out.println("Please enter a deposit amount: ");
					
					depositTransaction(input, atm);
				}
				else if (userMenuResponse.equals("2"))
				{
					System.out.println("Please enter a withdraw amount: ");
					//BigDecimal withdrawAmount = input.nextBigDecimal();
					BigDecimal newBankBalance = withdrawTransaction(atm, input);
					//String paddedWithdrawAmount = paddedFormat.format(withdrawAmount);
				}
				else if (userMenuResponse.equals("3"))
				{
					System.out.println("Your current balance is: " + atm.getBalance() + "\n");
				}
				
				System.out.println(ATM_MENU);
				userMenuResponse = input.nextLine();
			}
			
			if (userMenuResponse.equals("4"))
			{
				System.out.println("Thank you for banking with WeCanCodeIT Inc. Goodbye!");
				System.exit(0);
			}
		}
		else
		{
			System.out.println("You have exceeded the maximum amount of attempts. Terminating.");
			System.exit(0);
		}

		input.close();
	}

	private static void depositTransaction(Scanner input, ATM atm)
	{
		
		BigDecimal depositAmount = input.nextBigDecimal();
		input.nextLine();
		
		DecimalFormat paddDepositAmount = new DecimalFormat(); 

		System.out.println("Your deposit of " + paddDepositAmount.format(depositAmount )+ " was successful.");
		System.out.println("Your balance now is " + atm.deposit(depositAmount)  + "\n");
	}

	private static void withdrawTransaction(ATM atm, Scanner input)
	{
		
		BigDecimal withdrawAmount = input.nextBigDecimal();
		input.nextLine();

		// unsuccessful withdraw
		if (!atm.canWithdraw(withdrawAmount))
		{
			System.out.println("Withdraw was not successful. Insufficient funds. Enter another withdraw amount, or press 4 to exit to the main transaction screen.");
			withdrawTransaction(atm, input);
			
		}
		else
		{
			atm.withdraw(withdrawAmount);
		}
	}


}
