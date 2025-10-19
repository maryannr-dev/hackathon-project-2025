import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a single multiple-choice question. Must implement Serializable
 * to be saved/loaded by the Quiz class.
 */
public class Question implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    private String prompt;
    private List<String> answers;
    private String correctAnswer;

    /**
     * Constructor for the Question.
     */
    public Question(String prompt, List<String> answers, String correctAnswer) {
        this.prompt = prompt;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public String getPrompt() { return prompt; }
    public List<String> getAnswers() { return answers; }
    public String getCorrectAnswer() { return correctAnswer; }

    /**
     * Prints the question and its numbered answer choices to the console.
     */
    public void promptQuestion() {
        System.out.println("\n--- Question ---");
        System.out.println(this.prompt);
        System.out.println("----------------");

        // Display answers with numbers
        for (int i = 0; i < answers.size(); i++) {
            System.out.println((i + 1) + ". " + answers.get(i));
        }
        System.out.print("Enter the number of your choice: ");
    }

    /**
     * Verifies if a given answer (string or index) is correct.
     *
     * @param userAnswer The user's answer, either the full string or the index (1-based).
     * @return true if the answer is correct, false otherwise.
     */
    public boolean verifyAnswer(String userAnswer) {
        try {
            int index = Integer.parseInt(userAnswer.trim());
            if (index > 0 && index <= answers.size()) {
                // Check answer by index
                return answers.get(index - 1).equalsIgnoreCase(correctAnswer);
            }
        } catch (NumberFormatException e) {
            // Check answer by string comparison
            return userAnswer.trim().equalsIgnoreCase(correctAnswer);
        }

        return false;
    }
}