import java.util.*;
import java.awt.event.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.*;
class MyEditor{
    Resource R;
    MyEditor(){
        R=new Resource();
        R.f=new NotepadFrame(R);
        R.ta.getDocument().addDocumentListener(new DocumentListener(){
            public void removeUpdate(DocumentEvent e) {
                
                R.isSaved=false;
                updateUndoStack();
            }
            public void insertUpdate(DocumentEvent e) {
                
                R.isSaved=false;
                updateUndoStack();
            }
            public void changedUpdate(DocumentEvent e) {
                
                R.isSaved=false;
                updateUndoStack();
            }
        });
    }
    void updateUndoStack(){
        if(++R.changes>=10){
            R.undoStack.push(R.ta.getText());
            System.out.println("...Undo Stack Updated...");
            R.changes=0;
        }
    }
    public static void main(String[] args){
        MyEditor driver=new MyEditor();
        
    }
    
}