package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.history.ReasonHistory;

/**
 * Created by LT on 2015/3/22.
 */
public class ReasonRenameAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner past;
    EditText name;

    public ReasonRenameAction(Context context, Spinner past, EditText name){
        this.context=context;
        this.past=past;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String p,n;
        ReasonHistory user=new ReasonHistory();
        p=past.getSelectedItem().toString();
        n=name.getText().toString();
        if (n.contains("[")){
            Toast.makeText(context, "Please don't use '['", Toast.LENGTH_SHORT).show();
            return ;
        }else if (n.length()==0){
            Toast.makeText(context, "Please input name", Toast.LENGTH_SHORT).show();
            return ;
        }else if (user.reasonExist(n)){
            Toast.makeText(context, "This reason have exist!", Toast.LENGTH_SHORT).show();
            return ;
        }
        user.changeReasonName(p,n);
        MainActivity.repaint(4);
    }
}
