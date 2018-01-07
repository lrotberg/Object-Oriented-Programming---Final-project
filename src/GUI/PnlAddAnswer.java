package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import BL.Answer;
import BL.TestAdminForGUI;

public class PnlAddAnswer extends JPanel implements Updateable {

	private SpringLayout layAddAnswer;
	private JLabel lblQuestionChoice;
	private JComboBox<String> cmbQuestionChoice;
	private JTextPane txpSelectedQuestion;
	private JScrollPane scpTextScroller;
	private JLabel lblAddAnswer;
	private JTextArea txtAddAnswer;
	private JLabel lblIsTrue;
	private JCheckBox chkIsTrue;
	private JButton btnSaveAnswer;
	private boolean answerIsCorrect;
	private int questionChoice;
	private Answer tempAnswer;

	public PnlAddAnswer(TestAdminForGUI testAdmin) {

		layAddAnswer = new SpringLayout();
		lblQuestionChoice = new JLabel("Please choose a question:");

		cmbQuestionChoice = new JComboBox<String>();
		update(testAdmin);

		cmbQuestionChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbQuestionChoice.getSelectedIndex() >= 0) {
					txpSelectedQuestion.setEnabled(true);
					questionChoice = cmbQuestionChoice.getSelectedIndex();
					txpSelectedQuestion.setText(testAdmin.getQuestionsVector().elementAt(questionChoice).toStringWithCorretness());
					txpSelectedQuestion.setEnabled(true);
				}
			}
		});

		txpSelectedQuestion = new JTextPane();
		txpSelectedQuestion.setPreferredSize(new Dimension(0, 200));

		scpTextScroller = new JScrollPane(txpSelectedQuestion);
		scpTextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		lblAddAnswer = new JLabel("Please enter a new answer:");
		txtAddAnswer = new JTextArea("", 5, 30);

		txtAddAnswer.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (txtAddAnswer.getText().equals("")) {
					btnSaveAnswer.setEnabled(false);
					chkIsTrue.setEnabled(false);
				} else {
					btnSaveAnswer.setEnabled(true);
					chkIsTrue.setEnabled(true);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});

		lblIsTrue = new JLabel("Is true?");

		chkIsTrue = new JCheckBox();
		chkIsTrue.setEnabled(false);

		btnSaveAnswer = new JButton("Save answer");
		btnSaveAnswer.setEnabled(false);
		
		btnSaveAnswer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int questionIndex = cmbQuestionChoice.getSelectedIndex()+1;
				String newAnswer = txtAddAnswer.getText();
				
				if(chkIsTrue.isSelected() == true){
					answerIsCorrect = true;
				}else{
					answerIsCorrect = false;
				}
				
				tempAnswer = new Answer(newAnswer , answerIsCorrect);
				
				if(testAdmin.addAnswerToExistingQuestion(questionIndex, tempAnswer)){
					JOptionPane.showMessageDialog(null, "Answer was added successfully");
					txtAddAnswer.setText("");
					chkIsTrue.setSelected(false);
				}else{
					JOptionPane.showMessageDialog(null, "Question can have a max of 10 Answers, delete some and try again");
				};
				txpSelectedQuestion.setText(testAdmin.getQuestionsVector().get(questionIndex-1).toStringWithCorretness());
				
				try {
					save(testAdmin);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});

		setLayout(layAddAnswer);
		add(lblQuestionChoice);
		add(cmbQuestionChoice);
		add(scpTextScroller);
		add(lblAddAnswer);
		add(txtAddAnswer);
		add(lblIsTrue);
		add(chkIsTrue);
		add(btnSaveAnswer);

		layAddAnswer.putConstraint(layAddAnswer.WEST, lblQuestionChoice, 10, layAddAnswer.WEST, this);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, lblQuestionChoice, 10, layAddAnswer.NORTH, this);

		layAddAnswer.putConstraint(layAddAnswer.WEST, cmbQuestionChoice, 0, layAddAnswer.WEST, lblQuestionChoice);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, cmbQuestionChoice, 10, layAddAnswer.SOUTH, lblQuestionChoice);

		layAddAnswer.putConstraint(layAddAnswer.WEST, scpTextScroller, 0, layAddAnswer.WEST, cmbQuestionChoice);
		layAddAnswer.putConstraint(layAddAnswer.EAST, scpTextScroller, 0, layAddAnswer.EAST, cmbQuestionChoice);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, scpTextScroller, 10, layAddAnswer.SOUTH, cmbQuestionChoice);

		layAddAnswer.putConstraint(layAddAnswer.WEST, lblAddAnswer, 0, layAddAnswer.WEST, lblQuestionChoice);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, lblAddAnswer, 10, layAddAnswer.SOUTH, scpTextScroller);

		layAddAnswer.putConstraint(layAddAnswer.WEST, txtAddAnswer, 0, layAddAnswer.WEST, scpTextScroller);
		layAddAnswer.putConstraint(layAddAnswer.EAST, txtAddAnswer, 0, layAddAnswer.EAST, scpTextScroller);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, txtAddAnswer, 10, layAddAnswer.SOUTH, lblAddAnswer);

		layAddAnswer.putConstraint(layAddAnswer.WEST, lblIsTrue, 0, layAddAnswer.WEST, lblAddAnswer);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, lblIsTrue, 10, layAddAnswer.SOUTH, txtAddAnswer);

		layAddAnswer.putConstraint(layAddAnswer.WEST, chkIsTrue, 5, layAddAnswer.EAST, lblIsTrue);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, chkIsTrue, 0, layAddAnswer.NORTH, lblIsTrue);

		layAddAnswer.putConstraint(layAddAnswer.EAST, btnSaveAnswer, 0, layAddAnswer.EAST, scpTextScroller);
		layAddAnswer.putConstraint(layAddAnswer.NORTH, btnSaveAnswer, 20, layAddAnswer.SOUTH, chkIsTrue);

		setVisible(true);
	}

	public void updateQuestions(TestAdminForGUI testAdmin) {
		cmbQuestionChoice.removeAllItems();
		for (int i = 0; i < testAdmin.getQuestionsVector().size(); i++) {
			cmbQuestionChoice.addItem(testAdmin.getQuestionsVector().elementAt(i).getQuestion());
		}
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();

	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		updateQuestions(testAdmin);
	}

} // AddAnswerGUI class
