package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import BL.TestAdmin;
import BL.TestAdminForGUI;
import GUI.FrmMainWindow;

public class MainProgramForGUI {

	public static void main(String[] args) throws FileNotFoundException {

		File f = new File("Inventory.txt");
		TestAdminForGUI ta = new TestAdminForGUI(f.getName());
		new FrmMainWindow(ta);
		
	} // main
} // public class MainProgramForGUI
