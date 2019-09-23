package sang.thai.tran.travelcompanion.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.List;

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
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(android.R.string.ok), listener);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void showAlertDialogWithTile(final Activity context, String title, String message, DialogInterface.OnClickListener listener) {
        if (context == null) {
            return;
        }
        if (context.isDestroyed() || context.isFinishing()) {
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(android.R.string.ok), listener);
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void onCreateOptionDialog(Activity activity, String title, final String[] strings, final List<String> mSelectedServicePackage, DialogInterface.OnClickListener ok) {
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(strings, null,
                        (dialog, which, isChecked) -> {
                            // Else, if the item is already in the array, remove it
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                mSelectedServicePackage.add(strings[which]);
                            } else mSelectedServicePackage.remove(strings[which]);
                        })
                // Set the action buttons
                .setPositiveButton(android.R.string.ok, ok)
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> dialog.dismiss());

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

    public static AlertDialog showProgressDialog(final Activity context) {
        if (context == null) {
            return null;
        }
        if (context.isDestroyed() || context.isFinishing()) {
            return null;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setCancelable(false);
        alertDialogBuilder.setView(new ProgressBar(context));
        AlertDialog alert = alertDialogBuilder.create();
        if (alert.getWindow() != null) {
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return alert;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static AlertDialog showTermOfService(final Activity context) {
        if (context == null) {
            return null;
        }
        if (context.isDestroyed() || context.isFinishing()) {
            return null;
        }
        WebView webView = new WebView(context);
        webView.setWebViewClient(new MyWebViewClient());

        String url = "file:///android_asset/error.html";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(url);
//        webView.loadData(null, "text/html", "utf-8");
//        webView.loadDataWithBaseURL(null, "HTML content here", "text/html", "utf-8", null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(webView);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow();//            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alert;
    }

    private static class MyWebViewClient extends WebViewClient {
        ProgressDialog prDialog;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prDialog = new ProgressDialog(view.getContext());
            prDialog.setMessage("Please wait ...");
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (prDialog != null) {
                prDialog.dismiss();
            }
        }
    }
}
