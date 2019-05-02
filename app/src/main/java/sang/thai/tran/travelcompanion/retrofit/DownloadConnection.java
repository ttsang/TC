package sang.thai.tran.travelcompanion.retrofit;

import android.os.AsyncTask;
import android.os.Build;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import sang.thai.tran.travelcompanion.utils.AppConstant;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.utils.Log;

import static sang.thai.tran.travelcompanion.utils.AppUtils.getBase64;

public class DownloadConnection {
    //for upload audio
    public interface ReceiverAudioUrlFromServer {
        void onReceiveAudioUrl(String serverUrl, Map<String, Integer> param);

        void onReceiveError(Exception error);
    }

    public static class ExecuteUploadFileToPhpServer extends AsyncTask<Void, Void, String> {
        private String pathFile;
        private Map<String, Integer> param;
        private ReceiverAudioUrlFromServer handler;

        public ExecuteUploadFileToPhpServer(ReceiverAudioUrlFromServer handler, String pathFile) {
            this.pathFile = pathFile;
            this.handler = handler;
            param = new HashMap<>();
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadAudioToPhpServer();
        }

        @Override
        protected void onPostExecute(String result) {

            if (this.handler != null) {
                this.handler.onReceiveAudioUrl(result, param);
            }
            super.onPostExecute(result);
        }

        private String uploadAudioToPhpServer() {

            StringBuilder res = new StringBuilder();
            try {
                // TODO Upload file here
                HttpURLConnection conn;
                DataOutputStream dos;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize;
                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    maxBufferSize = 1024 * 1024;
                } else {
                    maxBufferSize = 2048 * 2048;
                }
                File sourceFile = new File(pathFile);
                try {
//                    Bitmap preBitmap = loadPrescaledBitmap(pathFile);
//                    if (preBitmap != null) {
//                        param.put("width", preBitmap.getWidth());
//                        param.put("height", preBitmap.getHeight());
////                        Log.d("Sang","preBitmap width: " + preBitmap.getWidth() + ",  width:" + preBitmap.getHeight());
//                    }
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    String urlString = HttpRetrofitClientBase.Companion.getInstance().getBaseUrl() + AppConstant.API_UPLOAD;
                    urlString = urlString + "?" + AppConstant.API_PARAM_ACCESS_TOKEN + "=" + ApplicationSingleton.getInstance().getToken();
                    URL url = new URL(urlString);

                    // Open a HTTP connection to the URL
                    conn = getHttpURLConnection(url, boundary);

                    dos = new DataOutputStream(conn.getOutputStream());

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                    while (fileInputStream.read(buffer) != -1) {
//
//                    }
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    String abc = "Content-Disposition: form-data; name=\"" + "file" + "\";filename=\"" + pathFile + "\"";
                    dos.writeBytes(abc + lineEnd);
                    dos.writeBytes(getBase64(buffer));
                    dos.writeBytes(lineEnd);

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);

//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
////                    dos.writeBytes("Content-Disposition: form-data; name=\"" + AppConstant.VERSION + "\"" + lineEnd);
//                    dos.writeBytes("Content-Type: text/plain" + lineEnd);
//                    dos.writeBytes(lineEnd);
////                    dos.writeBytes(AppConstant.CURRENT_VERSION);
//                    dos.writeBytes(lineEnd);

//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
//                    dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.fileName + lineEnd);
//                    dos.write(FileUtils.readFileToByteArray(file));
//                    dos.writeBytes(lineEnd);
//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
////                    dos.writeBytes("Content-Disposition: form-data; name=\"" + AppConstant.PANELIST_ID + "\"" + lineEnd);
//                    dos.writeBytes("Content-Type: text/plain" + lineEnd);
//                    dos.writeBytes(lineEnd);
////                    dos.writeBytes(AppHelper.sp.getString(AppConstant.USER_ID, ""));
//                    dos.writeBytes(lineEnd);

//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
//                    dos.writeBytes("Content-Disposition: form-data; name=\"" + AppConstant.API_PARAM_ACCESS_TOKEN + "\"" + lineEnd);
//                    dos.writeBytes("Content-Type: text/plain" + lineEnd);
//                    dos.writeBytes(lineEnd);
//                    dos.writeBytes(ApplicationSingleton.getInstance().getToken());
//                    dos.writeBytes(lineEnd);


//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
////                    dos.writeBytes("Content-Disposition: form-data; name=\"" + AppConstant.LANGUAGE + "\"" + lineEnd);
//                    dos.writeBytes("Content-Type: text/plain" + lineEnd);
//                    dos.writeBytes(lineEnd);
////                    dos.writeBytes(AppHelper.sp.getString(AppConstant.CURRENT_LANGUAGE, ""));
//                    dos.writeBytes(lineEnd);

//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
////                    dos.writeBytes("Content-Disposition: form-data; name=\"" + AppConstant.ACCESS_KEY + "\"" + lineEnd);
//                    dos.writeBytes("Content-Type: text/plain" + lineEnd);
//                    dos.writeBytes(lineEnd);
////                    dos.writeBytes(AppHelper.sp.getString(AppConstant.ACCESS_KEY, ""));
//                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)

                    /* Checking response */
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(response));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            res.append(line);
                        }
                        response.close();

                        // Parse res
                        JSONObject objRes = new JSONObject(res.toString());
                        Log.d("Sang", "objRes: " + objRes);
//                        res = new StringBuilder(objRes.getString("url"));

//                    } else if (isErrorHttpException(conn.getResponseCode())) {
//                        handleError(AppHelper.getAppContext().getString(R.string.error_login));
                    }
                    Log.d("Sang", "conn: " + conn.getResponseMessage());
                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    e.printStackTrace();
//                    handleError(handler, AppUtils.getErrorMessage(e));
                    handler.onReceiveError(e);
//                    res = new StringBuilder(ERROR);
                }
            } catch (Exception e) {
                if (handler != null) {
                    handler.onReceiveError(e);
                }
                e.printStackTrace();
            }
            return res.toString();
        }
    }

    private static HttpURLConnection getHttpURLConnection(URL url, String boundary) throws IOException {
        // Open a HTTP connection to the URL
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true); // Allow Inputs
        conn.setDoOutput(true); // Allow Outputs
        conn.setUseCaches(false); // Don't use a Cached Copy

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

}
