package br.com.ca.asap.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import br.com.ca.shareview.R;

/**
 * EmailChannel
 *
 */
public class EmailChannel {

    public EmailChannel() {
    }

    public void callEmailApp(Context context,
                             String to,
                             String cc,
                             String subject,
                             String text){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getResources().getString(R.string.sendEmail)));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, context.getResources().getString(R.string.missingEmailClient), Toast.LENGTH_SHORT).show();
        }
    }

}
