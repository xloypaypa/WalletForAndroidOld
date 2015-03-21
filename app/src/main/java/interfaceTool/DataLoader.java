package interfaceTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logic.history.ReasonHistory;
import logic.wallet.Money;
import logic.wallet.Wallet;
import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DataLoader {
	public static void loadAllType(Context context,Spinner spinner){
		List<String> list=new ArrayList<String>();
		Vector <String> type=new Money().getAllTypeName();
		for (int i=0;i<type.size();i++){
			list.add(type.get(i));
		}
		ArrayAdapter<String> ad=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
		spinner.setSelection(0);
	}

    public static void loadAllReason(Context context, Spinner spinner){
        List<String> list=new ArrayList<String>();
        Vector <String> reason=new ReasonHistory().getAllReasonName();
        for (int i=0;i<reason.size();i++){
            list.add(reason.get(i));
        }
        ArrayAdapter<String> ad=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllReasonWithRoot(Context context, Spinner spinner){
        List<String> list=new ArrayList<String>();
        Vector <String> reason=new ReasonHistory().getAllReasonName();
        list.add("root");
        for (int i=0;i<reason.size();i++){
            list.add(reason.get(i));
        }
        ArrayAdapter<String> ad=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllDebtRateType(Context context, Spinner spinner){
        List<String> list=new ArrayList<String>();
        list.add("null");
        list.add("year");
        list.add("month");
        ArrayAdapter<String> ad=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }
	
	public static void loadAllUserType(Activity activity,Spinner spinner){
		List<String> list=new ArrayList<String>();
		list.add("normal");
		list.add("tree");
		ArrayAdapter<String> ad=new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, list);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(ad);
		spinner.setSelection(0);
	}
}
