import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

class NotepadFrame extends JFrame implements WindowListener{
    NotepadMenuBar mb;
    JPanel statuspanel;
    Resource R;
    NotepadFrame(Resource R){
        this.R=R;
        mb=new NotepadMenuBar(R);
        this.setTitle(R.fileName+R.applicationName);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocation(200, 200);
        this.setSize(new Dimension(600,400));
        this.setLayout(new BorderLayout());
        this.setJMenuBar(mb);
        try{
            Font ft=(Font)((new ObjectInputStream(new FileInputStream("Font.txt"))).readObject());
            R.ta.setFont(ft);
        }
        catch(Exception e){
            System.out.println("Font File not Found");
        }
        
        this.add(new JScrollPane(R.ta),BorderLayout.CENTER);

        statuspanel=new JPanel();
        statuspanel.setLayout(new BorderLayout());;
        statuspanel.add(R.status,BorderLayout.EAST);
        this.add(statuspanel,BorderLayout.SOUTH);
        this.addWindowListener(this);
        // R.ta.requestFocus();
        this.setVisible(true);
    }
    public void windowClosing(WindowEvent e){
        if(!R.isSaved)
            if(!mb.fm.showConfirmDialogBox())
                return;
        this.dispose();
    }
    public void windowClosed(WindowEvent e){}
    public void windowOpened(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
}
