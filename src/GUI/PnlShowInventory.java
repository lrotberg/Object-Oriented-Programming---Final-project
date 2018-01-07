package GUI;

import java.awt.*;
import java.io.FileNotFoundException;

import javax.swing.*;

import BL.TestAdminForGUI;

public class PnlShowInventory extends JPanel implements Updateable {

	private SpringLayout layShowInventory;
	private JLabel lbltitle;
	private JScrollPane scpTextScroller;
	private JTextPane txtquestions;

	public PnlShowInventory(TestAdminForGUI testAdmin) {

		layShowInventory = new SpringLayout();
		lbltitle = new JLabel("Here is the current questions in the inventory:");

		txtquestions = new JTextPane();
		update(testAdmin);
		txtquestions.setPreferredSize(new Dimension(700, 500));

		scpTextScroller = new JScrollPane(txtquestions);
		scpTextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(lbltitle);
		// add(txtquestions);
		add(scpTextScroller);

		layShowInventory.putConstraint(SpringLayout.WEST, lbltitle, 10, SpringLayout.WEST, this);
		layShowInventory.putConstraint(SpringLayout.NORTH, lbltitle, 10, SpringLayout.NORTH, this);

		layShowInventory.putConstraint(SpringLayout.WEST, scpTextScroller, 0, SpringLayout.WEST, lbltitle);
		layShowInventory.putConstraint(SpringLayout.EAST, scpTextScroller, -25, SpringLayout.EAST, this);
		layShowInventory.putConstraint(SpringLayout.NORTH, scpTextScroller, 25, SpringLayout.SOUTH, lbltitle);
		layShowInventory.putConstraint(SpringLayout.SOUTH, scpTextScroller, -25, SpringLayout.SOUTH, this);

		setLayout(layShowInventory);
		setVisible(true);

	} // public PnlShowInventory()

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();
	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		StringBuilder tempInventory = new StringBuilder("");
		for (int i = 0; i < testAdmin.getQuestionsVector().size(); i++) {
			tempInventory.append(testAdmin.getQuestionsVector().elementAt(i).toStringWithCorretness());
		} // for
		txtquestions.setText(tempInventory.toString());
	}

} // public class PnlShowInventory
