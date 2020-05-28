import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class FileMenu extends JMenu implements ActionListener{
    JMenuItem newfile,open,save,saveas;
    String s;
    JFileChooser fc=new JFileChooser();
    Resource R;
    String path;
    FileMenu(Resource R){
        super("File");
        this.R=R;
        this.setMnemonic(KeyEvent.VK_F);

        newfile=new JMenuItem("New");
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        open=new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        save=new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveas=new JMenuItem("Save As");
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK|KeyEvent.SHIFT_DOWN_MASK));

        newfile.addActionListener(this);open.addActionListener(this);save.addActionListener(this);saveas.addActionListener(this);

        this.add(newfile);this.add(open);this.add(save);this.add(saveas);   

    }
    public void actionPerformed(ActionEvent e){
        if(((JMenuItem)e.getSource())==newfile){
            System.out.println("newfile CLICKED");
            newFile();
        }
        else if(((JMenuItem)e.getSource())==save){
            System.out.println("save CLICKED");
            if(R.hasName)
                saveText();
            else saveDialog();
        }
        else if(((JMenuItem)e.getSource())==open){
            System.out.println("open CLICKED");
            openDialog();
        }
        else{
            System.out.println("saveas CLICKED");
            saveDialog();
        }

    }
    void newFile(){
        
            if(!R.isSaved){
                showConfirmDialogBox();
            }
            R.ta.setText("");
            R.fileName="Untitled";
            R.f.setTitle(R.fileName+R.applicationName);
            R.isSaved=true;
            R.hasName=false;
            R.undoStack=new Stack <String>();
            R.undoStack.push("");
            System.out.println("...NEW File Opened...");
    }
    void openDialog(){
            if(!R.isSaved){
                showConfirmDialogBox();
            }
            
            if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                System.out.println(fc.getSelectedFile().getAbsolutePath());
                try{
                    s="";
                    FileReader fr=new FileReader(fc.getSelectedFile());
                    int i;
                    R.fileName=fc.getSelectedFile().getName();
                    while((i=fr.read())!=-1){
                        s+=(char)i;
                    }
                    R.ta.setText(s);
                    fr.close();
                    R.f.setTitle(R.fileName+R.applicationName);
                    path=fc.getSelectedFile().getPath();
                    System.out.println("...File Opened...");
                    R.isSaved=true;
                    R.hasName=true;
                    R.undoStack=new Stack <String>();
                    R.undoStack.push("");
                }catch(Exception E){System.out.println("Read Error");}
            }
    }
    boolean showConfirmDialogBox(){
        int flag;
        flag=JOptionPane.showConfirmDialog(null,"Do you want to save current file?");
        if(flag==0){
            System.out.println("...ConfirmDialog-->YES clicked...");
            if(!R.hasName)                    
                if(!saveDialog())
                    return true;
            else saveText();
        }
        else if(flag==-1||flag==2){
            System.out.println("..Cancel Clicked...");
            return false;
        }
        return true;
    }
    boolean saveDialog(){
        fc.setSelectedFile(new File("*.txt"));
        if(JFileChooser.APPROVE_OPTION==fc.showSaveDialog(null)){
            path=fc.getSelectedFile().getPath();
            if(!path.endsWith(".txt"))path=path+".txt";
            R.fileName=fc.getSelectedFile().getName();
            if(!R.fileName.endsWith(".txt"))R.fileName+=".txt";
            R.f.setTitle(R.fileName+R.applicationName);
            R.hasName=true;
            saveText();
            return true;
        }
        System.out.println("...File NOT Saved...");
        return false;
    }
    void saveText(){
        try{
            FileWriter fw=new FileWriter(path);
            String text=R.ta.getText();
            fw.write(text);
            fw.close();
            R.isSaved=true;
            System.out.println("...File Saved...");
        }
        catch(Exception e){
            System.out.println("...File NOT Saved...");
            System.out.println(e.getMessage());
        }
    }
}
