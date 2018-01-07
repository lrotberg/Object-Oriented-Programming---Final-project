package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;

import BL.NewTest;
import BL.TestAdminForGUI;

public class PnlAutomaticTestCreation extends JPanel implements Updateable {

	private SpringLayout layAutomaticTest;
	private JLabel lblNumOfQuestions;
	private JComboBox<Integer> cmbNumOfQuestons;
	private JButton btnCreateTest;
	private JLabel lblTestPreview;
	private JTextPane txpShowTest;
	private JScrollPane scpTextScroller;
	private JButton btnSaveTest;
	private NewTest test;

	public PnlAutomaticTestCreation(TestAdminForGUI testAdmin) {

		layAutomaticTest = new SpringLayout();
		lblNumOfQuestions = new JLabel("Please choose the number of questions you want in the test:");

		cmbNumOfQuestons = new JComboBox<Integer>();

		update(testAdmin);

		btnCreateTest = new JButton("Create test");
		
		btnCreateTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txpShowTest.setEnabled(true);
				btnSaveTest.setEnabled(true);
				
				test =  testAdmin.automaticTest(cmbNumOfQuestons.getSelectedIndex()+1);
				
				txpShowTest.setText(test.toStringWithSolutions());
				
			}
		});
		
		lblTestPreview = new JLabel("Test Preview");

		txpShowTest = new JTextPane();
		txpShowTest.setPreferredSize(new Dimension(600, 300));
		txpShowTest.setEnabled(false);

		scpTextScroller = new JScrollPane(txpShowTest);
		scpTextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		btnSaveTest = new JButton("Save Test");
		btnSaveTest.setEnabled(false);
		
		btnSaveTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					save(testAdmin);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "The test and it's solution has been created");
			}
		});

		setLayout(layAutomaticTest);
		add(lblNumOfQuestions);
		add(cmbNumOfQuestons);
		add(btnCreateTest);
		add(lblTestPreview);
		add(scpTextScroller);
		add(btnSaveTest);

		layAutomaticTest.putConstraint(layAutomaticTest.WEST, lblNumOfQuestions, 10, layAutomaticTest.WEST, this);
		layAutomaticTest.putConstraint(layAutomaticTest.NORTH, lblNumOfQuestions, 10, layAutomaticTest.NORTH, this);

		layAutomaticTest.putConstraint(layAutomaticTest.WEST, cmbNumOfQuestons, 40, layAutomaticTest.EAST,
				lblNumOfQuestions);
		layAutomaticTest.putConstraint(layAutomaticTest.NORTH, cmbNumOfQuestons, 0, layAutomaticTest.NORTH,
				lblNumOfQuestions);

		layAutomaticTest.putConstraint(layAutomaticTest.WEST, lblTestPreview, 0, layAutomaticTest.WEST,
				lblNumOfQuestions);
		layAutomaticTest.putConstraint(layAutomaticTest.NORTH, lblTestPreview, 10, layAutomaticTest.SOUTH,
				cmbNumOfQuestons);

		layAutomaticTest.putConstraint(layAutomaticTest.WEST, scpTextScroller, 0, layAutomaticTest.WEST,
				lblNumOfQuestions);
		layAutomaticTest.putConstraint(layAutomaticTest.NORTH, scpTextScroller, 10, layAutomaticTest.SOUTH,
				lblTestPreview);

		layAutomaticTest.putConstraint(layAutomaticTest.EAST, btnCreateTest, 0, layAutomaticTest.EAST, scpTextScroller);
		layAutomaticTest.putConstraint(layAutomaticTest.NORTH, btnCreateTest, 0, layAutomaticTest.NORTH,
				lblNumOfQuestions);

		layAutomaticTest.putConstraint(layAutomaticTest.EAST, btnSaveTest, 0, layAutomaticTest.EAST, scpTextScroller);
		layAutomaticTest.putConstraint(layAutomaticTest.NORTH, btnSaveTest, 10, layAutomaticTest.SOUTH,
				scpTextScroller);

		setVisible(true);
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		StringBuilder testTemp = new StringBuilder(test.getName());
		testTemp.append(".txt");
		File f = new File(testTemp.toString());
		PrintWriter pw = new PrintWriter(f.getName());
		pw.println(test.toString());
		pw.close();

		StringBuilder testSolutionTemp = new StringBuilder(test.getName());
		testSolutionTemp.append(" - Solution.txt");
		File f2 = new File(testSolutionTemp.toString());
		PrintWriter pw2 = new PrintWriter(f2.getName());
		pw2.println(test.toStringWithSolutions());
		pw2.close();
	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		cmbNumOfQuestons.removeAllItems();
		
		for (int i = 1; i <= testAdmin.getNumOfQuestions(); i++) {
			cmbNumOfQuestons.addItem(i);
		}
	}

}
