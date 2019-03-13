package sang.thai.tran.travelcompanion.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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
}
