package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Spinner;

import com.example.lt.walletforandroid.MainActivity;

import logic.history.ReasonHistory;

/**
 * Created by LT on 2015/3/21.
 */
public class ReasonRemoveAction implements DialogInterface.OnClickListener {
    Context context;
    Spinner name;

    public ReasonRemoveAction(Context context, Spinner name){
        this.context=context;
        this.name=name;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ReasonHistory reason=new ReasonHistory();
        reason.removeReason(name.getSelectedItem().toString());
        MainActivity.repaint(4);
    }
}
