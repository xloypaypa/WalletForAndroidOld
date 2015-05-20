package interfaceTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import database.viewer.DataViewer;
import type.DebtType;
import type.MoneyType;
import type.ReasonType;
import type.Type;

public class DataLoader {
    public static void loadAllType(Context context,Spinner spinner){
        DataViewer viewer=new DataViewer();
        viewer.loadData("money");
        List<String> list=new ArrayList<>();
        Vector <Type> type=viewer.getAllItem();
        for (int i=0;i<type.size();i++){
            list.add(((MoneyType)type.get(i)).getType());
        }
        ArrayAdapter<String> ad=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllReason(Context context, Spinner spinner){
        DataViewer viewer=new DataViewer();
        viewer.loadData("reason");
        List<String> list=new ArrayList<>();
        Vector <Type> reason=viewer.getAllItem();
        for (int i=0;i<reason.size();i++){
            list.add(((ReasonType)reason.get(i)).getName());
        }
        ArrayAdapter<String> ad=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllReasonWithRoot(Context context, Spinner spinner){
        DataViewer viewer=new DataViewer();
        viewer.loadData("reason");
        List<String> list=new ArrayList<>();
        Vector <Type> reason=viewer.getAllItem();
        list.add("root");
        for (int i=0;i<reason.size();i++){
            list.add(((ReasonType)reason.get(i)).getName());
        }
        ArrayAdapter<String> ad=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllDebtRateType(Context context, Spinner spinner){
        List<String> list=new ArrayList<>();
        list.add("null");
        list.add("year");
        list.add("month");
        ArrayAdapter<String> ad=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllUserType(Activity activity,Spinner spinner){
        List<String> list=new ArrayList<>();
        list.add("normal");
        list.add("tree");
        ArrayAdapter<String> ad=new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }

    public static void loadAllDebtID(Context context, Spinner spinner) {
        DataViewer viewer = new DataViewer();
        viewer.loadData("debt");
        Vector <Type> all = viewer.getAllItem();
        List<String> list=new ArrayList<>();
        for (int i=0;i<all.size();i++) {
            DebtType now= (DebtType) all.get(i);
            list.add(now.getID()+"");
        }
        ArrayAdapter<String> ad=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setSelection(0);
    }
}