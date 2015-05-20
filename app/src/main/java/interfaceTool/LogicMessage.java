package interfaceTool;

import android.app.Activity;
import android.widget.Toast;

import logic.logicListener.LogicMessageListener;

/**
 * Created by LT on 2015/5/3.
 *
 */
public class LogicMessage extends LogicMessageListener {

    protected Activity activity;

    public void setActivity(Activity activity){
        this.activity=activity;
    }

    @Override
    public void UIAction() {
        Toast.makeText(activity, this.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
