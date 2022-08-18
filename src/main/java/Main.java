import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {

    private static JMenuItem newItem, openItem, saveItem, printItem, searchItem, exitItem;
    private static final JMenuBar menuBar = new JMenuBar();
    private static TextFieldPanel textField = new TextFieldPanel();

    public Main() {
        super("Test Editor");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(menuBar);

        textField.setLayout(new GridLayout(0,1));
        this.add(textField);

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

        printItem = new JMenuItem("Print");
        printItem.addActionListener(this);
        fileMenu.add(printItem);

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
                textField.FileOpener(myFile);
            }
        } else if (source == newItem){
            textField.clearField();

        } else if (source == exitItem) {
            System.out.println("Quitting ...");
            System.exit(0);
        }
        super.repaint();

    }
}
