import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a multiple-choice quiz. Implements Serializable for binary file persistence.
 */
public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Question> questions;
    
    // 'transient' means this field will NOT be saved to the file, as it's only needed
    // during a live quiz session.
    private transient int currentScore;
    
    private int mostRecentScore;
    private List<Integer> scoreHistory; 

    /**
     * Constructor for the Quiz.
     */
    public Quiz(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
        this.scoreHistory = new ArrayList<>();
        this.currentScore = 0;
        this.mostRecentScore = 0;
    }

    // Getters
    public String getName() { return name; }
    public int getCurrentScore() { return currentScore; }
    public int getMostRecentScore() { return mostRecentScore; }

    /**
     * Adds a question to the quiz.
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    /**
     * Retrieves a specific question by its 0-based index.
     */
    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    /**
     * Starts the quiz, prompting the user for answers and tracking the score.
     */
    public void startQuiz() {
        if (questions.isEmpty()) {
            System.out.println("Quiz is empty. Cannot start.");
            return;
        }

        System.out.println("\n--- Starting Quiz: " + name + " ---");
        Scanner scanner = new Scanner(System.in);
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + " of " + questions.size());
            q.promptQuestion();

            String input = scanner.nextLine();

            if (q.verifyAnswer(input)) {
                System.out.println("✅ Correct!");
                correctCount++;
            } else {
                System.out.println("❌ Incorrect. The correct answer was: " + q.getCorrectAnswer());
            }
        }

        this.currentScore = correctCount;
        this.mostRecentScore = correctCount;
        this.scoreHistory.add(correctCount);

        System.out.println("\n--- Quiz Finished! ---");
        System.out.println("You scored " + correctCount + " out of " + questions.size() + ".");
        System.out.println("Your score history: " + scoreHistory);
    }

    // --- Serialization/Deserialization Methods ---

    /**
     * Saves the entire Quiz object to a binary file using native Java serialization.
     * @param fileName The path/name of the file (e.g., "myquiz.ser").
     * @return true if save successful, false otherwise.
     */
    public boolean saveQuiz(String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            
            objectOut.writeObject(this);
            return true;
        } catch (IOException i) {
            System.err.println("Error saving quiz: " + i.getMessage());
            return false;
        }
    }

    /**
     * Loads a Quiz object from a binary file using native Java deserialization.
     * @param fileName The path/name of the file (e.g., "myquiz.ser").
     * @return The loaded Quiz object, or null if loading fails.
     */
    public static Quiz loadQuiz(String fileName) {
        Quiz quiz = null;
        // Use a separate check for FileNotFoundException for better initial user feedback
        File file = new File(fileName);
        if (!file.exists()) {
            return null; // File doesn't exist, this is fine for the first run
        }
        
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            quiz = (Quiz) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading quiz: " + e.getMessage());
            return null;
        }
        return quiz;
    }
}
