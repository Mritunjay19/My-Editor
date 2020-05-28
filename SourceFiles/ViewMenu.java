import javax.swing.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.event.*;

class ViewMenu extends JMenu implements CaretListener,ActionListener{
    JCheckBoxMenuItem statusbar;
    Resource R;
    
    ViewMenu(Resource R){
        super("View");
        this.R=R;
        this.setMnemonic(KeyEvent.VK_V);
        statusbar=new JCheckBoxMenuItem("Status Bar");
        //statusbar.setSelected(true);
        statusbar.addActionListener(this);
        R.ta.addCaretListener(this);

        this.add(statusbar);
    }
    void updateStatus(){
        if(R.showstatusbar)
            R.status.setText("Line: "+R.r+" , Col: "+R.c);
        else R.status.setText("");
    }
    public void caretUpdate(CaretEvent e){
        try{
        R.r=R.ta.getLineOfOffset(R.ta.getCaretPosition());
        R.c=R.ta.getCaretPosition()-R.ta.getLineStartOffset(R.r);
        R.r++;
        }catch(Exception E){
            System.out.println("Caret Update Error");
        }
        updateStatus();
    }
    public void actionPerformed(ActionEvent e){
        R.showstatusbar=statusbar.getState();
        updateStatus();
    }
}