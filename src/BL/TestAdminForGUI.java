package BL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class TestAdminForGUI {

	private NewTest test;
	private Vector<NewQuestion> allQuestions;
	private int numOfQuestions;
	public static final int MAX_QUESTIONS = 20;

	public TestAdminForGUI(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		numOfQuestions = s.nextInt();
		// clean the buffer
		s.nextLine();

		allQuestions = new Vector<NewQuestion>(MAX_QUESTIONS);
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions.add(new NewQuestion(s));
		}
		s.close();
	} // TestAdminForGUI(Scanner s)

	public int getNumOfQuestions() {
		return allQuestions.size();
	} // int getNumOfQuestions()

	public int getTotalNumOfAnswers() {
		int numOfAnswers = 0;

		for (int i = 0; i < allQuestions.size(); i++) {
			numOfAnswers += allQuestions.elementAt(i).getNumOfAnswers();
		}
		return numOfAnswers;
	} // int getTotalNumOfAnswers();

	public boolean addQuestion(NewQuestion newQuestion) {
		if (allQuestions.size() < MAX_QUESTIONS) {
			allQuestions.add(newQuestion);
			numOfQuestions = allQuestions.size();
			return true;
		} // if
		return false;
	}// boolean addQuestion(NewQuestion newQuestion)

	public Vector<NewQuestion> getQuestionsVector() {
		return allQuestions;
	}

	public void showAllQuestions() {
		for (int i = 0; i < numOfQuestions; i++) {
			System.out.println((i + 1) + ") " + allQuestions.elementAt(i).toStringWithCorretness());
			System.out.println();
		}
	} // void printAllQuestions()

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numOfQuestions; i++) {
			sb.append((i + 1) + ") " + allQuestions.elementAt(i).toStringWithCorretness());
		}
		return sb.toString();
	} // void printAllQuestions()

	public void printToFile(PrintWriter pw) {
		pw.println(numOfQuestions);
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions.elementAt(i).printToFileWithCorrectness(pw);
		}
	} // void printToFile(PrintWriter pw)

	public void printToFile() throws FileNotFoundException {
		File f = new File("Inventory.txt");
		PrintWriter pw = new PrintWriter(f);
		pw.println(numOfQuestions);
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions.elementAt(i).printToFileWithCorrectness(pw);
			// pw.println();
		}
		pw.close();
	} // void printToFile() throws FileNotFoundException

	public String getTestName() {
		return test.getName();
	} // String getTestName()

	public NewTest manualTest(int testNumOfQuesetions, int howMannyAnswers, int[] questionChoices,
			int[][] answerChoices) {
		this.test = new NewTest(testNumOfQuesetions);

		for (int i = 0; i < testNumOfQuesetions; i++) {
			NewQuestion questionTemp = new NewQuestion(allQuestions.elementAt(questionChoices[i] - 1).getQuestion());
			for (int j = 0; j < howMannyAnswers; j++) {
				Answer answerTemp = new Answer(
						allQuestions.elementAt(questionChoices[i] - 1).getAnswer(answerChoices[i][j] - 1).getAnswer(),
						allQuestions.elementAt(questionChoices[i] - 1).getAnswer(answerChoices[i][j] - 1)
								.getCorrectness());
				questionTemp.addAnswer(answerTemp);
			}

			Answer ans1 = new Answer("All answers are false");
			if (questionTemp.checkIfAllAnswersAreFalse() == true) {
				ans1.setCorrectness(true);
			} else {
				ans1.setCorrectness(false);
			}
			questionTemp.addAnswer(ans1);

			Answer ans2 = new Answer("There's more then 1 true answers");
			if (questionTemp.checkIfMoreThenOneAnswerIsTrue() == true) {
				ans2.setCorrectness(true);
			} else {
				ans2.setCorrectness(false);
			}
			questionTemp.addAnswer(ans2);

			test.addQuestion(questionTemp);
		}
		return test;
	} // public NewTest manualTest()

	public NewTest automaticTest(int testNumOfQuestions) {
		this.test = new NewTest(testNumOfQuestions);
		Random r = new Random();

		int[] randomQuestionNumbers = new int[testNumOfQuestions];
		for (int i = 0; i < testNumOfQuestions; i++) {
			int randomQuestion = r.nextInt(numOfQuestions);
			randomQuestionNumbers[i] = randomQuestion;

			for (int j = 0; j < i; j++) {
				while (randomQuestionNumbers[i] == randomQuestionNumbers[j]) {
					randomQuestion = r.nextInt(numOfQuestions);
					randomQuestionNumbers[i] = randomQuestion;
				} // while
			} // for j
		} // for i

		final int NUM_OF_ANSWERS_IN_AUTOMATIC_TEST = 4;
		int[][] randomAnswersNumbers = new int[testNumOfQuestions][NUM_OF_ANSWERS_IN_AUTOMATIC_TEST];

		for (int i = 0; i < testNumOfQuestions; i++) {
			int[] randomAnswersHelper = new int[NUM_OF_ANSWERS_IN_AUTOMATIC_TEST];

			for (int j = 0; j < NUM_OF_ANSWERS_IN_AUTOMATIC_TEST; j++) {
				
				int randomAnswer = r.nextInt(allQuestions.elementAt(randomQuestionNumbers[i]).getNumOfAnswers());
				
				randomAnswersHelper[j] = randomAnswer;
				randomAnswersNumbers[i][j] = randomAnswersHelper[j];

				for (int k = 0; k < j; k++) {
					while (randomAnswersHelper[k] == randomAnswersHelper[j]) {
						randomAnswer = r.nextInt(allQuestions.elementAt(randomQuestionNumbers[i]).getNumOfAnswers());
						randomAnswersHelper[j] = randomAnswer;
						randomAnswersNumbers[i][j] = randomAnswersHelper[j];
					} // while
				} // for k
			} // for j
		} // for i

		for (int i = 0; i < testNumOfQuestions; i++) {
			
			NewQuestion questionTemp = new NewQuestion(allQuestions.elementAt(randomQuestionNumbers[i]).getQuestion());
			
			for (int j = 0; j < NUM_OF_ANSWERS_IN_AUTOMATIC_TEST; j++) {
				
				String tempAnswerText = allQuestions.elementAt(randomQuestionNumbers[i]).getAnswer(randomAnswersNumbers[i][j]).getAnswer() ;
				boolean tempAnswerCorrectness = allQuestions.elementAt(randomQuestionNumbers[i]).getAnswer(randomAnswersNumbers[i][j]).getCorrectness();
				
				Answer answerTemp = new Answer(tempAnswerText, tempAnswerCorrectness);
				questionTemp.addAnswer(answerTemp);
			}

			Answer ans1 = new Answer("All answers are false");
			if (questionTemp.checkIfAllAnswersAreFalse() == true) {
				ans1.setCorrectness(true);
			} else {
				ans1.setCorrectness(false);
			}
			questionTemp.addAnswer(ans1);

			test.addQuestion(questionTemp);
		}

		return test;
	} // Test automaticTest()

	public void updateQuestion(int numOfQuestionChoice, String newQuestion) {
		allQuestions.elementAt(numOfQuestionChoice).updateQuestion(newQuestion);
	} // void updateQuestion(int numOfQuestionChoice, String newQuestion)

	public void printQuestionsOnly() {
		for (int i = 0; i < numOfQuestions; i++) {
			System.out.println((i + 1) + ") " + allQuestions.elementAt(i).getQuestion());
		}
	} // void printQuestionsOnly()

	public void printAnswersOnly(int qustionToAddAnswerNumber) {
		allQuestions.elementAt(qustionToAddAnswerNumber - 1).printAnswersOnly();
	} // void printAnswersOnly(int qustionToAddAnswerNumber)

	public void printAnswersAndCorrecness(int qustionToAddAnswerNumber) {
		allQuestions.elementAt(qustionToAddAnswerNumber - 1).printAnswersAndCorrectness();
	} // void printAnswersOnly(int qustionToAddAnswerNumber)

	public void deleteQuestion(int numOfQuestionChoice) {
		allQuestions.remove(numOfQuestionChoice - 1);
		numOfQuestions = allQuestions.size();
	} // void deleteAnswer(Answer ans)

	public boolean addAnswerToExistingQuestion(int questionToAddAnswerNumber, Answer answerTemp) {
		if (allQuestions.elementAt(questionToAddAnswerNumber - 1).addAnswer(answerTemp)) {
			return true;
		}
		return false;
	} // boolean addAnswerToExistingQuestion(int qustionToAddAnswerNumber,
		// Answer answerTemp)

	public void updateAnswer(int qustionToUpdateAnswerNumber, int answerChoice, String temp, boolean bTemp) {
		allQuestions.elementAt(qustionToUpdateAnswerNumber - 1).updateAnswer(answerChoice - 1, temp, bTemp);
	} // void updateAnswer(int qustionToUpdateAnswerNumber, int answerChoice,
		// String temp)

	public void deleteAnswer(int questionToDeleteAnswerNumber, int answerDeleteChoice) {
		allQuestions.elementAt(questionToDeleteAnswerNumber).deleteAnswer(answerDeleteChoice);
	} // void deleteAnswer(int qustionToDeleteAnswerNumber, int
		// answerDeleteChoice)

	public int getNumOfAnswers(int questionNum) {
		return allQuestions.elementAt(questionNum - 1).getNumOfAnswers();
	}

	/*
	 * public int getQuestionIndex (NewQuestion newQuestion) { for (int i = 0 ;
	 * i < allQuestions.size() ; i++) {
	 * if(allQuestions.elementAt(i).equals(newQuestion)) { return i; } } return
	 * null; }
	 */

} // public class TestAdminForGUI