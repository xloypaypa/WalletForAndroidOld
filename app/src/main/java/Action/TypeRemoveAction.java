package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Spinner;

import com.example.xlo.walletforandroid.MainActivity;

import logic.Operator;

/**
 * Created by xlo on 2015/3/21.
 *
 */
public class TypeRemoveAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner name;

    public TypeRemoveAction(Context context, Spinner name){
        this.context=context;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Operator.removeMoneyType(name.getSelectedItem().toString());
        MainActivity.repaint();
    }
}
