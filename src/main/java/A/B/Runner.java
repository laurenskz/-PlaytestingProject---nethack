package A.B;


import javax.swing.JFrame;

public class Runner {
    public static void main(String[] args) {

        Screen sc = new Screen(new NethackConfiguration());

        JFrame frame = new JFrame("NetHack Clone");

        frame.add(sc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        sc.animate();


    }
}