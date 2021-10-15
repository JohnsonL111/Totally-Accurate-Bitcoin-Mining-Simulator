package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cmpt276.assignments.assignment3.R;
import cmpt276.assignments.assignment3.model.OptionsManager;

public class MainMenuActivity extends AppCompatActivity {
    private OptionsManager options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        options = OptionsManager.getInstance();

        // initialize default game values
        options.setNumMines(OptionsActivity.getNumMines(this));
        options.setBoardDimensionX(OptionsActivity.getBoardDimensionX(this));
        options.setBoardDimensionY(OptionsActivity.getBoardDimensionY(this));

        System.out.println(options.getNumMines());
        System.out.println(options.getBoardDimensionX());
        System.out.println(options.getGetBoardDimensionY());

        setHelpButton();
        setOptionsButton();
    }

    // https://stackoverflow.com/questions/18404271/android-back-button-to-specific-activity
    @Override
    public void onBackPressed() {
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

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenuActivity.class);
    }

}