import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CCheckers {

    private static JFrame frame;
    private static GUI6players disp;


    CCheckers(int n) {


        frame = new JFrame("Chinese Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //sets fullscreen
    /*    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
*/
        frame.setSize(800, 800);

        //our GUI + physics
        disp = new GUI6players(n);

       /*
       @    We set a GridLayout to display our sliders at the right location on the left of the screen
       @    A larger number of columns will result with thinner sliders
        */
        disp.setLayout(new GridLayout(0, 10));


        //ads the GUI to the frame
        frame.setContentPane(disp);
        frame.setVisible(true);
    }

    public static void changeScene(){
        // disp = new DisplayEarth(true, 2000);
        disp.setLayout(new GridLayout(0, 10));

        //ads the GUI to the frame
        frame.setContentPane(disp);
        frame.setVisible(true);
    }

}
