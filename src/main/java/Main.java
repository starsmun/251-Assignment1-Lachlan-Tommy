import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import org.fife.ui.rtextarea.*;
import org.yaml.snakeyaml.Yaml;



public class Main extends JFrame implements ActionListener {

    private static JMenuItem newItem, openItem, saveItem, printItem, addDate, searchItem, exitItem, aboutItem, pdfConvertItem;
    private static final JMenuBar menuBar = new JMenuBar();
    private static final TextFieldPanel textField = new TextFieldPanel();

    private static Color highlight = Color.red;

    public Main() {
        super("Test Editor");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menuBar);

        loadYaml();

        textField.setLayout(new GridLayout(0,1));

        RTextScrollPane sp = new RTextScrollPane(textField);
        this.add(sp);

        // File Menu with options //
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

        pdfConvertItem = new JMenuItem("Convert to PDF");
        pdfConvertItem.addActionListener(this);
        fileMenu.add(pdfConvertItem);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);

        // Manage Menu with options //
        JMenu manageMenu = new JMenu("Manage");
        menuBar.add(manageMenu);

        addDate = new JMenuItem("Add Date");
        addDate.addActionListener(this);
        manageMenu.add(addDate);

        searchItem = new JMenuItem("Search");
        searchItem.addActionListener(this);
        manageMenu.add(searchItem);

        // Help Menu with options //
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);

        this.setSize(800, 800);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    // Loads Data from Yaml file
    public void loadYaml(){
        try{
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream("config.yml");
            Map<String, Object> yamlMap = yaml.load(inputStream);

            setHighlight((String) yamlMap.get("highlight-colour"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // Reads string as Color and changes colour
    public void setHighlight(String colour){
        try {
            Field field = Class.forName("java.awt.Color").getField(colour);
            highlight = (Color)field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // File menu options based on Source
    public void actionPerformed(ActionEvent event) {
        JComponent source = (JComponent) event.getSource();

       if (source == newItem) {
           textField.clearField();

       } else if (source == openItem) {
           textField.openFile();

       } else if (source == saveItem) {
           textField.saveToFile();

       } else if (source == printItem) {
           textField.printText();

       } else if (source == pdfConvertItem) {
           textField.savePDF();

       } else if (source == exitItem) {
           System.out.println("Quitting ...");
           System.exit(0);

       } else if (source == addDate) {
            textField.addCurrentDate();

       } else if (source == searchItem) {
            String searchQuery = JOptionPane.showInputDialog(this, "Search: ");
            if(searchQuery != null) {
                ArrayList<Integer> results = textField.search(searchQuery);
                if (!results.get(0).equals(-1)) {
                    for (int index : results) {
                        Highlighter highlighter = textField.textField.getHighlighter();
                        HighlightPainter painter =
                                new DefaultHighlighter.DefaultHighlightPainter(highlight);
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
       } else if (source == aboutItem) {
           JOptionPane.showMessageDialog(this,
                   "Lachlan - 21005784. \n" +
                           "Tommy - 21013898. \n" +
                           "This text editor was made for assignment one for the course 159251. ");
       }
       super.repaint();

    }
}
