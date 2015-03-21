package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.User;
import logic.history.ReasonHistory;
import logic.history.TreeReasonHistory;
import logic.wallet.Cost;

/**
 * Created by LT on 2015/3/21.
 */
public class UseAction implements DialogInterface.OnClickListener {

    protected Context context;
    protected Spinner type,reason;
    protected EditText number;
    Switch in_out;

    public UseAction(Context context, Spinner type, Switch in_out, EditText number, Spinner reason){
        this.context=context;
        this.type=type;
        this.in_out=in_out;
        this.number=number;
        this.reason=reason;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Cost user=new Cost();
        ReasonHistory rh;

        System.out.print(type.getSelectedItem().toString()+" "+in_out.isChecked()+" "+number.getText().toString()+" "+reason.getSelectedItem().toString());

        if (User.userReason.equals("normal")){
            rh=new ReasonHistory();
        }else{
            rh=new TreeReasonHistory();
        }

        double val;

        try{
            val=Double.valueOf(number.getText().toString());
        }catch(NumberFormatException error){
            Toast.makeText(context, "please input value!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (!User.userReason.equals("normal")&&!in_out.isChecked()&&!((TreeReasonHistory) rh).checkExpenditure(reason.getSelectedItem().toString(), val)){
            Toast.makeText(context, "this reason is over-expenditure!", Toast.LENGTH_SHORT).show();
            return ;
        }else if (!in_out.isChecked()&&user.getMoney(type.getSelectedItem().toString())<val){
            Toast.makeText(context, "don't have enough money!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (in_out.isChecked()){
            rh.addIncome(reason.getSelectedItem().toString(), val);
            val=-val;
        }else{
            rh.addExpenditure(reason.getSelectedItem().toString(), val);
        }

        user.addChange(type.getSelectedItem().toString(), val, reason.getSelectedItem().toString());
        Toast.makeText(context, "Cost Saved!", Toast.LENGTH_SHORT).show();
        MainActivity.repaint();
    }
}
