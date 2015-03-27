package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Spinner;
import android.widget.Toast;
import logic.wallet.Money;

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
        if (new Money().getMoney().size()==0){
            Toast.makeText(context, "No type for remove!", Toast.LENGTH_SHORT).show();
            return ;
        }
        new Money().removeType(name.getSelectedItem().toString());
    }
}
