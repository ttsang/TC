package sang.thai.tran.travelcompanion.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;
import sang.thai.tran.travelcompanion.BuildConfig;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

public class AppUtils {

    public static Drawable getDrawable(Context context, int id) {
        if (context == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            if (context.getResources() != null) {
                return ContextCompat.getDrawable(context, id);
            }
        }
        return null;
    }

    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            if (context.getResources() != null) {
                return ContextCompat.getColor(context, id);
            } else {
                return -1;
            }
        }
    }
//    public static int getPixelValue(Context context, int dp) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dp * scale + 0.5f);
//    }

    /**
     * get called from method
     *
     * @return
     */
    public static String calledFrom() {
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        if (steArray.length <= 4) {
            return "";
        }
        StackTraceElement ste = steArray[4];
        return ste.getMethodName() +        // method name
                "(" +
                ste.getFileName() +        // file name
                ":" +
                ste.getLineNumber() +    // line number
                ") ──> ";
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isPassValid(String email) {
        if (BuildConfig.DEBUG) {
            return true;
        }
        boolean isValid = false;

//        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String expression = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static List<String> isPassValid(String passwordhere, String confirmhere) {

        List<String> errorList = new ArrayList<String>();

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

//        if (!passwordhere.equals(confirmhere)) {
//            errorList.add("password and confirm password does not match");
//        }
        if (passwordhere.length() <= 8) {
            errorList.add("Password lenght must have alleast 8 character !!");
        }
        if (!specailCharPatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one specail character !!");
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one uppercase character !!");
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one lowercase character !!");
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one digit character !!");
        }

        return errorList;

    }

    public static boolean isPhoneValid(String email) {
        if (BuildConfig.DEBUG) {
            return true;
        }
        boolean isValid = false;
        if (email.length() >= 9) {
            isValid = true;
        }
        return isValid;
    }

    public static void openDatePicker(Activity activity, final EditTextViewLayout et_date) {
        if (activity == null) {
            return;
        }
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String date1 = sdf.format(myCalendar.getTime());
            et_date.setText(date1);
            int position = date1.length();
            Editable text = et_date.getEditableText();
            Selection.setSelection(text, position);
        };
        new DatePickerDialog(activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void openTimePicker(Activity activity, final EditTextViewLayout et_date) {
        if (activity == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        TimePickerDialog timePicker = new TimePickerDialog(activity, (timePicker1, selectedHour, selectedMinute) -> {
            String date =  selectedHour + ":" + selectedMinute + ":00";
            et_date.setText(date);
            int position = date.length();
            Editable text = et_date.getEditableText();
            Selection.setSelection(text, position);
        }, hour, minute, true);//Yes 24 hour time
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    public static String listToString(List<String> listToConvert) {
        StringBuilder csvBuilder = new StringBuilder();
        for (String s : listToConvert) {
            csvBuilder.append(s);
            csvBuilder.append(",");
            csvBuilder.append(" ");
        }
        String csv = " " + csvBuilder.toString();
        if (csv.endsWith(", ")) {
            csv = csv.substring(0, csv.length() - ", ".length());
        }
        return csv;
    }

    public static void openWeb(Activity activity) {
        String url = "http://uniquetour.biz/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public static String getBase64(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }



}
