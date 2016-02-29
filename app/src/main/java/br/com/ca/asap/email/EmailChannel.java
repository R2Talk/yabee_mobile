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

    /**
     * Constructor
     *
     */
    public EmailChannel() {
    }

    /**
     * callEmailApp
     *
     * Receive email recipients and message text to acitivate, with use of intent, the existing applications that listen to ACTION_SEND.
     * It's primary intention is activate an email application to send plain text as message.
     *
     * @param context
     * @param to
     * @param cc
     * @param subject
     * @param text
     */
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
