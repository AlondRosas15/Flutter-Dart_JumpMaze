package edu.gettysburg.cs.jumpmaze;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AboutActivity extends MyActivity {
        
        //Buttons that move back and forth in the tutorial
        private ImageButton homeButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_about);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                homeButton = (ImageButton) findViewById(R.id.home);
                
                homeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                                toHomePage();
                        }
                });

                
                //https://github.com/tneller/jump-maze-android.git
        }
    	
    	
    	

}