package uk.ac.imperial.icfootballfantasy.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 8/30/17.
 */

public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);

        final TextView emailTextView = (TextView) findViewById(R.id.forgot_password_email);
        final TextView passcodeTextView = (TextView) findViewById(R.id.forgot_password_passcode);

        final Button changePasswordButton = (Button) findViewById(R.id.forgot_password_change);
        final Button requestPasswordButton = (Button) findViewById(R.id.forgot_password_request);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailTextView.getText().toString();
                final String passcode = passcodeTextView.getText().toString();
                String message = "";
                if (!isEmailValid(email)) {
                    message += "Enter a valid email\n";
                }
                if (passcode.length() != 4) {
                    message += "Enter a 4 digit passcode\n";
                }
                if (message.equals("")) {
                    changePasswordButton.setText("");
                    changePasswordButton.setEnabled(false);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.forgot_password_change_progress);
                    progressBar.setVisibility(View.VISIBLE);
                    checkPasscodeDB(email, passcode);
                } else {
                    message = message.substring(0, message.length() - 1); //to get rid of last \n
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        requestPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailTextView.getText().toString();
                String message = "";
                if (!isEmailValid(email)) {
                    message += "Enter a valid email";
                }
                if (message.equals("")) {
                    requestPasswordButton.setText("");
                    requestPasswordButton.setEnabled(false);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.forgot_password_request_progress);
                    progressBar.setVisibility(View.VISIBLE);
                    requestPasswordToEmailDB(email);
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void requestPasswordToEmailDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/send_email.php?email=" + input[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    message = response.body().string();
                    if (message.equals("failure")) {
                        message = "Email doesn't exist";
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                Toast.makeText(getBaseContext(), message,
                        Toast.LENGTH_SHORT).show();
                //show text instead of progress
                Button changePassowordButton = (Button) findViewById(R.id.forgot_password_request);
                changePassowordButton.setText("Email me the passcode");
                changePassowordButton.setEnabled(true);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.forgot_password_request_progress);
                progressBar.setVisibility(View.GONE);
            }
        };

        asyncTask.execute(input);
    }

    private void checkPasscodeDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            String email;
            @Override
            protected String doInBackground(String... input) {
                String message;
                email = input[0];

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/check_passcode.php?email=" + input[0]
                        + "&passcode=" + Integer.parseInt(input[1]))
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    message = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("success")) {
                    Intent intent = new Intent(getBaseContext(), ChangePasswordActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                    //show text instead of progress
                    Button requestPasscodeButton = (Button) findViewById(R.id.forgot_password_change);
                    requestPasscodeButton.setText("Check Passcode");
                    requestPasscodeButton.setEnabled(true);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.forgot_password_change_progress);
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        asyncTask.execute(input);
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
