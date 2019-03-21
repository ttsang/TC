package sang.thai.tran.travelcompanion.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

import sang.thai.tran.travelcompanion.R;

public class MonthYearPickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    public MonthYearPickerDialog() {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.year_picker_dialog, null);
//        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

//        monthPicker.setMinValue(1);
//        monthPicker.setMaxValue(12);
//        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(3500);
        yearPicker.setValue(year);

        builder.setView(dialog).setPositiveButton(Html.fromHtml("<font color='#FF4081'>Ok</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (listener != null) {
                    listener.onDateSet(null, yearPicker.getValue(), 0, 0);
                }
            }
        }).setNegativeButton(Html.fromHtml("<font color='#FF4081'>Cancel</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MonthYearPickerDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
