import javax.swing.*;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.awt.event.*;
import java.awt.*;
class FormatMenu extends JMenu{
    JMenuItem font;
    Resource R;
    FormatMenu(Resource R){
        super("Format");
        this.R=R;
        this.setMnemonic(KeyEvent.VK_O);

        font=new JMenuItem("Font");
        font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));

        this.add(font);
        font.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("...Font DIALOG OPENED...");
                FontDialog d=new FontDialog(R);
            }
        });
    }

}
class FontDialog extends JDialog implements ItemListener,ActionListener{
    Font font;
    JPanel p1,p2,p3;
    JLabel sample;
    JButton ok,cancel;
    List fnt,sz,st;
    Resource R;
    String fname[];
    String fsize[]=new String[]{"10","20","30","40","50","60","70"};
    String fstyle[]=new String[]{"Regular","Bold","Italic","Bold Italic"};
    FontDialog(Resource R){
        super(R.f,"Font",true);
        this.R=R;
        this.setLayout(new GridLayout(3,1));
        this.setLocation(R.f.getLocation().x+75, R.f.getLocation().y+100);
        p1=new JPanel();p2=new JPanel();p3=new JPanel();

        font=R.ta.getFont();

        fname=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fnt=new List();
        int i=0;
        for(String x:fname){
            fnt.add(x);
            if(x.equals(font.getFontName()))
                fnt.select(i);
            i++;
        }
        fnt.addItemListener(this);
        sz=new List();
        i=0;
        for(String x:fsize){
            sz.add(x);
            if(Integer.parseInt(x)==font.getSize())
                sz.select(i);
            i++;
        }
        sz.addItemListener(this);
        st=new List();
        i=0;
        for(String x:fstyle){st.add(x);if(i==font.getStyle())st.select(i);i++;}
        st.addItemListener(this);
        
        sample=new JLabel("AaBbCc");
        sample.setFont(font);
        p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Sample"));
        p2.setLayout(new BorderLayout());
        p2.add(sample);

        ok=new JButton("OK");
        ok.addActionListener(this);
        cancel=new JButton("Cancel");
        cancel.addActionListener(this);
        
        p3.add(ok);
        p3.add(cancel);

        
        p1.add(fnt);p1.add(sz);p1.add(st);

        this.add(p1);this.add(p2);this.add(p3);

        pack();
        setVisible(true);
        setResizable(false);
    }
    public void itemStateChanged(ItemEvent e){
        if((List)e.getSource()==fnt){
            font=new Font(((List)e.getSource()).getSelectedItem(),font.getStyle(),font.getSize());
        }
        else if((List)e.getSource()==sz){
            font=new Font(font.getName(),font.getStyle(),Integer.parseInt(((List)e.getSource()).getSelectedItem()));
        }
        else {
            font=new Font(font.getName(),((List)e.getSource()).getSelectedIndex(),font.getSize());
        }
        sample.setFont(font);
    }
    public void actionPerformed(ActionEvent e){
        if((JButton)e.getSource()==ok){
            System.out.println("...Font Dialog->OK CLICKED...");
            System.out.println("...NEW Font ->"+font);
            R.ta.setFont(font);
            try{
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Font.txt"));

                oos.writeObject(R.ta.getFont());
            }
            catch(Exception E){
                System.out.println("Exception Occured");
            }
        }
        else System.out.println("...Font Dialog->CANCEL CLICKED...");
        this.dispose();
        System.out.println("...Font Dialog Closed...");
    }
}
