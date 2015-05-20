package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xlo.walletforandroid.MainActivity;

import logic.Operator;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class UseAction implements DialogInterface.OnClickListener {

    protected Context context;
    protected Spinner type,reason;
    protected EditText number;
    protected RadioButton expenditure,income;

    public UseAction(Context context, Spinner type, RadioButton expenditure, RadioButton income, EditText number, Spinner reason){
        this.context=context;
        this.type=type;
        this.expenditure=expenditure;
        this.income=income;
        this.number=number;
        this.reason=reason;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        double value;
        try{
            value=Double.valueOf(number.getText().toString());
        }catch (Exception e){
            Toast.makeText(context, "Please input number.", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (expenditure.isChecked()){
            Operator.expenditure(type.getSelectedItem().toString(),value,reason.getSelectedItem().toString());
        }else if (income.isChecked()){
            Operator.income(type.getSelectedItem().toString(),value,reason.getSelectedItem().toString());
        }
        MainActivity.repaint();
    }
}
