package com.activity.devibar.cartogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();


    }


    @Override
    public void handleResult (Result result){

        try{



            String message = result.getText();

            StringTokenizer itemToken = new StringTokenizer(message, "|||");

            String[] itemContent = new String[3];

            while (itemToken.hasMoreTokens()) {

                itemContent[0] = itemToken.nextElement().toString();
                itemContent[1] = itemToken.nextElement().toString();
                itemContent[2] = itemToken.nextElement().toString();

            }

            if (Integer.parseInt(itemContent[1])>=1 && Double.parseDouble(itemContent[2])>=1){

                Intent intent = new Intent();
                intent.putExtra("RESULT", message);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "SUCCESS!", Toast.LENGTH_SHORT).show();


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(300);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };

                thread.start();
            }
            else {
                Toast.makeText(this, "INVALID INPUT", Toast.LENGTH_SHORT).show();
                mScannerView.stopCamera();
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            }


        }catch(Exception e){
            Toast.makeText(this,"INVALID FORMAT",Toast.LENGTH_SHORT).show();
            mScannerView.stopCamera();
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }




    }

    @Override
    protected void onPause () {
        super.onPause();
        mScannerView.stopCamera();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mScannerView.startCamera();
    }
}


