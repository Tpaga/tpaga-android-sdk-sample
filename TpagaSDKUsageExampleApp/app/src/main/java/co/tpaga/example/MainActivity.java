package co.tpaga.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_start_example_activity).setOnClickListener(this);
        findViewById(R.id.button_start_customized_view_example_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_example_activity:
                startActivity(new Intent(this, AddCreditCardExampleActivity.class));
                break;
            case R.id.button_start_customized_view_example_activity:
                startActivity(new Intent(this, AddCreditCardCustomizedViewExampleActivity.class));
                break;
        }
    }
}
