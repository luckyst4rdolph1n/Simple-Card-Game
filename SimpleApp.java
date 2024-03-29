/** The SimpleApp class generates the GUI from the SimpleGUI class through the main method
    @author Shamika Anne E. Sawalha (235724)
    @version November 16, 2023
**/

/*
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.

I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.

If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
*/

public class SimpleApp {
    
    /**
        generates a GUI for the user
        @param args commandline arguments

    **/

    public static void main(String[] args) {
        SimpleGUI gui = new SimpleGUI(800, 600);
        gui.setUpGUI();
        gui.gameListeners();
    }
}
