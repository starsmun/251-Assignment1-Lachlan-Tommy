import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextFieldPanel extends JPanel {
    public JTextPane textField = new JTextPane();

    public TextFieldPanel(){
        JScrollPane jsp = new JScrollPane(textField);
        this.add(jsp);
    }

    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }

    public void FileOpener(File textFile)
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

            int retVal = chooser.showOpenDialog(this);
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
        textField.setText(dtf.format(now) + "\n");
    }



}
