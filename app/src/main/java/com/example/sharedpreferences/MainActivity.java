package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Make references to XML elements
    EditText name, age;
    TextView data;
    LinearLayoutCompat mainLayout;
    List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameEditText);
        age = findViewById(R.id.ageEditText);
        data = findViewById(R.id.currentDataTextView);

        //set view to layout
        mainLayout = findViewById(R.id.mainLayout);

        // prepare ArrayList for Student data
        studentList = new ArrayList<>();

        Log.d("Denna", "onCreate, studentList.size = " + studentList.size());

    }

    public void saveInfo(View v){
        closeKeyboard();
        String snackbarString = "";
        // get data and create a Student object
        try {
            String nameVal = name.getText().toString();
            int ageVal = Integer.parseInt(age.getText().toString());
            Student student = new Student(nameVal, ageVal);

            // save new student
            StudentManager.saveStudent(this, student);
            snackbarString = "Info Saved";
        }
        catch (Exception e) {
            snackbarString = "Missing data";
        }

        Snackbar snackbar = Snackbar.make(v, snackbarString, Snackbar.LENGTH_LONG);
        snackbar.show();
        clearInfo();

    }

    public void eraseStoredMemory(View v) {
        StudentManager.eraseAllData(this);
        studentList = new ArrayList<>();
        data.setText("");
    }

    public void clearInfo(){
        name.setText("");
        age.setText("");
    }

    public void refreshInfo(View v) {
        // Retrieving the list of students
        List<Student> retrievedStudents = StudentManager.getStudents(this);
        Log.d("Denna", "Num students: " + retrievedStudents.size());
        String dataForTextView = "";

        for (Student student : retrievedStudents) {
            // Use each student object in the list
            Log.d("Denna", student.getName() + " " + student.getAge());
            dataForTextView += student.getName() + ", " + student.getAge() + "\n";

            Snackbar snackbar = Snackbar.make(v, "Info retrieved", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        data.setText(dataForTextView);
    }

    /*
  How to close the keyboard
  Source: https://www.geeksforgeeks.org/how-to-programmatically-hide-android-soft-keyboard/
   */
    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }
}