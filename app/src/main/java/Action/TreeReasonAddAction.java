package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lt.walletforandroid.MainActivity;

import logic.history.TreeReasonHistory;

/**
 * Created by LT on 2015/3/21.
 */
public class TreeReasonAddAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner father;
    EditText name,min,max,rank;

    public TreeReasonAddAction(Context context, Spinner father,EditText name,EditText min,EditText max,EditText rank){
        this.context=context;
        this.father=father;
        this.name=name;
        this.min=min;
        this.max=max;
        this.rank=rank;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String sname,root;
        double maxValue,minValue;
        int rankValue;

        sname=name.getText().toString();
        if (sname.length()==0){
            Toast.makeText(context, "please input name!", Toast.LENGTH_SHORT).show();
            return ;
        }
        root=father.getSelectedItem().toString();
        try{
            maxValue=Double.valueOf(max.getText().toString());
            minValue=Double.valueOf(min.getText().toString());
            rankValue=Integer.valueOf(rank.getText().toString());
            rankValue=Math.min(rankValue, 5);
            rankValue=Math.max(rankValue, 0);
        }catch (NumberFormatException e){
            Toast.makeText(context, "please input number!", Toast.LENGTH_SHORT).show();
            return ;
        }

        TreeReasonHistory trh=new TreeReasonHistory();

        if (trh.reasonExist(sname)||sname.equals("root")){
            Toast.makeText(context, "This reason have exist!", Toast.LENGTH_SHORT).show();
            return ;
        }else if (sname.contains("[")){
            Toast.makeText(context, "Please don't use '['", Toast.LENGTH_SHORT).show();
            return ;
        }else if (sname.length()==0){
            Toast.makeText(context, "Please input name", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (minValue>maxValue){
            Toast.makeText(context, "min expenditure should be lower than the max.", Toast.LENGTH_SHORT).show();
            return ;
        }else if (minValue<0){
            Toast.makeText(context, "value should be more than zero.", Toast.LENGTH_SHORT).show();
            return ;
        }

        trh.addReason(root, sname, minValue, maxValue, rankValue);
        MainActivity.repaint(4);
    }
}
