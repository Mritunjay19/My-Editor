import javax.swing.JTextArea;
import java.util.*;
import javax.swing.*;
class Resource{
    final String applicationName=" - MyEditor";
    String fileName;
    JTextArea ta;
    boolean isSaved;
    boolean hasName;
    int changes=0;
    Stack <String> undoStack;
    NotepadFrame f;
    int r,c;
    JLabel status;
    boolean showstatusbar;
    Resource(){
        fileName="Untitled";
        ta=new JTextArea();
        isSaved=true;
        hasName=false;
        changes=0;
        undoStack=new Stack<String>();
        undoStack.push("");
        r=1;c=0;
        status=new JLabel("");
        showstatusbar=false;
    }    
}