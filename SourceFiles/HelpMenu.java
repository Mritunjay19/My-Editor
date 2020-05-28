import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.*;
import java.io.File;

class HelpMenu extends JMenu{
    JMenuItem about;
    Resource R;

    HelpMenu(Resource R){
        super("About");
        this.R=R;
        this.setMnemonic(KeyEvent.VK_A);
        about=new JMenuItem("About");
        about.addActionListener(e->{
            new AboutDialog();
        });
        this.add(about);
    }
    class AboutDialog extends JDialog{
        class ImagePanel extends Panel{
            BufferedImage bi;
            ImagePanel(){
                
                try{
                       bi=ImageIO.read(new File("ig.class"));
                       JLabel il=new JLabel(new ImageIcon(bi));
                        this.add(il,BorderLayout.WEST);
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            public void paintComponent(Graphics g){
                g.drawImage(bi,0,0,10,10,this);
            }

        }
        AboutDialog(){
            super(R.f,true);
            this.setLayout(new BorderLayout());
            this.setTitle("Made By :-");
            this.setLocation(R.f.getLocation().x+75, R.f.getLocation().y+100);
            this.setResizable(false);
            JTextArea l1=new JTextArea("\nMritunjay Dev Sharma\nB.Tech. C.S.E\nIndian Institute of Technology (Indian School Of Mines),Dhanbad\nMritunjay19.02.03@gmail.com");
            l1.setFont(new Font("Serif",3,17));
            l1.setEditable(false);
            l1.setBackground(Color.GRAY);
            this.add(new ImagePanel(),BorderLayout.WEST);
            this.add(l1);
            this.pack();
            this.setVisible(true);
        }
    }
}


