package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xlo.walletforandroid.MainActivity;

import database.viewer.DebtViewer;
import logic.Operator;
import type.DebtType;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class DebtRepayAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner type, id;
    EditText value;

    public DebtRepayAction(Context context, Spinner type, Spinner id, EditText value){
        this.context=context;
        this.type=type;
        this.id=id;
        this.value=value;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int pos;
        double val;
        try{
            pos=Integer.valueOf(id.getSelectedItem().toString());
            val=Double.valueOf(value.getText().toString());
        }catch (Exception e){
            Toast.makeText(context, "Please input right.", Toast.LENGTH_SHORT).show();
            return ;
        }

        DebtViewer debt=new DebtViewer();
        debt.loadData("debt");
        DebtType now=(DebtType) debt.getItem(id.getSelectedItem().toString());
        if (now.getDebtType().equals("borrowing")){
            Operator.repayBorrowing(pos, val, type.getSelectedItem().toString());
        }else{
            Operator.repayLoan(pos, val, type.getSelectedItem().toString());
        }
        MainActivity.repaint();
    }
}
