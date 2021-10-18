package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import cmpt276.assignments.assignment3.R;

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

        courseTextView.setMovementMethod(LinkMovementMethod.getInstance());
        gridImgTextView.setMovementMethod(LinkMovementMethod.getInstance());
        bitCoinImgTextView.setMovementMethod(LinkMovementMethod.getInstance());
        cyberBGTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }
}