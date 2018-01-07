package BL;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class NewQuestion {

	private String question;
	private Vector<Answer> answers;
	public static final int MAX_ANSWERS = 10;
	private int numOfAnswers;

	public NewQuestion(String question) {
		this.question = question;
		answers = new Vector<Answer>(MAX_ANSWERS);
		numOfAnswers = answers.size();
	} // NewQuestion(String question)

	public NewQuestion(Scanner s) {
		question = s.nextLine();
		numOfAnswers = s.nextInt();
		answers = new Vector<Answer>(MAX_ANSWERS);
		// clean the buffer
		s.nextLine();

		for (int i = 0; i < numOfAnswers; i++) {
			answers.add(new Answer(s));
		} // for
	} // NewQuestion(Scanner s)

	public NewQuestion(String question, Answer[] answersArr) {
		this.question = question;
		numOfAnswers = answersArr.length;
		answers = new Vector<Answer>(MAX_ANSWERS);

		for (int i = 0; i < numOfAnswers; i++) {
			answers.add(answersArr[i]);
		} // for
	} // NewQuestion(String question, Answer[] answersArr)

	public NewQuestion(String question, Vector<Answer> answersVector) {
		this.question = question;
		numOfAnswers = answersVector.size();
		answers = new Vector<Answer>(answersVector);
	} // NewQuestion(String question, Vector<Answer> answersVector)

	public NewQuestion(NewQuestion newQuestion) {
		question = newQuestion.question;
		numOfAnswers = newQuestion.numOfAnswers;
		answers = new Vector<Answer>(newQuestion.answers);
	}

	public void printToFile(PrintWriter pw) {
		pw.println(question);
		pw.println(numOfAnswers);
		for (int i = 0; i < numOfAnswers; i++) {
			answers.elementAt(i).printToFile(pw);
		} // for
	} // printToFile(PrintWriter pw)

	public void printToFileWithCorrectness(PrintWriter pw) {
		pw.println(question);
		pw.println(numOfAnswers);
		for (int i = 0; i < numOfAnswers; i++) {
			answers.elementAt(i).printToFileWithCorrectness(pw);
		} // for
	} // printToFileWithCorrectness(PrintWriter pw)

	public String getQuestion() {
		return question;
	}// String getQuestionText()

	public int getNumOfAnswers() {
		return answers.size();
	}// int getNumOfAnswers()

	public boolean addAnswer(Answer answer) {
		if (answers.size() < MAX_ANSWERS) {
			answers.add(answer);
			numOfAnswers = answers.size();
			return true;
		}
		return false;
	} // boolean addAnswer(Answer answer)

	public void updateQuestion(String newQuestion) {
		question = newQuestion;
	}// void updateQuestionText(String newQuestion)

	public boolean deleteAnswer(Answer answer) {
		for (int i = 0; i < numOfAnswers; i++) {
			if (answers.elementAt(i).equals(answer)) {
				answers.remove(i);
				numOfAnswers = answers.size();
				return true;
			} // if
		} // for
		return false;
	} // boolean deleteAnswer(Answer ans)

	public boolean deleteAnswer(int answerIndex) {
		if (numOfAnswers > 0) {
			answers.remove(answerIndex);
			numOfAnswers = answers.size();
			return true;
		} // if
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(getQuestion());
		// + ", there are " + getNumOfAnswers() + " answers, and the answers are: "
		for (int i = 0; i < numOfAnswers; i++) {
			sb.append("\n   " + (i + 1) + ") " + answers.elementAt(i).getAnswer());
		}

		return sb.toString();
	}// String toString()

	public String toStringWithCorretness() {
		StringBuilder sb = new StringBuilder(getQuestion());
		 // + ", there are " + getNumOfAnswers() + " answers, and the answers are: ");
		for (int i = 0; i < numOfAnswers; i++) {
			sb.append("\n   " + (i + 1) + ") " + answers.elementAt(i).toString());
		}
		sb.append("\n");

		return sb.toString();
	}// String toStringNoCorretness()

	public void printAnswersOnly() {
		for (int i = 0; i < numOfAnswers; i++) {
			System.out.println((i + 1) + ") " + answers.elementAt(i).getAnswer());
		}
	} // void printAnswersonly()

	public void printAnswersAndCorrectness() {
		for (int i = 0; i < numOfAnswers; i++) {
			System.out.println(
					(i + 1) + ") " + answers.elementAt(i).getAnswer() + " - " + answers.elementAt(i).getCorrectness());
		}
	} // void printAnswersonly()

	public void updateAnswer(int answerChoice, String newAnswer, boolean bTemp) {
		answers.elementAt(answerChoice).updateAnswer(newAnswer);
		answers.elementAt(answerChoice).setCorrectness(bTemp);
	} // void updateAnswer(int answerChoice, String newAnswer)

	public Answer getAnswer(int answerNumber) {
		return answers.elementAt(answerNumber);
	} // Answer getAnswer(int answerNumber)

	public boolean checkIfAllAnswersAreFalse() {
		for (int i = 0; i < numOfAnswers; i++) {
			if (answers.elementAt(i).getCorrectness() == true) {
				return false;
			} // if
		} // for
		return true;
	} // boolean checkIfAllAnswersAreFalse()

	public boolean checkIfMoreThenOneAnswerIsTrue() {
		int counter = 0;

		for (int i = 0; i < numOfAnswers; i++) {
			if (answers.elementAt(i).getCorrectness() == true) {
				counter++;
			} // if
		} // for

		if (counter > 1) {
			return true;
		} // if

		return false;
	} // boolean checkIfMoreThenOneAnswerIsTrue()

	public Vector<Answer> getAnswerVector() {
		return answers;
	} // Vector<Answer> getAnswerVector()

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	} // int hashCode()

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NewQuestion)) {
			return false;
		}

		NewQuestion temp = (NewQuestion) obj;
		return question == temp.question && answers == temp.answers;
	} // boolean equals(Object obj)

} // public Class NewQuestion
