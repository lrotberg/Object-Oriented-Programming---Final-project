package GUI;

import java.io.FileNotFoundException;

import BL.TestAdminForGUI;

public interface Updateable {


	void save(TestAdminForGUI testAdmin) throws FileNotFoundException;

	void update(TestAdminForGUI testAdmin);

}
