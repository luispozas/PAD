package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    EditText mEditTextX;
    EditText mEditTextY;
    Button mButton;
    Integer mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Se ha llamado a onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextX = findViewById(R.id.number1);
        mEditTextY = findViewById(R.id.number2);
        mButton = findViewById(R.id.button);
    }

    public void addXandY(View v){
        Log.d(TAG, "Se ha pulsado el boton y se ha llamado a addXandY.");

        try {
            if(!mEditTextX.getText().toString().isEmpty() && !mEditTextY.getText().toString().isEmpty() &&
                    !mEditTextX.getText().toString().contains(".") && !mEditTextY.getText().toString().contains(".")){

                Intent resultIntent = new Intent(getApplicationContext(), CalculatorResultActivity.class);
                Integer x = Integer.parseInt(mEditTextX.getText().toString());
                Integer y = Integer.parseInt(mEditTextY.getText().toString());
                mResult =  Calculator.add(x, y);
                resultIntent.putExtra("resultant", mResult.toString());
                this.startActivity(resultIntent);
            }
        }catch(NumberFormatException e){
            Log.e(TAG, "Numero introducido demasiado grande.");
            Toast.makeText(getApplicationContext(), "Numero introducido demasiado grande.", Toast.LENGTH_SHORT).show();
        }

    }
}