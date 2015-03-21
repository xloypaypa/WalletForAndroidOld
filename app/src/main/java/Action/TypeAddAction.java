package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.history.ReasonHistory;
import logic.wallet.Money;

/**
 * Created by LT on 2015/3/21.
 */
public class TypeAddAction implements DialogInterface.OnClickListener {
    Context context;
    EditText name;

    public TypeAddAction(Context context, EditText name){
        this.context=context;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Money user=new Money();
        String message=name.getText().toString();
        if (user.moneyTypeExist(message)){
            Toast.makeText(context, "This type have exist!", Toast.LENGTH_SHORT).show();
        }else if (message.contains("[")){
            Toast.makeText(context, "Please don't use '['!", Toast.LENGTH_SHORT).show();
        }else if (message.length()==0) {
            Toast.makeText(context, "please input type!", Toast.LENGTH_SHORT).show();
        }else{
            user.addType(message);
        }
    }
}
