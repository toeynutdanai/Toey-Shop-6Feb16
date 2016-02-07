package com.example.teay.myshop;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage objMyManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BindWidget ผูกตัวแปร
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

        //Request Database
        objMyManage = new MyManage(this);

        //Test Add Value
        //testAddValue();

        //Clean data
        cleanData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }   // Main Method

    public void clickLogin(View view) {

        //Check Space trim ตัดช่องว่าง
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordEditText.equals("")) {

            //Have Space มีช่องว่าง
            Toast.makeText(MainActivity.this,"กรุณากรอกให้ครบสาดดดดด", Toast.LENGTH_SHORT).show();

        } else {

            //No Spacr

        } // if equals=ว่างเปล่า

    } //clickLogin


    private void synJSONtoSQLite() {

        //Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTable = 1;
        String tag = "Restaurant";

        while (intTable <= 2) {

            //1. Create InputStream
            InputStream objInputStream = null;
            String strURLuser = "http://swiftcodingthai.com/6feb/php_get_data_toey.php";
            String strURLfood = "http://swiftcodingthai.com/6feb/php_get_data_food.php";
            HttpPost objHttpPost = null;

            try {

                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTable) {
                    case 1:
                        objHttpPost = new HttpPost(strURLuser);
                        break;
                    case 2:
                        objHttpPost = new HttpPost(strURLfood);
                        break;
                    default:
                        break;
                }   // switch

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();


            } catch (Exception e) {
                Log.d(tag, "InputStream ==> " + e.toString());
            }


            //2. Create JSON String
            String strJSON = null;

            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = objBufferedReader.readLine()) != null ) {
                    objStringBuilder.append(strLine);
                }   // while

                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d(tag, "strJSON ==> " + e.toString());
            }

            //3. Update SQLite
            try {

                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i=0;i<objJsonArray.length();i++) {

                    JSONObject jsonObject = objJsonArray.getJSONObject(i);

                    switch (intTable) {
                        case 1:

                            //Get Value From userTABLE
                            String strUser = jsonObject.getString(MyManage.column_user);
                            String strPassword = jsonObject.getString(MyManage.column_pass);
                            String strName = jsonObject.getString(MyManage.column_name);

                            objMyManage.addNewValue(0, strUser, strPassword, strName);

                            break;
                        case 2:

                            //Get Value From foodTABLE
                            String strFood = jsonObject.getString(MyManage.column_food);
                            String strPrice = jsonObject.getString(MyManage.column_price);
                            String strSource = jsonObject.getString(MyManage.column_source);

                            objMyManage.addNewValue(1, strFood, strPrice, strSource);

                            break;
                    }   //switch

                }   // for


            } catch (Exception e) {
                Log.d(tag, "Update ==> " + e.toString());
            }



            intTable += 1;
        }   // while
    }   // synJSONtoSQLite

    private void cleanData() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(MyManage.food_TABLE, null, null);
        objSqLiteDatabase.delete(MyManage.user_TABLE, null, null);

    }   //cleanData

    private void testAddValue() {

        for (int i=0;i<=1;i++) {
            objMyManage.addNewValue(i, "test1", "test2", "test3");
        }   //for

    }   // testAddValue

}   // Main Class