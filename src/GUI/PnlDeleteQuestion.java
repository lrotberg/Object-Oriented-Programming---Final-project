package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.*;

import BL.TestAdminForGUI;

public class PnlDeleteQuestion extends JPanel implements Updateable {

	private SpringLayout deleteQuestionLayout;
	private JLabel lblWarning;
	private JLabel lblQuestionChoice;
	private JComboBox<String> cmbQuestionChoice;
	private JTextPane txpQuestion;
	private JButton btnDelete;
	private int questionChoice;

	public PnlDeleteQuestion(TestAdminForGUI testAdmin) {

		deleteQuestionLayout = new SpringLayout();
		lblWarning = new JLabel("Warning! Deleting a question will delete all it's answers as well!");
		lblQuestionChoice = new JLabel("Please choose a question to delete:");

		cmbQuestionChoice = new JComboBox<String>();
		updateInventory(testAdmin);
		cmbQuestionChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbQuestionChoice.getSelectedIndex() >= 0) {
					questionChoice = cmbQuestionChoice.getSelectedIndex();
					txpQuestion.setText(testAdmin.getQuestionsVector().elementAt(questionChoice).toStringWithCorretness());
					btnDelete.setEnabled(true);
				}
			}
		});

		txpQuestion = new JTextPane();
		txpQuestion.setPreferredSize(new Dimension(0, 200));

		btnDelete = new JButton("Delete question");
		btnDelete.setEnabled(false);

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbQuestionChoice.getSelectedIndex() >= 0) {
					questionChoice = cmbQuestionChoice.getSelectedIndex() + 1;
					testAdmin.deleteQuestion(questionChoice);
					updateInventory(testAdmin);
					txpQuestion.setText(testAdmin.getQuestionsVector().get(questionChoice).toString());
					JOptionPane.showMessageDialog(null, "Your question has been deleted successfully");
					updateInventory(testAdmin);
					try {
						save(testAdmin);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		add(lblWarning);
		add(lblQuestionChoice);
		add(cmbQuestionChoice);
		add(txpQuestion);
		add(btnDelete);

		deleteQuestionLayout.putConstraint(deleteQuestionLayout.WEST, lblWarning, 10, deleteQuestionLayout.WEST, this);
		deleteQuestionLayout.putConstraint(deleteQuestionLayout.NORTH, lblWarning, 10, deleteQuestionLayout.NORTH, this);

		deleteQuestionLayout.putConstraint(deleteQuestionLayout.WEST, lblQuestionChoice, 0, deleteQuestionLayout.WEST, lblWarning);
		deleteQuestionLayout.putConstraint(deleteQuestionLayout.NORTH, lblQuestionChoice, 10,
				deleteQuestionLayout.SOUTH, lblWarning);

		deleteQuestionLayout.putConstraint(deleteQuestionLayout.WEST, cmbQuestionChoice, 0, deleteQuestionLayout.WEST, lblQuestionChoice);
		deleteQuestionLayout.putConstraint(deleteQuestionLayout.NORTH, cmbQuestionChoice, 10, deleteQuestionLayout.SOUTH, lblQuestionChoice);

		deleteQuestionLayout.putConstraint(deleteQuestionLayout.WEST, txpQuestion, 0, deleteQuestionLayout.WEST, cmbQuestionChoice);
		deleteQuestionLayout.putConstraint(deleteQuestionLayout.EAST, txpQuestion, 0, deleteQuestionLayout.EAST, cmbQuestionChoice);
		deleteQuestionLayout.putConstraint(deleteQuestionLayout.NORTH, txpQuestion, 10, deleteQuestionLayout.SOUTH, cmbQuestionChoice);

		deleteQuestionLayout.putConstraint(deleteQuestionLayout.EAST, btnDelete, 0, deleteQuestionLayout.EAST, txpQuestion);
		deleteQuestionLayout.putConstraint(deleteQuestionLayout.NORTH, btnDelete, 20, deleteQuestionLayout.SOUTH, txpQuestion);

		lblWarning.setForeground(Color.RED);

		setLayout(deleteQuestionLayout);
		setVisible(true);

	}
	
	public void updateInventory(TestAdminForGUI testAdmin) {
		cmbQuestionChoice.removeAllItems();
		for (int i = 0; i < testAdmin.getNumOfQuestions(); i++) {
			cmbQuestionChoice.addItem(testAdmin.getQuestionsVector().elementAt(i).getQuestion());
		}
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();
	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		updateInventory(testAdmin);
	}

} // public class PnlDeleteQuestion extends JPanel
