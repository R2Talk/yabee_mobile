package br.com.ca.asap.demo;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import br.com.ca.shareview.R;

/**
 * DemoLogin
 */
public class DemoLogin {
    String demoMessage;

    public DemoLogin(String demoMessage) {

        this.demoMessage = demoMessage;
    }

    public void doDemoLogin(){
        //Demo login toast message
        Log.i("DemoLogin",demoMessage);
    }
}
