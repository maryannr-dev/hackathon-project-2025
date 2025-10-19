import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinancialLiteracyApp {
    
    // File names for the three serialized quizzes
    private static final String BEGINNER_FILE = "beginner.ser";
    private static final String INTERMEDIATE_FILE = "intermediate.ser";
    private static final String ADVANCED_FILE = "advanced.ser";
    
    public static void main(String[] args) {
        System.out.println("--- Financial Literacy Quiz Manager ---");
        
        Quiz beginnerQuiz = Quiz.loadQuiz(BEGINNER_FILE);
        Quiz intermediateQuiz = Quiz.loadQuiz(INTERMEDIATE_FILE);
        Quiz advancedQuiz = Quiz.loadQuiz(ADVANCED_FILE);

        if (beginnerQuiz == null || intermediateQuiz == null || advancedQuiz == null) {
            System.out.println("\n🚨 Saved quizzes not found. Creating and saving new quizzes...");
            
            // Create the quizzes and save them
            List<Quiz> quizzes = createAllQuizzes();
            beginnerQuiz = quizzes.get(0);
            intermediateQuiz = quizzes.get(1);
            advancedQuiz = quizzes.get(2);
            
            saveAllQuizzes(beginnerQuiz, intermediateQuiz, advancedQuiz);
            System.out.println("✅ Three new quizzes saved successfully.");
        } else {
            System.out.println("\n✅ Successfully loaded three existing quizzes.");
            System.out.println("Latest Beginner Score: " + beginnerQuiz.getMostRecentScore() + 
                               ", Latest Intermediate Score: " + intermediateQuiz.getMostRecentScore() + 
                               ", Latest Advanced Score: " + advancedQuiz.getMostRecentScore());
        }

        // --- Demo Run: Run the Beginner Quiz ---
        System.out.println("\n==============================================");
        System.out.println("Starting the " + beginnerQuiz.getName() + " for demonstration.");
        System.out.println("==============================================");
        
        beginnerQuiz.startQuiz();
        
        // --- Re-save the Beginner Quiz to update its score history ---
        if (beginnerQuiz.saveQuiz(BEGINNER_FILE)) {
            System.out.println("\n💾 Beginner Quiz score history updated and saved.");
        }
    }

    /**
     * Creates and populates all three quizzes with 10 questions each.
     */
    private static List<Quiz> createAllQuizzes() {
        Quiz beginner = new Quiz("Financial Literacy: Beginner Basics");
        Quiz intermediate = new Quiz("Financial Literacy: Intermediate Concepts");
        Quiz advanced = new Quiz("Financial Literacy: Advanced Investing & Tax");

        // --- BEGINNER QUIZ (10 Questions: Focus on basics, definitions) ---
        beginner.addQuestion(new Question("What is the primary purpose of a budget?", List.of("Tracking investments", "Planning spending", "Paying taxes", "Borrowing money"), "Planning spending"));
        beginner.addQuestion(new Question("Which term describes money you earn?", List.of("Expense", "Liability", "Income", "Asset"), "Income"));
        beginner.addQuestion(new Question("An 'Emergency Fund' should typically cover how many months of living expenses?", List.of("1 month", "3-6 months", "1 year", "It depends on salary only"), "3-6 months"));
        beginner.addQuestion(new Question("What is the term for a fixed, regular payment you owe?", List.of("Interest", "Principal", "Rent", "Liability"), "Rent"));
        beginner.addQuestion(new Question("What is debt?", List.of("Money you save", "Money owed to someone else", "Money earned from a job", "A type of investment"), "Money owed to someone else"));
        beginner.addQuestion(new Question("The interest paid on both the principal and previously earned interest is called:", List.of("Simple interest", "Compound interest", "Annual interest", "Fixed interest"), "Compound interest"));
        beginner.addQuestion(new Question("Which of these is typically considered a 'Need'?", List.of("New phone", "Streaming service", "Rent/Housing", "Vacation"), "Rent/Housing"));
        beginner.addQuestion(new Question("What is the main danger of paying only the minimum on a credit card?", List.of("You won't get rewards", "It lowers your credit score immediately", "You pay a lot more interest over time", "The card is immediately canceled"), "You pay a lot more interest over time"));
        beginner.addQuestion(new Question("A bank account that is used for daily transactions and bill payments is a:", List.of("Savings account", "CD", "Checking account", "Brokerage account"), "Checking account"));
        beginner.addQuestion(new Question("What is the name of the score used to determine your creditworthiness?", List.of("FICO Score", "GPA", "AP Score", "Asset Score"), "FICO Score"));

        // --- INTERMEDIATE QUIZ (10 Questions: Focus on practical application, common terms) ---
        intermediate.addQuestion(new Question("What does APR stand for in the context of loans?", List.of("Annual Purchase Rate", "Asset Profit Ratio", "Annual Percentage Rate", "Actual Payment Requirement"), "Annual Percentage Rate"));
        intermediate.addQuestion(new Question("What is 'diversification' in investing?", List.of("Putting all money into one stock", "Saving money in a single bank account", "Spreading investments across different assets", "Investing only in international markets"), "Spreading investments across different assets"));
        intermediate.addQuestion(new Question("What is the portion of an insurance claim you pay before the insurance company covers the rest?", List.of("Premium", "Deductible", "Claim limit", "Co-pay"), "Deductible"));
        intermediate.addQuestion(new Question("Which term refers to the rate at which the general level of prices for goods and services is rising?", List.of("Deflation", "Recession", "Inflation", "Liquidity"), "Inflation"));
        intermediate.addQuestion(new Question("The difference between your gross income and your net income is primarily due to:", List.of("Stock market losses", "Taxes and withholdings", "Credit card debt", "Utility bills"), "Taxes and withholdings"));
        intermediate.addQuestion(new Question("What is the 'Rule of 72' used to estimate?", List.of("The total value of a home mortgage", "How long it takes an investment to double", "The required interest rate for retirement", "The maximum allowable credit card debt"), "How long it takes an investment to double"));
        intermediate.addQuestion(new Question("A **Roth IRA** is an investment vehicle where:", List.of("Contributions are taxed, but withdrawals in retirement are tax-free", "Contributions are tax-deductible, but withdrawals are taxed", "All gains are taxed annually", "It is only available to those under 30"), "Contributions are taxed, but withdrawals in retirement are tax-free"));
        intermediate.addQuestion(new Question("What is the riskiest type of investment listed below?", List.of("U.S. Treasury Bond", "High-yield Savings Account", "Blue-chip Stock", "Individual Penny Stocks"), "Individual Penny Stocks"));
        intermediate.addQuestion(new Question("A **principal** payment on a loan refers to:", List.of("The interest portion of the payment", "The amount paid toward the original borrowed sum", "The amount paid to the bank owner", "The total fees charged"), "The amount paid toward the original borrowed sum"));
        intermediate.addQuestion(new Question("Which of these expenses is generally NOT considered discretionary?", List.of("Gym membership", "Cable television", "Food and utilities", "Restaurant meals"), "Food and utilities"));

        // --- ADVANCED QUIZ (10 Questions: Focus on complex instruments, tax implications) ---
        advanced.addQuestion(new Question("The tax paid on profits generated from the sale of an asset held for over one year is called:", List.of("Income tax", "Marginal tax", "Short-term capital gains tax", "Long-term capital gains tax"), "Long-term capital gains tax"));
        advanced.addQuestion(new Question("What investment term is represented by the formula: Market Price per Share / Earnings per Share (EPS)?", List.of("Dividend Yield", "P/E Ratio", "Beta", "Book Value"), "P/E Ratio"));
        advanced.addQuestion(new Question("What is the primary purpose of **Dollar-Cost Averaging**?", List.of("To time the market's peak", "To reduce the impact of market volatility", "To invest a large lump sum at once", "To guarantee a profit"), "To reduce the impact of market volatility"));
        advanced.addQuestion(new Question("What is an **expense ratio** on a mutual fund?", List.of("The daily return of the fund", "The annual fee charged to manage the fund", "The maximum gain allowed on the fund", "The number of stocks held in the fund"), "The annual fee charged to manage the fund"));
        advanced.addQuestion(new Question("In the US, what is the key difference between a **Traditional 401(k)** and a **Roth 401(k)**?", List.of("Contribution limits are different", "Employer matching is higher for Roth", "Tax treatment of contributions and withdrawals", "Only the Roth account can invest in stocks"), "Tax treatment of contributions and withdrawals"));
        advanced.addQuestion(new Question("What term describes a tax system where higher income individuals pay a larger percentage of their income in tax?", List.of("Flat tax", "Regressive tax", "Progressive tax", "Excise tax"), "Progressive tax"));
        advanced.addQuestion(new Question("Which professional has a **Fiduciary Duty** to always act in their client's best financial interest?", List.of("Insurance Agent", "Certified Public Accountant (CPA)", "Registered Investment Advisor (RIA)", "Stockbroker"), "Registered Investment Advisor (RIA)"));
        advanced.addQuestion(new Question("A **Zero-Based Budget** requires that:", List.of("You only pay for expenses that cost zero dollars", "Every dollar of income is assigned a purpose", "You only spend money after the month is over", "Savings are excluded from the budget"), "Every dollar of income is assigned a purpose"));
        advanced.addQuestion(new Question("What is **Liquidity Risk**?", List.of("The risk of inflation making money worthless", "The risk that an asset cannot be sold quickly without losing value", "The risk of a company going bankrupt", "The risk of interest rates changing"), "The risk that an asset cannot be sold quickly without losing value"));
        advanced.addQuestion(new Question("A **convertible bond** gives the holder the option to exchange the bond for what?", List.of("A different bond with a lower interest rate", "A new car", "A predetermined number of the issuer's common stock", "Cash equal to the original face value"), "A predetermined number of the issuer's common stock"));
        
        return List.of(beginner, intermediate, advanced);
    }
    
    /**
     * Helper to save all quizzes to their respective files.
     */
    private static void saveAllQuizzes(Quiz beginner, Quiz intermediate, Quiz advanced) {
        beginner.saveQuiz(BEGINNER_FILE);
        intermediate.saveQuiz(INTERMEDIATE_FILE);
        advanced.saveQuiz(ADVANCED_FILE);
    }
}


