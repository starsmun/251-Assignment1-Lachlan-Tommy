import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Main extends JFrame implements ActionListener {

    private static JMenuItem newItem, openItem, saveItem, printItem,addDate, searchItem, exitItem;
    private static final JMenuBar menuBar = new JMenuBar();
    private static final TextFieldPanel textField = new TextFieldPanel();

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

        JMenu manageMenu = new JMenu("Manage");
        menuBar.add(manageMenu);

        addDate = new JMenuItem("Add Date");
        addDate.addActionListener(this);
        manageMenu.add(addDate);

        searchItem = new JMenuItem("Search");
        searchItem.addActionListener(this);
        manageMenu.add(searchItem);

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
                textField.openFile(myFile);
            }
        } else if (source == newItem) {
            textField.clearField();

        } else if (source == addDate) {
            textField.addCurrentDate();

        } else if (source == saveItem) {
            textField.saveToFile();

        } else if (source == exitItem) {
            System.out.println("Quitting ...");
            System.exit(0);

        }else if (source == searchItem) {
            String searchQuery = JOptionPane.showInputDialog(this, "Search: ");
            if(searchQuery != null) {
                ArrayList<Integer> results = textField.search(searchQuery);
                if (!results.get(0).equals(-1)) {
                    for (int index : results) {
                        Highlighter highlighter = textField.textField.getHighlighter();
                        HighlightPainter painter =
                                new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
                        int p1 = index + searchQuery.length();
                        try {
                            highlighter.addHighlight(index, p1, painter);
                            JOptionPane.showMessageDialog(this, searchQuery + " was found " + results.size() + " times");
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }


                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The following search could not be found: " + searchQuery);
                }
            }
        }

        super.repaint();

    }
}
