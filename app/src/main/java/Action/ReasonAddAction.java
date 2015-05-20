package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xlo.walletforandroid.MainActivity;

import logic.Operator;

/**
 * Created by LT on 2015/3/22.
 *
 */
public class ReasonAddAction implements DialogInterface.OnClickListener  {
    Context context;
    EditText name;

    public ReasonAddAction(Context context, EditText name){
        this.context=context;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Operator.addReason(name.getText().toString());
        MainActivity.repaint();
    }
}
