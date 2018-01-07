package BL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class TestAdmin {

	private Test test;
	private Question[] allQuestions;
	private int numOfQuestions;
	public static final int MAX_QUESTIONS = 20;

	public TestAdmin(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		numOfQuestions = s.nextInt();
		// clean the buffer
		s.nextLine();

		allQuestions = new Question[MAX_QUESTIONS];
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions[i] = new Question(s);
		}
		s.close();
	} // TestAdmin(Scanner s)

	public int getNumOfQuestions() {
		return numOfQuestions;
	} // int getNumOfQuestions()

	public boolean addQuestion(Question question) {
		if (checkIfThereIsPlaceForQuestion(question) == true) {
			allQuestions[numOfQuestions++] = question;
			return true;
		} // if
		return false;
	}// boolean addAnswer(Question que)

	public boolean checkIfThereIsPlaceForQuestion(Question que) {
		if (numOfQuestions < MAX_QUESTIONS) {
			return true;
		} // if
		return false;
	}// boolean checkIfThereIsPlaceForQuestion(Question que)

	public void printAllQuestions() {
		for (int i = 0; i < numOfQuestions; i++) {
			System.out.println((i + 1) + ") " + allQuestions[i].toStringWithCorretness());
		}
	} // void printAllQuestions()

	public void printToFile(PrintWriter pw) {
		pw.println(numOfQuestions);
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions[i].printToFileWithCorrectness(pw);
		}
	} // void printToFile(PrintWriter pw)

	public String getTestName() {
		return test.getName();
	} // String getTestName()

	public Test manualTest(int testNumOfQuesetions, int howMannyAnswers, int[] questionChoices, int[][] answerChoices) {
		this.test = new Test(testNumOfQuesetions);

		for (int i = 0; i < testNumOfQuesetions; i++) {
			Question questionTemp = new Question(allQuestions[questionChoices[i] - 1].getQuestion());
			for (int j = 0; j < howMannyAnswers; j++) {
				Answer answerTemp = new Answer(
						allQuestions[questionChoices[i] - 1].getAnswer(answerChoices[i][j] - 1).getAnswer(),
						allQuestions[questionChoices[i] - 1].getAnswer(answerChoices[i][j] - 1).getCorrectness());
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
	} // Test manualTest()

	public Test automaticTest(int testNumOfQuesetions) {
		this.test = new Test(testNumOfQuesetions);
		Random r = new Random();

		int[] randomQuestionNumbers = new int[testNumOfQuesetions];
		for (int i = 0; i < testNumOfQuesetions; i++) {
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
		int[][] randomAnswersNumbers = new int[testNumOfQuesetions][NUM_OF_ANSWERS_IN_AUTOMATIC_TEST];

		for (int i = 0; i < testNumOfQuesetions; i++) {
			int[] randomAnswersHelper = new int[NUM_OF_ANSWERS_IN_AUTOMATIC_TEST];

			for (int j = 0; j < NUM_OF_ANSWERS_IN_AUTOMATIC_TEST; j++) {
				int randomAnswer = r.nextInt(allQuestions[i].getNumOfAnswers());
				randomAnswersHelper[j] = randomAnswer;
				randomAnswersNumbers[i][j] = randomAnswersHelper[j];

				for (int k = 0; k < j; k++) {
					while (randomAnswersHelper[k] == randomAnswersHelper[j]) {
						randomAnswer = r.nextInt(allQuestions[i].getNumOfAnswers());
						randomAnswersHelper[j] = randomAnswer;
						randomAnswersNumbers[i][j] = randomAnswersHelper[j];
					} // while
				} // for k
			} // for j
		} // for i

		for (int i = 0; i < testNumOfQuesetions; i++) {
			Question questionTemp = new Question(allQuestions[randomQuestionNumbers[i]].getQuestion());
			for (int j = 0; j < NUM_OF_ANSWERS_IN_AUTOMATIC_TEST; j++) {
				Answer answerTemp = new Answer(
						allQuestions[randomQuestionNumbers[i]].getAnswer(randomAnswersNumbers[i][j]).getAnswer(),
						allQuestions[randomQuestionNumbers[i]].getAnswer(randomAnswersNumbers[i][j]).getCorrectness());
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

	public boolean checkIfThereIsAQuestion(int numOfQuestionChoice) {
		if ((numOfQuestionChoice - 1) >= numOfQuestions || (numOfQuestionChoice - 1) < 0) {
			return false;
		}
		return true;
	} // boolean checkIfThereIsAQuestion(int numOfQuestionChoice)

	public void updateQuestion(int numOfQuestionChoice, String newQuestion) {
		allQuestions[numOfQuestionChoice - 1].updateQuestion(newQuestion);
	} // void updateQuestion(int numOfQuestionChoice, String newQuestion)

	public void printQuestionsOnly() {
		for (int i = 0; i < numOfQuestions; i++) {
			System.out.println((i + 1) + ") " + allQuestions[i].getQuestion());
		}
	} // void printQuestionsOnly()

	public void printAnswersOnly(int qustionToAddAnswerNumber) {
		allQuestions[qustionToAddAnswerNumber - 1].printAnswersOnly();
	} // void printAnswersOnly(int qustionToAddAnswerNumber)

	public void printAnswersAndCorrecness(int qustionToAddAnswerNumber) {
		allQuestions[qustionToAddAnswerNumber - 1].printAnswersAndCorrectness();
	} // void printAnswersOnly(int qustionToAddAnswerNumber)

	public void deleteQuestion(int numOfQuestionChoice) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equalsIgnoreCase(allQuestions[numOfQuestionChoice - 1].getQuestion())) {
				Question temp = allQuestions[--numOfQuestions];
				allQuestions[i] = temp;
				allQuestions[numOfQuestions] = null;
			} // if
		} // for
	} // void deleteAnswer(Answer ans)

	public boolean addAnswerToExistingQuestion(int qustionToAddAnswerNumber, Answer answerTemp) {
		if (allQuestions[qustionToAddAnswerNumber - 1].addAnswer(answerTemp)) {
			return true;
		}
		return false;
	} // boolean addAnswerToExistingQuestion(int qustionToAddAnswerNumber,
		// Answer answerTemp)

	public boolean checkIfThereIsAnAnswer(int qustionToUpdateAnswerNumber) {
		if (qustionToUpdateAnswerNumber - 1 < 0
				|| qustionToUpdateAnswerNumber - 1 > allQuestions[qustionToUpdateAnswerNumber - 1].getNumOfAnswers()) {
			return false;
		}
		return true;
	} // boolean checkIfThereIsAnAnswer(int qustionToUpdateAnswerNumber)

	public void updateAnswer(int qustionToUpdateAnswerNumber, int answerChoice, String temp, boolean bTemp) {
		allQuestions[qustionToUpdateAnswerNumber - 1].updateAnswer(answerChoice - 1, temp, bTemp);
	} // void updateAnswer(int qustionToUpdateAnswerNumber, int answerChoice,
		// String temp)

	public void deleteAnswer(int qustionToDeleteAnswerNumber, int answerDeleteChoice) {
		allQuestions[qustionToDeleteAnswerNumber - 1]
				.deleteAnswer(allQuestions[qustionToDeleteAnswerNumber - 1].getAnswer(answerDeleteChoice - 1));
	} // void deleteAnswer(int qustionToDeleteAnswerNumber, int

	// answerDeleteChoice)

	public int getNumOfAnswers(int questionNum) {
		return allQuestions[questionNum - 1].getNumOfAnswers();
	}

}// TestAdmin class
