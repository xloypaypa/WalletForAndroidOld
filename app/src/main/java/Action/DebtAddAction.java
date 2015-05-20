package Action;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.xlo.walletforandroid.MainActivity;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import logic.Operator;

/**
 * Created by LT on 2015/3/21.
 *
 */
public class DebtAddAction implements DialogInterface.OnClickListener {

    Context context;
    Spinner type, rateType;
    EditText creditor, value, deadline, rate;
    protected RadioButton borrowing, loan;

    public DebtAddAction(Context context, RadioButton borrowing, RadioButton loan, Spinner type, EditText creditor, EditText value, EditText deadline, EditText rate, Spinner rateType) {
        this.context = context;
        this.type = type;
        this.borrowing = borrowing;
        this.loan = loan;
        this.creditor = creditor;
        this.value = value;
        this.deadline = deadline;
        this.rate = rate;
        this.rateType = rateType;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        double v, r;
        Date die;
        v = r = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            v = Double.valueOf(value.getText().toString());
            r = Double.valueOf(rate.getText().toString());
            die = sdf.parse(deadline.getText().toString());
        } catch (Exception e) {
            Toast.makeText(context, "Please input right.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (borrowing.isChecked()) {
            Operator.addBorrowing(creditor.getText().toString(), v, type.getSelectedItem().toString(), die, r, rateType.getSelectedItem().toString());
        } else if (loan.isChecked()) {
            Operator.addLoan(creditor.getText().toString(), v, type.getSelectedItem().toString(), die, r, rateType.getSelectedItem().toString());
        }

        MainActivity.repaint();
    }
}
