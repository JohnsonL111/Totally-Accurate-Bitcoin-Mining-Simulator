package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.cert.PKIXRevocationChecker;

import cmpt276.assignments.assignment3.MainActivity;
import cmpt276.assignments.assignment3.R;
import cmpt276.assignments.assignment3.model.Options;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setHelpButton();
        setOptionsButton();

    }

    private void setOptionsButton() {
        Button optionsBtn = findViewById(R.id.optionsBtn);
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionsIntent = OptionsActivity.makeIntent(MainMenuActivity.this);
                startActivity(optionsIntent);
            }
        });
    }

    // https://stackoverflow.com/questions/18404271/android-back-button-to-specific-activity
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        // https://developer.android.com/reference/android/app/Activity
        super.moveTaskToBack(true);
    }

    private void setHelpButton() {
        Button helpBtn = findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // make intent to launch HelpActivity
                Intent helpIntent = HelpActivity.makeIntent(MainMenuActivity.this);
                startActivity(helpIntent);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenuActivity.class);
    }


}