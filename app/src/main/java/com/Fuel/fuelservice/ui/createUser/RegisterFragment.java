package com.Fuel.fuelservice.ui.createUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.ui.login.LoginFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText editEmail;
    private EditText editUsername;
    private EditText editPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create, container, false);

        editEmail = view.findViewById(R.id.editTextEmail);
        editUsername = view.findViewById(R.id.editTextUsername);
        editPassword = view.findViewById(R.id.editTextPassword);

        Button regButton = (Button) view.findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.regButton:
                        userRegister();
                        break;
                }
            }

        });

        return view;
    }

    private void userRegister() {
        final String email = editEmail.getText().toString();
        String uid = editUsername.getText().toString();
        String pwd = editPassword.getText().toString();


        if (uid.isEmpty()) {
            editUsername.setError("Please enter a username");
            editUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Please enter an email!");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email!");
            editEmail.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            editPassword.setError("Please enter a password");
            editPassword.requestFocus();
            return;
        }


        // User registration using api call
        Call<ResponseBody> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .createUser(email, uid, pwd);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Activity mActivity = getActivity();
                    Fragment newFragment = new LoginFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();


                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            response.body().string();
                            Toast.makeText(mActivity, "Account Created! Login to buy and sell items!", Toast.LENGTH_SHORT).show();
                            fragmentTransaction.replace(R.id.fragment_contatiner, newFragment).commit();
                        }
                    } else {
                        Toast.makeText(mActivity, "Something went wrong, please try again.", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Activity mActivity = getActivity();
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
