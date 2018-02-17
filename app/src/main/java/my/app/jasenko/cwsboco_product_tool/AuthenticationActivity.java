package my.app.jasenko.cwsboco_product_tool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class AuthenticationActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    private EditText etAuthentication;
    private CheckBox cbRememberKey;
    private Button buttAuthenticate;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String KEY_REMEMBER = "remember";
    private final String PREF_NAME = "prefs";
    private final String SECRET_KEY = "test";
    private String sAuthentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        etAuthentication = (EditText) findViewById(R.id.etAuthentication);
        buttAuthenticate = (Button) findViewById(R.id.buttAuthenticate);
        cbRememberKey = (CheckBox) findViewById(R.id.cbRememberKey);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            cbRememberKey.setChecked(true);
        else
            cbRememberKey.setChecked(false);

        etAuthentication.setText(sharedPreferences.getString(SECRET_KEY, ""));
        etAuthentication.addTextChangedListener(this);
        cbRememberKey.setOnCheckedChangeListener(this);

        buttAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sAuthentication = etAuthentication.getText().toString();
                if(sAuthentication.equals(SECRET_KEY))
                {
                    Toast.makeText(AuthenticationActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(AuthenticationActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
                else
                    Toast.makeText(AuthenticationActivity.this, "Authentication failed, incorrect secret key.", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(cbRememberKey.isChecked()){
            editor.putString(SECRET_KEY, etAuthentication.getText().toString());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(SECRET_KEY);
            editor.apply();
        }
    }
}


//TODO before uploading the app in APP STORE change secret key to Key!802