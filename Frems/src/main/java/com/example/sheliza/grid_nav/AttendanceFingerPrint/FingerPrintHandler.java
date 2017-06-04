package com.example.sheliza.grid_nav.AttendanceFingerPrint;

/**
 * Created by Sheliza on 17-05-2017.
 */



        import android.Manifest;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.hardware.fingerprint.FingerprintManager;
        import android.os.CancellationSignal;
        import android.support.v4.app.ActivityCompat;
        import android.widget.Toast;

/**
 * Created by himanshoo on 5/2/17.
 */

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback  {
    private CancellationSignal cancellationSignal;
    private Context appContext;
    //Student student = new Student();

    // MainActivity activity = new MainActivity();
//    RequestQueue requestQueue = Volley.newRequestQueue(appContext);



    public FingerPrintHandler(Context context) {
        appContext = context;
    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();


        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(appContext,
                errString,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(appContext,
                helpString,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(appContext,
                "Authentication succeeded.",
                Toast.LENGTH_SHORT).show();


        Intent i = new Intent(appContext, SuceedActivity.class);
        appContext.startActivity(i);
        ((Activity) appContext).finish();      //Finish MainActivity
        // volley operation*/



    }

}