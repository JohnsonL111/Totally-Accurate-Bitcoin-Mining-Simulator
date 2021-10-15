package cmpt276.assignments.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cmpt276.assignments.assignment3.ui.HelpActivity;
import cmpt276.assignments.assignment3.ui.MainMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMenuButton();
    }

    private void setMenuButton() {
        Button menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // make intent to launch HelpActivity
                Intent helpIntent = MainMenuActivity.makeIntent(MainActivity.this);
                startActivity(helpIntent);
            }
        });
    }
}