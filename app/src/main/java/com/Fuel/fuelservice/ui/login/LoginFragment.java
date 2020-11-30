package com.Fuel.fuelservice.ui.login;

import android.os.Bundle;
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

import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.preference.UserPrefs;
import com.Fuel.fuelservice.ui.home.FuelStationFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    EditText editTextUsername, editTextPwd;
    private User user = new User();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsernameOnLogin);
        editTextPwd = view.findViewById(R.id.editTextTextPassword);

        Button loginBtn = (Button) view.findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.loginbtn:
                        userLogin();
                        break;
                }
            }
        });


        return view;
    }

    private void userLogin() {
        String uid = editTextUsername.getText().toString().trim();
        String pwd = editTextPwd.getText().toString().trim();

        final UserPrefs userPrefs = new UserPrefs(getContext());

        if (uid.isEmpty()) {
            editTextUsername.setError("Please enter a valid username");
            editTextUsername.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            editTextPwd.setError("Please enter your password");
            editTextPwd.requestFocus();
            return;
        }


        Call<ResponseBody> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .userLogin(uid, pwd);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Fragment newFragment = new FuelStationFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

                    try {
                        userPrefs.setToken(response.body().string());
                        getActivity().recreate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_LONG).show();
                    System.out.println("22222222222222222222222222");
                    System.out.println(getActivity());
                    fragmentTransaction.replace(R.id.fragment_contatiner, newFragment).commit();


                } else {
                    Toast.makeText(getActivity(), "Login Failed, please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
