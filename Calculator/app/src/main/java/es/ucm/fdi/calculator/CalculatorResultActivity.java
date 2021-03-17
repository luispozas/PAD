package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {

    TextView mResultantText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);

        String result = getIntent().getExtras().getString("resultant");

        mResultantText = findViewById(R.id.result);
        mResultantText.setText(result);
    }
}