package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import logic.wallet.Debt;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class DebtAddAction implements DialogInterface.OnClickListener {

    Context context;
    Spinner type,rateType;
    EditText creditor,value,deadline,rate;

    public DebtAddAction(Context context, Spinner type, EditText creditor, EditText value, EditText deadline, EditText rate, Spinner rateType){
        this.context=context;
        this.type=type;
        this.creditor=creditor;
        this.value=value;
        this.deadline=deadline;
        this.rate=rate;
        this.rateType=rateType;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        double val,rt;
        String name;
        try{
            val=Double.valueOf(value.getText().toString());
            rt=Double.valueOf(rate.getText().toString());
        }catch(NumberFormatException error){
            Toast.makeText(context, "please input number!", Toast.LENGTH_SHORT).show();
            return ;
        }

        name=creditor.getText().toString();

        if (name.length()==0){
            Toast.makeText(context, "please input creditor!", Toast.LENGTH_SHORT).show();
            return ;
        }else if (name.contains("[")){
            Toast.makeText(context, "Please don't use '['!", Toast.LENGTH_SHORT).show();
            return ;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date dat;
        try {
            dat=sdf.parse(deadline.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(context, "please input date right!", Toast.LENGTH_SHORT).show();
            return ;
        }

        Debt debt=new Debt();
        debt.addDebt(creditor.getText().toString(), val, dat, rateType.getSelectedItem().toString(), rt, type.getSelectedItem().toString());
        Toast.makeText(context, "Debt saved!", Toast.LENGTH_SHORT).show();
        MainActivity.repaint(2);
    }
}
