package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xlo.walletforandroid.MainActivity;

import logic.Operator;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class TransferAction implements DialogInterface.OnClickListener {
    Spinner from,to;
    EditText value;
    Context context;

    public TransferAction(Context context, Spinner from, Spinner to, EditText value){
        this.context=context;
        this.from=from;
        this.to=to;
        this.value=value;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        double v=0;
        try{
            v=Double.valueOf(value.getText().toString());
        }catch (Exception e){
            Toast.makeText(context, "Please input number.", Toast.LENGTH_SHORT).show();
            return ;
        }
        Operator.transfer(from.getSelectedItem().toString(),to.getSelectedItem().toString(),v);
        MainActivity.repaint();
    }
}
