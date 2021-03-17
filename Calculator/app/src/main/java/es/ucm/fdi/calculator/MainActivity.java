package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextX;
    EditText mEditTextY;
    Button mButton;
    Integer mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextX = findViewById(R.id.number1);
        mEditTextY = findViewById(R.id.number2);
        mButton = findViewById(R.id.button);
    }

    public void addXandY(View v){
        if(!mEditTextX.getText().toString().isEmpty() && !mEditTextY.getText().toString().isEmpty() &&
           !mEditTextX.getText().toString().contains(".") && !mEditTextY.getText().toString().contains(".")){

            Intent resultIntent = new Intent(getApplicationContext(), CalculatorResultActivity.class);
            Integer x = Integer.parseInt(mEditTextX.getText().toString());
            Integer y = Integer.parseInt(mEditTextY.getText().toString());
            mResult =  Calculator.add(x, y);
            resultIntent.putExtra("resultant", mResult.toString());
            this.startActivity(resultIntent);
        }
    }
}