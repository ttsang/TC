package sang.thai.tran.travelcompanion.retrofit;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sang.thai.tran.travelcompanion.model.Response;
import sang.thai.tran.travelcompanion.utils.Log;

public abstract class BaseObserver<T> implements Observer<T> {

    private static final String TAG = BaseObserver.class.getSimpleName();
    private static final String TRY_AGAIN = "try_again";
    private String mUrl;
    private String mDomain;
    private boolean mIsShowErrorDialog;
    private int mCountSSl = 0;
    private Disposable disposable;
    private RetryCallApiWhenSSL mRetryCallApiWhenSSL;
    private Map<String, String> mParams;

    interface RetryCallApiWhenSSL {
        void onSSL();
    }

    public BaseObserver(boolean isShowDialog) {
        mIsShowErrorDialog = isShowDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T s) {
        sang.thai.tran.travelcompanion.model.Response response = (Response) s;
        if (response.getResult().getData() != null) {
        }
        onSuccess(s, response.toString());
        Log.d("Sang","onNext: " + response.getResult());
//        String tmp = (String) s;
////        test();
//        if (TextUtils.isEmpty(tmp)) {
//            handleOnFailure(new Exception("Empty response"), "Empty response", false);
//        } else {
//            try {
//                JSONObject response = new JSONObject(tmp);
////                // Handle for google get address
////                if (response.has("results") && response.has("status") && response.optString("status").equals("OK")) {
////                    JSONArray array = new JSONArray(response.optString("results"));
////                    if (array.length() > 0) {
//////                        JSONObject formatted_address = array.get(0);
////                        String formatted_address = array.optJSONObject(0).optString("formatted_address");
////                        onSuccess(response, formatted_address);
////                        return;
////                    }
////                }
//            } catch (JSONException e) {
//                handleOnFailure(e, e.getMessage(), true);
//            }
//        }
    }

    @Override
    public void onError(Throwable e) {
        resetDisposable();
        if (mIsShowErrorDialog) {
        }
    }

    @Override
    public void onComplete() {
        resetDisposable();
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setDomain(String mUrl) {
        this.mDomain = mUrl;
    }

    void setRetryCallApiWhenSSL(RetryCallApiWhenSSL mUrl) {
        this.mRetryCallApiWhenSSL = mUrl;
    }

    void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    private void handleOnFailure(Throwable e, String errorMsg, boolean checkServerReachable) {

    }

    private void resetDisposable() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void test() {
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public abstract void onSuccess(T result, String response);

    public abstract void onFailure(Throwable e, String errorMsg);

}
