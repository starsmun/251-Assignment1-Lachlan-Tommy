import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import org.fife.ui.rsyntaxtextarea.*;




import java.awt.print.PrinterException;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TextFieldPanel extends JPanel {
    public RSyntaxTextArea textField = new RSyntaxTextArea();

    public TextFieldPanel(){

        textField.setCodeFoldingEnabled(true);

        JScrollPane jsp = new JScrollPane (textField);

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

        if(!textFile.getName().endsWith(".odt")){
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
        else {


        }

        if (textFile.getName().endsWith(".java")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        }
        else if (textFile.getName().endsWith(".py")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        }else if (textFile.getName().endsWith(".cpp") | textFile.getName().endsWith(".c++") | textFile.getName().endsWith(".h")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
        }else if (textFile.getName().endsWith(".c")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        }else if (textFile.getName().endsWith(".cs")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSHARP);
        }else if (textFile.getName().endsWith(".css")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        }else if (textFile.getName().endsWith(".csv")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSV);
        }else if (textFile.getName().endsWith(".html")) {
            textField.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        }
    }

    public void printText() {
        try{
            textField.print();
        }catch (PrinterException e){
            e.printStackTrace();
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

        try{
            Document document = new Document();
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
        } catch (ExceptionConverter | DocumentException | IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
