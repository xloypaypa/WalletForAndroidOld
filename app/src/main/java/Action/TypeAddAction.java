package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.example.xlo.walletforandroid.MainActivity;

import logic.Operator;

/**
 * Created by LT on 2015/3/21.
 *
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
        Operator.addMoneyType(name.getText().toString());
        MainActivity.repaint();
    }
}
