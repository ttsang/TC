package sang.thai.tran.travelcompanion.fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.LoginActivity;
import sang.thai.tran.travelcompanion.activity.MainActivity;
import sang.thai.tran.travelcompanion.model.Response;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.retrofit.BaseObserver;
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.utils.DialogUtils;

import static sang.thai.tran.travelcompanion.utils.AppConstant.COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.POSTER;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUCCESS_CODE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.WELL_TRAINED_COMPANION;

public class LoginFragment extends BaseFragment {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.tv_register_account)
    TextView tv_register_account;

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.email_sign_in_button)
    protected void login() {
        attemptLogin();
    }

    @OnClick(R.id.tv_register_account)
    protected void register() {
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).register();
        }
    }

    @OnClick(R.id.tv_forgot_pass)
    protected void forgotPass() {
        if (getActivity() != null) {
            ((LoginActivity) getActivity()).forgotPass();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        et_phone.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = et_phone.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_phone.setError(getString(R.string.error_field_required));
            focusView = et_phone;
            cancel = true;
        } else if (!isEmailValid(email)) {
            et_phone.setError(getString(R.string.error_invalid_email));
            focusView = et_phone;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            showProgressDialog();
            Map<String, String> map = new HashMap<>();
            map.put("email", et_phone.getText().toString());
            map.put("password", mPasswordView.getText().toString());
            HttpRetrofitClientBase.getInstance().loginFunction("api/account/login", map, new BaseObserver<Response>(true) {
                @Override
                public void onSuccess(final Response response, String responseStr) {
                    hideProgressDialog();
                    if (getActivity() == null) {
                        return;
                    }
                    if (response.getStatusCode() == SUCCESS_CODE) {
                        if (response.getResult().getData() != null) {
                            UserInfo userInfo = response.getResult().getData().getUserInfo();
                            ApplicationSingleton.getInstance().setUserInfo(userInfo);
                            startMain(userInfo);
                        }
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.showAlertDialog(getActivity(), response.getMessage(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Throwable e, String errorMsg) {
                    hideProgressDialog();
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return email.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//        mProgressView.animate().setDuration(shortAnimTime).alpha(
//                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            }
//        });
//    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

//        et_phone.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
//            showProgress(false);

            if (success) {
//                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//            showProgress(false);
        }
    }
}
