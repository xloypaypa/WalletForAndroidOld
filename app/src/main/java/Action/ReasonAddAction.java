package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.history.ReasonHistory;

/**
 * Created by LT on 2015/3/22.
 */
public class ReasonAddAction implements DialogInterface.OnClickListener  {
    Context context;
    EditText name;

    public ReasonAddAction(Context context, EditText name){
        this.context=context;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String reason=name.getText().toString();
        ReasonHistory rh=new ReasonHistory();
        if (rh.reasonExist(reason)){
            Toast.makeText(context, "This reason have exist!", Toast.LENGTH_SHORT).show();
            return ;
        }else if (reason.contains("[")){
            Toast.makeText(context, "Please don't use '['", Toast.LENGTH_SHORT).show();
            return ;
        }else if (reason.length()==0){
            Toast.makeText(context, "Please input name", Toast.LENGTH_SHORT).show();
            return ;
        }
        rh.addReason(reason);
        MainActivity.repaint(4);
    }
}
