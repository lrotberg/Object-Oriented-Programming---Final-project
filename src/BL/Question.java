package BL;

import java.io.PrintWriter;
import java.util.Scanner;

public class Question {// Question

	private String question;
	public static final int MAX_ANSWERS = 10;
	private Answer[] answers;
	private int numOfAnswers;

	public Question(String question) {
		this.question = question;
		answers = new Answer[MAX_ANSWERS];
		numOfAnswers = 0;
	} // Question(String questionText)

	public Question(Scanner s) {
		question = s.nextLine();
		numOfAnswers = s.nextInt();
		answers = new Answer[MAX_ANSWERS];
		// clean the buffer
		s.nextLine();

		for (int i = 0; i < numOfAnswers; i++) {
			answers[i] = new Answer(s);
		} // for
	} // Question(Scanner s)

	public void printToFile(PrintWriter pw) {
		pw.println(question);
		pw.println(numOfAnswers);
		for (int i = 0; i < numOfAnswers; i++) {
			answers[i].printToFile(pw);
		} // for
	} // printToFile(PrintWriter pw)

	public void printToFileWithCorrectness(PrintWriter pw) {
		pw.println(question);
		pw.println(numOfAnswers);
		for (int i = 0; i < numOfAnswers; i++) {
			answers[i].printToFileWithCorrectness(pw);
		} // for
	} // printToFile(PrintWriter pw)

	public String getQuestion() {
		return question;
	}// String getQuestionText()

	public int getNumOfAnswers() {
		return numOfAnswers;
	}// int getNumOfAnswers()

	public boolean addAnswer(Answer answer) {
		if (checkIfThereIsPlaceForAnswer(answer)) {
			answers[numOfAnswers++] = answer;
			return true;
		}
		return false;
	}// boolean addAnswer(Answer ans)

	public boolean addAnswer(String AnswerText, boolean isTrue) {
		if (checkIfThereIsPlaceForAnswer()) {
			answers[numOfAnswers++] = new Answer(AnswerText, isTrue);
			return true;
		}
		return false;
	}

	public boolean checkIfThereIsPlaceForAnswer(Answer ans) {
		if (numOfAnswers < MAX_ANSWERS) {
			return true;
		} // if
		return false;
	}// boolean checkIfThereIsPlaceForAnswer(Answer ans)

	public boolean checkIfThereIsPlaceForAnswer() {
		if (numOfAnswers < MAX_ANSWERS) {
			return true;
		} // if
		return false;
	}// boolean checkIfThereIsPlaceForAnswer()

	public void updateQuestion(String newQuestion) {
		question = newQuestion;
	}// void updateQuestionText(String newQuestion)

	public void deleteAnswer(Answer answer) {
		for (int i = 0; i < numOfAnswers; i++) {
			if (answers[i].getAnswer().equalsIgnoreCase(answer.getAnswer())) {
				Answer temp = answers[--numOfAnswers];
				answers[i] = temp;
				answers[numOfAnswers] = null;
			} // if
		} // for
	} // void deleteAnswer(Answer ans)

	public boolean deleteAnswer(int ans) {
		if (numOfAnswers > 0) {
			Answer temp = answers[--numOfAnswers];
			answers[ans - 1] = temp;
			answers[numOfAnswers] = null;
			return true;
		} // if
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(
				getQuestion() + ", there are " + getNumOfAnswers() + " answers, and the answers are: ");
		for (int i = 0; i < numOfAnswers; i++) {
			sb.append("\n\t" + (i + 1) + ") " + answers[i].getAnswer());
		}

		return sb.toString();
	}// String toString()

	public String toStringWithCorretness() {
		StringBuilder sb = new StringBuilder(
				getQuestion() + ", there are " + getNumOfAnswers() + " answers, and the answers are: ");
		for (int i = 0; i < numOfAnswers; i++) {
			sb.append("\n\t" + (i + 1) + ") " + answers[i].toString());
		}

		return sb.toString();
	}// String toStringNoCorretness()

	public void printAnswersOnly() {
		for (int i = 0; i < numOfAnswers; i++) {
			System.out.println((i + 1) + ") " + answers[i].getAnswer());
		}
	} // void printAnswersonly()

	public void printAnswersAndCorrectness() {
		for (int i = 0; i < numOfAnswers; i++) {
			System.out.println((i + 1) + ") " + answers[i].getAnswer() + " - " + answers[i].getCorrectness());
		}
	} // void printAnswersonly()

	public void updateAnswer(int answerChoice, String newAnswer, boolean bTemp) {
		answers[answerChoice].updateAnswer(newAnswer);
		answers[answerChoice].setCorrectness(bTemp);
	} // void updateAnswer(int answerChoice, String newAnswer)

	public Answer getAnswer(int answerNumber) {
		return answers[answerNumber];
	} // Answer getAnswer(int answerNumber)

	public boolean checkIfAllAnswersAreFalse() {
		for (int i = 0; i < numOfAnswers; i++) {
			if (answers[i].getCorrectness() == true) {
				return false;
			} // if
		} // for
		return true;
	} // boolean checkIfAllAnswersAreFalse()

	public boolean checkIfMoreThenOneAnswerIsTrue() {
		int counter = 0;

		for (int i = 0; i < numOfAnswers; i++) {
			if (answers[i].getCorrectness() == true) {
				counter++;
			} // if
		} // for

		if (counter > 1) {
			return true;
		} // if

		return false;
	} // boolean checkIfMoreThenOneAnswerIsTrue()

	public Answer[] getAnswerArray() {
		return answers;
	}

}// Question class
