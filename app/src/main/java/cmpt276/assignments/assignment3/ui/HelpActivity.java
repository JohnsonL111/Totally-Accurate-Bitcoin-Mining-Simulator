package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import cmpt276.assignments.assignment3.R;

/**
 * Sets help screen activity and deals with the hyperlinks for citations.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Sets the hyperlink functionality.
        TextView courseTextView = findViewById(R.id.courseInfo);
        TextView gridImgTextView = findViewById(R.id.gridImgLink);
        TextView bitCoinImgTextView = findViewById(R.id.bitCoinLogoLink);
        TextView cyberBGTextView = findViewById(R.id.cyberBGLink);
        TextView bitCoinFoundTextView = findViewById(R.id.bitcoinFoundLink);
        TextView scanSoundTextView = findViewById(R.id.scanSoundLink);

        courseTextView.setMovementMethod(LinkMovementMethod.getInstance());
        gridImgTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bitCoinImgTextView.setMovementMethod(LinkMovementMethod.getInstance());
        cyberBGTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bitCoinFoundTextView.setMovementMethod(LinkMovementMethod.getInstance());
        scanSoundTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }
}