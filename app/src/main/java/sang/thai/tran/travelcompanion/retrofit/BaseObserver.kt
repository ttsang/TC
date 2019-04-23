package sang.thai.tran.travelcompanion.retrofit

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.utils.Log

abstract class BaseObserver<T>(private val mIsShowErrorDialog: Boolean) : Observer<T> {
    private var mUrl: String? = null
    private var mDomain: String? = null
    private val mCountSSl = 0
    var disposable: Disposable? = null
        private set
    private var mRetryCallApiWhenSSL: RetryCallApiWhenSSL? = null
    private var mParams: Map<String, String>? = null

    internal interface RetryCallApiWhenSSL {
        fun onSSL()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(s: T) {
        val response = s as Response
        if (response.result.data != null) {
        }
        onSuccess(s, response.toString())
        Log.d("Sang", "onNext: " + response.result)
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

    override fun onError(e: Throwable) {
        resetDisposable()
        if (mIsShowErrorDialog) {
        }
    }

    override fun onComplete() {
        resetDisposable()
    }

    fun setUrl(mUrl: String) {
        this.mUrl = mUrl
    }

    fun setDomain(mUrl: String) {
        this.mDomain = mUrl
    }

    internal fun setRetryCallApiWhenSSL(mUrl: RetryCallApiWhenSSL) {
        this.mRetryCallApiWhenSSL = mUrl
    }

    internal fun setParams(params: Map<String, String>) {
        this.mParams = params
    }

    private fun handleOnFailure(e: Throwable, errorMsg: String, checkServerReachable: Boolean) {

    }

    private fun resetDisposable() {
        if (disposable != null && disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    private fun test() {}

    abstract fun onSuccess(result: T, response: String)

    abstract fun onFailure(e: Throwable, errorMsg: String)

    companion object {
        private val TAG = BaseObserver::class.java.simpleName
        private val TRY_AGAIN = "try_again"
    }

}
