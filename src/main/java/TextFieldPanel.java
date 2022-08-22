import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TextFieldPanel extends JPanel {
    public JTextPane textField = new JTextPane();

    public TextFieldPanel(){
        JScrollPane jsp = new JScrollPane(textField);

        JPopupMenu menuRC = new JPopupMenu();
        textField.setComponentPopupMenu(menuRC);

        Action copyOption = new DefaultEditorKit.CopyAction();
        copyOption.putValue(Action.NAME, "Copy");

        Action pasteOption = new DefaultEditorKit.PasteAction();
        pasteOption.putValue(Action.NAME, "Paste");

        Action cutOption = new DefaultEditorKit.CutAction();
        cutOption.putValue(Action.NAME, "Cut");

        menuRC.add(copyOption);
        menuRC.add(pasteOption);
        menuRC.add(cutOption);

        this.add(jsp);
    }

    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }

    public void openFile(File textFile)
    {
        try{
            FileReader reader = new FileReader(textFile);
            while(reader.read() != -1){
                textField.read(reader,null);
            }
            reader.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void clearField(){
        textField.setText("");
    }

    public void saveToFile(){
        try{
            JFileChooser chooser = new JFileChooser("./");
            chooser.setDialogTitle("Save");

            int retVal = chooser.showSaveDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                FileWriter pw = new FileWriter (chooser.getSelectedFile() + ".txt");
                textField.write(pw);
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void addCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        textField.setText(dtf.format(now) + "\n" + textField.getText());
    }

    public ArrayList<Integer> search(String q) {
        ArrayList<Integer> indexs = new ArrayList<>();
        int index = textField.getText().indexOf(q);
        while (index >=0){
            indexs.add(index);
            index = textField.getText().indexOf(q, index+q.length())   ;
        }

        if (indexs.size() == 0) {
            indexs.add(-1);
        }
        return indexs;
    }

    public void savePDF() {
        Document document = new Document();
        try{
            JFileChooser chooser = new JFileChooser("./");
            chooser.setDialogTitle("Save");
            int retVal = chooser.showSaveDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                final PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(chooser.getSelectedFile() + ".pdf"));
                document.open();
                instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                document.add(new Paragraph(textField.getText()));
            }
            document.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (java.io.IOException ioe) {
           System.err.println(ioe.getMessage());
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
