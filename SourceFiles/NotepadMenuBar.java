import java.awt.Color;
import javax.swing.*;

class NotepadMenuBar extends JMenuBar{
    Resource R;
    EditMenu em;
    FileMenu fm;
    FormatMenu fom;
    HelpMenu hm;
    ViewMenu vm;
    public NotepadMenuBar(Resource R){
        this.R=R;
        fm=new FileMenu(R);
        em=new EditMenu(R);
        fom=new FormatMenu(R);
        vm=new ViewMenu(R);
        hm=new HelpMenu(R);
        add(fm);
        add(em);
        add(fom);
        add(vm);
        add(hm);
    }
}