package sang.thai.tran.travelcompanion.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import sang.thai.tran.travelcompanion.R;

import static sang.thai.tran.travelcompanion.utils.AppUtils.listToString;

public class DialogUtils {

    public static void showAlertDialog(final Activity context, String message, DialogInterface.OnClickListener listener) {
        if (context == null) {
            return;
        }
        if (context.isDestroyed() || context.isFinishing()) {
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(context.getResources().getString(android.R.string.ok), listener);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void onCreateOptionDialog(Activity activity, String title, final CharSequence[] strings, final List<String> mSelectedServicePackage, DialogInterface.OnClickListener ok) {
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(strings, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                // Else, if the item is already in the array, remove it
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedServicePackage.add(strings[which].toString());
                                } else mSelectedServicePackage.remove(strings[which].toString());
                            }
                        })
                // Set the action buttons
                .setPositiveButton(android.R.string.ok, ok)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    public static void onCreateSingleChoiceDialog(Activity activity, String title, final CharSequence[] strings, DialogInterface.OnClickListener ok) {
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(strings, 0, ok);
                // Set the action buttons
//                .setPositiveButton(android.R.string.ok, ok)
//                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });

        builder.create().show();
    }
}
