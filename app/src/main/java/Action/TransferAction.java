package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.wallet.Cost;

/**
 * Created by LT on 2015/3/21.
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
        Cost user=new Cost();
        double val;
        if (from.getSelectedItem().toString().equals(to.getSelectedItem().toString())){
            Toast.makeText(context, "two type should not same!", Toast.LENGTH_SHORT).show();
            return ;
        }

        try{
            val=Double.valueOf(value.getText().toString());
        }catch(NumberFormatException error){
            Toast.makeText(context, "please input number!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (user.getMoney(from.getSelectedItem().toString())<val){
            Toast.makeText(context, "don't have enough money!", Toast.LENGTH_SHORT).show();
            return ;
        }

        user.transfer(from.getSelectedItem().toString(), to.getSelectedItem().toString(), val);
        Toast.makeText(context, "transfer saved!", Toast.LENGTH_SHORT).show();
        MainActivity.repaint();
    }
}
