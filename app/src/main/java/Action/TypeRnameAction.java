package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.xlo.walletforandroid.MainActivity;

import logic.Operator;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class TypeRnameAction  implements DialogInterface.OnClickListener {
    Context context;
    Spinner past;
    EditText name;

    public TypeRnameAction(Context context,Spinner past, EditText name){
        this.context=context;
        this.past=past;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Operator.renameMoneyType(this.past.getSelectedItem().toString(),name.getText().toString());
        MainActivity.repaint();
    }
}
