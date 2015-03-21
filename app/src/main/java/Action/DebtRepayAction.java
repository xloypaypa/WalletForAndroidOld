package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.User;
import logic.wallet.Debt;
import logic.wallet.Money;

/**
 * Created by LT on 2015/3/21.
 */
public class DebtRepayAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner type;
    EditText id,value;

    public DebtRepayAction(Context context, Spinner type, EditText id, EditText value){
        this.context=context;
        this.type=type;
        this.id=id;
        this.value=value;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Debt debt=new Debt();
        int did;
        double val;
        try{
            did=Integer.valueOf(id.getText().toString());
            val=Double.valueOf(value.getText().toString());
        }catch(NumberFormatException e){
            Toast.makeText(context, "please input number!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (!debt.debtExist(did)){
            Toast.makeText(context, "Debt not exist!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (debt.getDebtByID(did).getMaxRepay()<val){
            Toast.makeText(context, "Debt only need "+debt.getDebtByID(did).getMaxRepay()+".", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (new Money().getMoney(type.getSelectedItem().toString())<val){
            Toast.makeText(context, "don't have enough money!", Toast.LENGTH_SHORT).show();
            return ;
        }

        debt.repay(did, val, type.getSelectedItem().toString());
        Toast.makeText(context, "Repay saved!", Toast.LENGTH_SHORT).show();
        MainActivity.repaint(2);
    }
}
