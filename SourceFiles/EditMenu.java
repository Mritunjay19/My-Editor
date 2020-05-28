import javax.swing.undo.UndoManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;  
import java.util.Date;  

class EditMenu extends JMenu{
    JMenuItem undo,find,replace,datetime;
    Resource R;
    EditMenu(Resource R){
        super("Edit");
        this.R=R;
        this.setMnemonic(KeyEvent.VK_E);
        
        undo=new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        undo.addActionListener(e->{
            System.out.println("UNDO Called");
            R.ta.setText(R.undoStack.peek());
            if(R.undoStack.size()>1)
                R.undoStack.pop();
            R.changes=0;
        });
        
        find=new JMenuItem("Find");find.addActionListener(e->{
            new FindReplaceDialog(false,R);
        });
        replace=new JMenuItem("Replace");replace.addActionListener(e->{
            new FindReplaceDialog(true,R);
        });
        datetime=new JMenuItem("Date/Time");datetime.addActionListener(e->{
            appendDateTime();
        });

        this.add(undo);
        this.addSeparator();
        this.add(find);this.add(replace);
        this.addSeparator();
        this.add(datetime);
        
    }
    void appendDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();  
        R.ta.append(formatter.format(date));
    }
}
class FindReplaceDialog extends JDialog{
    JPanel buttonpanel,actionpanel,updownpanel;
    JButton findnext,replace,replaceall,cancel;
    JTextField findwhat,replacewith;
    JCheckBox matchcase;
    JRadioButton up,down;
    ButtonGroup bg;
    boolean isreplacedialog,ismatchcase=false;
    int prevdirection=1;
    Resource R;
    FindReplaceDialog(boolean isreplacedialog,Resource R){
        super(R.f,"Find / Replace",true);
        this.R=R;
        this.isreplacedialog=isreplacedialog;
        this.setLayout(new BorderLayout());

        buttonpanel=new JPanel();actionpanel=new JPanel();updownpanel=new JPanel();

        findnext=new JButton("Find Next");replace=new JButton("Replace");replaceall=new JButton("Replace All");cancel=new JButton("Cancel");

        buttonpanel.setLayout(new GridLayout(4,1,5,5));
        buttonpanel.add(findnext);buttonpanel.add(replace);buttonpanel.add(replaceall);buttonpanel.add(cancel);

        findwhat=new JTextField(10);replacewith=new JTextField(10);
        actionpanel.setLayout(new GridLayout(3,1));
        JPanel p1=new JPanel();
        p1.add(new JLabel("Find What"));p1.add(findwhat);
        JPanel p2=new JPanel();
        p2.add(new JLabel("Replace With"));p2.add(replacewith);
        matchcase=new JCheckBox("Match Case");
        actionpanel.add(p1);actionpanel.add(p2);actionpanel.add(matchcase);

        up=new JRadioButton("Up");down=new JRadioButton("Down",true);
        bg=new ButtonGroup();bg.add(down);bg.add(up);
        updownpanel.setLayout(new GridLayout(3,1));
        updownpanel.add(down);updownpanel.add(up);updownpanel.add(new JLabel(""));
        updownpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Direction"));


        this.add(buttonpanel,BorderLayout.EAST);
        this.add(actionpanel,BorderLayout.CENTER);
        this.add(updownpanel,BorderLayout.WEST);
        this.setLocation(R.f.getLocation().x+50,R.f.getLocation().y+100);
        this.setResizable(false);
        this.pack();
        this.setDialogType();
        this.setListeners();
        this.setVisible(true);
    }
    void setDialogType(){
        findnext.setEnabled(false);
        replace.setEnabled(false);
        replaceall.setEnabled(false);
        replacewith.setEditable(false);
        if(isreplacedialog){
            replacewith.setEditable(true);
        }
    }
    void setListeners(){
        findnext.addActionListener(e->{
            System.out.println("Find Clicked");
            if(findText(false)&&isreplacedialog){
                replace.setEnabled(true);
            }
            else replace.setEnabled(false);
        });
        replace.addActionListener(e->{
            System.out.println("Replace Clicked");
            replaceText();
            replace.setEnabled(false);
        });
        replaceall.addActionListener(e->{
            replaceAllText();
            System.out.println("ReplaceAll Clicked");
        });
        cancel.addActionListener(e->{
            this.dispose();
        });
        findwhat.addKeyListener(new KeyAdapter(){
            public void keyReleased(KeyEvent e){
                System.out.println("KeyListener Called");
                if(findwhat.getText().equals("")){
                    findnext.setEnabled(false);
                }
                else {
                    findnext.setEnabled(true);
                    if(isreplacedialog)
                        replaceall.setEnabled(true);
                }
                prevdirection=1;
            }
        });
        // if(isreplacedialog){
        //     replacewith.addKeyListener(new KeyAdapter(){
        //         public void keyReleased(KeyEvent e){
        //             if(replacewith.getText().equals("")){
        //                 replace.setEnabled(false);
        //                 replaceall.setEnabled(false);
        //             }
        //             else {replacewith.setEnabled(true);replaceall.setEnabled(true);}
        //         }
        //     });
        // }
        matchcase.addActionListener(e->{
            ismatchcase=matchcase.isSelected();
            System.out.println("Match case Changed="+ismatchcase);
            prevdirection=1;
        });
    }
    boolean findText(boolean foundone){
        String text=R.ta.getText();
        int current;
        int direction;
        int textsize=findwhat.getText().length();
        if(down.isSelected())direction=1;
        else direction=-1;
        if(prevdirection==-1&&direction==-1)current=R.ta.getCaretPosition()-1;
        else current=R.ta.getCaretPosition();
        while(current+direction*textsize<=text.length()&&current+direction*textsize>=0){
            int newcurrent=current+direction*textsize;
            String subs;
            if(direction==1)subs=text.substring(current, newcurrent);
            else subs=text.substring(newcurrent , current);
            if((ismatchcase&&findwhat.getText().equals(subs))||(!ismatchcase&&findwhat.getText().equalsIgnoreCase(subs))){
                if(direction==1)
                {R.ta.setSelectionStart(current);R.ta.setSelectionEnd(newcurrent);}
                else {R.ta.setSelectionStart(newcurrent);R.ta.setSelectionEnd(current);}
                prevdirection=direction;
                System.out.println("Text Found");
                return true;
            }
            current+=direction;
        }
        if(!foundone)
        JOptionPane.showMessageDialog(this, "Text Not Found", "MyNotepad" , JOptionPane.INFORMATION_MESSAGE);
        return false;
    }
    void replaceText(){
        R.ta.replaceSelection(replacewith.getText());
        if(up.isSelected()){
            prevdirection=1;
            R.ta.setCaretPosition(R.ta.getCaretPosition()-replacewith.getText().length());
        }
    }
    void replaceAllText(){
        boolean foundone=false;
        while(findText(foundone)){
            foundone=true;
            R.ta.replaceSelection(replacewith.getText());
            if(up.isSelected()){
                prevdirection=1;
                R.ta.setCaretPosition(R.ta.getCaretPosition()-replacewith.getText().length());
            }
        }
    }
}