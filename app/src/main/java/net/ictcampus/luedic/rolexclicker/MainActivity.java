package net.ictcampus.luedic.rolexclicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            TextView tv = (TextView) findViewById(R.id.textView);
            long score = 0;
            @Override
            public void onClick(View v) {
                score+=1;
                tv.setText("Score: "+score);
            }
        });
        Button btnShop = (Button) findViewById(R.id.buttonshop);
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

