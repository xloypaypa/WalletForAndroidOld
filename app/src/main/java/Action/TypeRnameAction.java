package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import logic.wallet.Money;

/**
 * Created by LT on 2015/3/21.
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
        Money user=new Money();
        if (user.getMoney().size()==0){
            Toast.makeText(context, "No type for rename!", Toast.LENGTH_SHORT).show();
            return ;
        }

        String pname,nname;
        pname=past.getSelectedItem().toString();
        nname=name.getText().toString();

        if (user.moneyTypeExist(nname)){
            Toast.makeText(context, "This type have exist!", Toast.LENGTH_SHORT).show();
            return ;
        }else if (nname.length()==0){
            Toast.makeText(context, "please input type!", Toast.LENGTH_SHORT).show();
            return ;
        }else if (nname.contains("[")){
            Toast.makeText(context, "Please don't use '['!", Toast.LENGTH_SHORT).show();
            return;
        }
        user.renameType(pname, nname);
    }
}
