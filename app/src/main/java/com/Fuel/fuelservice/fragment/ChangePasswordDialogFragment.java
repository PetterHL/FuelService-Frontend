package com.Fuel.fuelservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.preference.UserPrefs;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialogFragment extends DialogFragment{
    Button button;
    EditText uidText, newPass;
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.changepassword_dialog_fragment, container, false);
        button = view.findViewById(R.id.changePasswordButton);
        uidText = view.findViewById(R.id.ETuid);
        newPass = view.findViewById(R.id.ETNewPass);



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uidTextString = uidText.getText().toString();
                String newPassString = newPass.getText().toString();
                UserPrefs userPrefs = new UserPrefs((getContext()));
                String token = "Bearer " + userPrefs.getToken();
                Call<ResponseBody> call = ApiClient
                        .getSINGLETON(false)
                        .getApi()
                        .changePassword(token,uidTextString,newPassString );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                assert response.body() != null;
                                response.body().string();
                                Toast.makeText(getContext(),"Your password has been changed", Toast.LENGTH_SHORT).show();
                                dismiss();
                                System.out.println(response);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }


                });
            }
        });

        return view;
    }


}


