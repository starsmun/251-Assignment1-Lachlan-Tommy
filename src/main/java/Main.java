import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {

    private static JMenuItem newItem, openItem, saveItem, searchItem, exitItem;
    private static final JMenuBar menuBar = new JMenuBar();

    public Main() {
        super("Test Editor");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(menuBar);

        // Add a file menu with some menu items
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        newItem = new JMenuItem("New");
        newItem.addActionListener(this);
        fileMenu.add(newItem);

        openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);


        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        searchItem = new JMenuItem("Search");
        searchItem.addActionListener(this);
        viewMenu.add(searchItem);


        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        this.setSize(800, 800);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    public void actionPerformed(ActionEvent event) {
        JComponent source = (JComponent) event.getSource();
        if (source == openItem) {
            JFileChooser chooser = new JFileChooser("./");

            int retVal = chooser.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File myFile = chooser.getSelectedFile();


                try {
                    ImageIO.read(myFile);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        } else if (source == exitItem) {
            System.out.println("Quitting ...");
            System.exit(0);
        }
        super.repaint();

    }
}
