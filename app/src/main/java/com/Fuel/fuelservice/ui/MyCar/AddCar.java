package com.Fuel.fuelservice.ui.MyCar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.ui.MyCar.MyCars;
import com.Fuel.fuelservice.CarRecViewAdapter;
import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.preference.UserPrefs;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.function.ToDoubleBiFunction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCar extends BottomSheetDialogFragment {

    static EditText regnumber_field;
    static EditText field_manufacturer;
    static EditText field_model;
    static EditText field_fuelUsage;
    static RadioButton petrol_radio, diesel_radio;
    static RadioGroup radioGroup;

    boolean petrol;

    private User user = new User();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addcar_sheet, container, false);

        regnumber_field = view.findViewById(R.id.RegNumber_field);
        field_manufacturer = view.findViewById(R.id.Field_manufacturer);
        field_model = view.findViewById(R.id.Field_model);
        field_fuelUsage = view.findViewById(R.id.Field_fuelUsage);


        radioGroup = view.findViewById(R.id.radioGroup);
        petrol_radio = view.findViewById(R.id.Petrol_radio);
        diesel_radio = view.findViewById(R.id.Diesel_radio);

        radioGroup = view.findViewById(R.id.RadioGroup);

        Button searchCar = view.findViewById(R.id.Search_car_button);
        Button registerCar = view.findViewById(R.id.Register_car_button);



        searchCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

        registerCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String RegNumber = regnumber_field.getText().toString();
                String manufacturer = field_manufacturer.getText().toString();
                String model = field_model.getText().toString();
                String fuelUsage = field_fuelUsage.getText().toString();



                if (RegNumber.isEmpty()) {
                    regnumber_field.setError("Please enter a registration number");
                    regnumber_field.requestFocus();
                    return;
                }

                if (manufacturer.isEmpty()) {
                    field_manufacturer.setError("Please enter an manufacturer!");
                    field_manufacturer.requestFocus();
                    return;
                }

                if (model.isEmpty()) {
                    field_model.setError("Please enter a model");
                    field_model.requestFocus();
                    return;
                }

                if (fuelUsage.isEmpty()) {
                    field_fuelUsage.setError("Please enter the fuel consumption");
                    field_fuelUsage.requestFocus();
                    return;
                }

                if(petrol_radio.isChecked()) {
                    boolean petrol = true;
                } else if(diesel_radio.isChecked()) {
                    boolean petrol = false;
                } else {
                    petrol_radio.setError("Please choose fueltype");
                    petrol_radio.requestFocus();
                    return;
                }

                double fuelUsage_double = setDouble(fuelUsage);

                if (fuelUsage_double < 0) {
                    field_fuelUsage.setError("Fuel consumption can not be a negative number");
                    field_fuelUsage.requestFocus();
                }

                addCar(RegNumber, manufacturer, model, petrol, fuelUsage_double);
            }
        });

        return view;
    }

    /**
     * Sends get request to reg number api
     */
    public void sendRequest() {

        //param 1 = the registration number
        //param 2 = username in get request (User never change or see this)
        Call<ResponseBody> call = ApiClient
                .getSINGLETON(true)
                .getApi()
                .getCar(regnumber_field.getText().toString(), "regbil1212");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Parse(response.body().string());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }

    /**
     * @param RegNumber
     * @param manufacturer
     * @param model
     * @param petrol
     * @param fuelUsage
     */
    public void addCar(String RegNumber, String manufacturer, String model, boolean petrol, double fuelUsage) {

        //Gets the token for the user
        UserPrefs userPrefs = new UserPrefs(getContext());
        String token = "Bearer " + userPrefs.getToken();

        Call<ResponseBody> call = ApiClient
                .getSINGLETON(false)
                .getApi()
                .addCar(token, RegNumber, manufacturer, model, petrol, fuelUsage);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    dismiss();
                    Toast.makeText(getActivity(), "Car added!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @param xml
     * gets a xml and creates a new document
     * picks out vehicleJson part of xml
     */
    public void Parse(String xml) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource is;

        try {
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName("vehicleJson");

            String xml_Item = list.item(0).getTextContent();

            parseNews(xml_Item);

        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public static void parseNews(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        parseNewsJsonObject(jsonElement.getAsJsonObject());
    }

    /**
     * @param object
     * Gets the different values we neeed from the json object
     * Auto fills the fields the user can fill out with the values
     */
    private static void parseNewsJsonObject(JsonObject object){

        JsonObject carInfo = object.getAsJsonObject("ExtendedInformation");

        //sets variables from values from json
        String manufacturer = carInfo.get("Name").getAsString();
        String model = carInfo.get("modell").getAsString();
        int petrol = carInfo.get("drivst").getAsInt();
        double fuelUsage = carInfo.get("drivstoff-forbruk").getAsDouble();

        //Converts it to l/10km
        fuelUsage = fuelUsage / 100;

        //auto fills fields with the values
        field_manufacturer.setText(manufacturer);
        field_model.setText(model);
        field_fuelUsage.setText(String.valueOf(fuelUsage));

        //checks and sets if car uses petrol or diesel
        if(petrol == 1) {
            petrol_radio.setChecked(true);
        } else if(petrol == 2) {
            diesel_radio.setChecked(true);
        }

    }


    /**
     * @param fuelUsage
     * @return a double with 2 decimals
     */
    private static double setDouble(String fuelUsage) {

        double fuelUsage_double = Double.parseDouble(fuelUsage);

        DecimalFormat df = new DecimalFormat("#.00");
        String fuelUsage_String = df.format(fuelUsage_double);

        double fuelUsage_decimal = Double.parseDouble(fuelUsage_String);

        return fuelUsage_decimal;
    }

}