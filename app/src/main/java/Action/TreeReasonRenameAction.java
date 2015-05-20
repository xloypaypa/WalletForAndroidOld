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
public class TreeReasonRenameAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner past,father;
    EditText name,min,max,rank;

    public TreeReasonRenameAction(Context context, Spinner past, Spinner father,EditText name,EditText min,EditText max,EditText rank){
        this.context=context;
        this.past=past;
        this.father=father;
        this.name=name;
        this.min=min;
        this.max=max;
        this.rank=rank;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        double mi,ma;
        int r;
        mi=ma=r=0;
        try{
            mi=Double.valueOf(min.getText().toString());
            ma=Double.valueOf(max.getText().toString());
            r=Integer.valueOf(rank.getText().toString());
            if (r>5) r=5; if (r<0) r=0;
        }catch (Exception e){
            Toast.makeText(context, "Please input number.", Toast.LENGTH_SHORT).show();
            return ;
        }
        Operator.renameTreeReason(past.getSelectedItem().toString(),name.getText().toString(),father.getSelectedItem().toString(),mi,ma,r);
        MainActivity.repaint();
    }
}
