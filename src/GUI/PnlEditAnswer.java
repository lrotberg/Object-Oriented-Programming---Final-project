package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import BL.*;

public class PnlEditAnswer extends JPanel implements Updateable {
	
	private SpringLayout layEditAnswer;
	private JLabel lblQuestionChoice;
	private JComboBox<String> cmbQuestionChoice;
	private JTextPane txpSelectedQuestion;
	private JScrollPane scpTextScroller;
	private JLabel lblAnswerChoice;
	private JComboBox<String> cmbAnswerChoice;
	private JLabel lblEnterAnswer;
	private JTextArea txtNewAnswer;
	private JLabel lblIsTrue;
	private JCheckBox chkIsTrue;
	private JButton btnSaveAnswer;
	private int questionChoice;
	
	public PnlEditAnswer(TestAdminForGUI testAdmin) {
		
		layEditAnswer = new SpringLayout();

		lblQuestionChoice = new JLabel("Please choose a question:");
		cmbQuestionChoice = new JComboBox<String>();
		
		cmbAnswerChoice = new JComboBox<String>();
		
		cmbQuestionChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
				if (cmbQuestionChoice.getSelectedIndex() >= 0) {
					questionChoice = cmbQuestionChoice.getSelectedIndex();

					txpSelectedQuestion.setText(testAdmin.getQuestionsVector().elementAt(questionChoice).toStringWithCorretness());

					updateAnswers(testAdmin);

					cmbAnswerChoice.setEnabled(true);
				}
			}
		});
		
		txpSelectedQuestion = new JTextPane();
		txpSelectedQuestion.setPreferredSize(new Dimension(0, 200));

		scpTextScroller = new JScrollPane(txpSelectedQuestion);
		scpTextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		lblAnswerChoice = new JLabel("Please choose an answer:");

		lblEnterAnswer = new JLabel("Enter new Answer");
		txtNewAnswer = new JTextArea("", 3, 30);
		
		txtNewAnswer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtNewAnswer.getText().equals("")) {
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
		
		btnSaveAnswer = new JButton("Save Answer");
		btnSaveAnswer.setEnabled(false);
		
		btnSaveAnswer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int questionIndex = cmbQuestionChoice.getSelectedIndex();
				int answerIndex = cmbAnswerChoice.getSelectedIndex();
				String newAnswer = txtNewAnswer.getText();
				boolean answerIsCorrect;
				
				if(chkIsTrue.isSelected() == true){
					answerIsCorrect = true;
				}else{
					answerIsCorrect = false;
				}
				
				testAdmin.getQuestionsVector().elementAt(questionIndex).updateAnswer(answerIndex, newAnswer, answerIsCorrect);
				JOptionPane.showMessageDialog(null, "Answer was Edited successfully");
				
				txtNewAnswer.setText("");
				chkIsTrue.setSelected(false);
				txpSelectedQuestion.setText(testAdmin.getQuestionsVector().get(questionIndex).toStringWithCorretness());
				
				try {
					save(testAdmin);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setLayout(layEditAnswer);
		add(lblQuestionChoice);
		add(cmbQuestionChoice);
		add(scpTextScroller);
		add(lblAnswerChoice);
		add(cmbAnswerChoice);
		add(lblEnterAnswer);
		add(txtNewAnswer);
		add(lblIsTrue);
		add(chkIsTrue);
		add(btnSaveAnswer);

		layEditAnswer.putConstraint(layEditAnswer.WEST, lblQuestionChoice, 10, layEditAnswer.WEST, this);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, lblQuestionChoice, 10, layEditAnswer.NORTH, this);

		layEditAnswer.putConstraint(layEditAnswer.WEST, cmbQuestionChoice, 0, layEditAnswer.WEST, lblQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, cmbQuestionChoice, 10, layEditAnswer.SOUTH, lblQuestionChoice);

		layEditAnswer.putConstraint(layEditAnswer.WEST, scpTextScroller, 0, layEditAnswer.WEST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.EAST, scpTextScroller, 0, layEditAnswer.EAST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, scpTextScroller, 10, layEditAnswer.SOUTH, cmbQuestionChoice);
		
		layEditAnswer.putConstraint(layEditAnswer.WEST, lblAnswerChoice, 0, layEditAnswer.WEST, lblQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, lblAnswerChoice, 10, layEditAnswer.SOUTH, scpTextScroller);
		
		layEditAnswer.putConstraint(layEditAnswer.EAST, cmbAnswerChoice, 0, layEditAnswer.EAST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.WEST, cmbAnswerChoice, 0, layEditAnswer.WEST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, cmbAnswerChoice, 10, layEditAnswer.SOUTH, lblAnswerChoice);
		
		layEditAnswer.putConstraint(layEditAnswer.WEST, lblEnterAnswer, 0, layEditAnswer.WEST, lblQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, lblEnterAnswer, 10, layEditAnswer.SOUTH, cmbAnswerChoice);
		
		layEditAnswer.putConstraint(layEditAnswer.WEST, txtNewAnswer, 0, layEditAnswer.WEST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.EAST, txtNewAnswer, 0, layEditAnswer.EAST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, txtNewAnswer, 10, layEditAnswer.SOUTH, lblEnterAnswer);
		
		layEditAnswer.putConstraint(layEditAnswer.WEST, lblIsTrue, 0, layEditAnswer.WEST, lblQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, lblIsTrue, 10, layEditAnswer.SOUTH, txtNewAnswer);
		
		layEditAnswer.putConstraint(layEditAnswer.WEST, chkIsTrue, 5, layEditAnswer.EAST, lblIsTrue);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, chkIsTrue, 0, layEditAnswer.NORTH, lblIsTrue);
		
		layEditAnswer.putConstraint(layEditAnswer.EAST, btnSaveAnswer, 0, layEditAnswer.EAST, cmbQuestionChoice);
		layEditAnswer.putConstraint(layEditAnswer.NORTH, btnSaveAnswer, 20, layEditAnswer.SOUTH, lblIsTrue);

		setVisible(true);
		
	}
	
	public void updateQuestions(TestAdminForGUI testAdmin) {
		cmbQuestionChoice.removeAllItems();
		for (int i = 0; i < testAdmin.getQuestionsVector().size(); i++) {
			cmbQuestionChoice.addItem(testAdmin.getQuestionsVector().elementAt(i).getQuestion());
		}
	}
	
	public void updateAnswers(TestAdminForGUI testAdmin) {
		if(questionChoice >= 0) {
			cmbAnswerChoice.removeAllItems();

			for (int i = 0; i < testAdmin.getQuestionsVector().elementAt(questionChoice).getNumOfAnswers(); i++) {
				cmbAnswerChoice.addItem(testAdmin.getQuestionsVector().elementAt(questionChoice).getAnswer(i).getAnswer());
			} // for
		} // if
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();	
	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		updateQuestions(testAdmin);
	}


} // EditAnswerGUI
