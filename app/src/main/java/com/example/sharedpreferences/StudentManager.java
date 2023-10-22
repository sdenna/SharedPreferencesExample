package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private static final String PREF_NAME = "StudentPrefs";
    private static final String KEY_STUDENT = "student";
    private static final String KEY_STUDENT_LIST = "student_list";



    // These methods make it so the app can have a List of Student objects
    public static void saveStudent(Context context, Student student) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // this is done so that new data is added to existing data
        List<Student> pastStudents = getStudents(context);
        pastStudents.add(student);

        Gson gson = new Gson();
        String studentListJson = gson.toJson(pastStudents);

        editor.putString(KEY_STUDENT_LIST, studentListJson);
        editor.apply();
    }
    public static List<Student> getStudents(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String studentListJson = sharedPreferences.getString(KEY_STUDENT_LIST, null);

        if (studentListJson != null) {
            Gson gson = new Gson();
            Type studentListType = new TypeToken<List<Student>>() {}.getType();
            return gson.fromJson(studentListJson, studentListType);
        } else {
            return new ArrayList<>(); // Return an empty list if no data found
        }
    }

    public static void eraseAllData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<Student> emptyList = new ArrayList<>();

        Gson gson = new Gson();
        String studentListJson = gson.toJson(emptyList);

        editor.putString(KEY_STUDENT_LIST, studentListJson);
        editor.apply();


    }

    /*

    // These two methods work for saving/retrieving ONE student.  Not an array though
    public static void saveStudent(Context context, Student student) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String studentJson = gson.toJson(student);

        editor.putString(KEY_STUDENT, studentJson);
        editor.apply();
    }

    public static Student getStudent(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String studentJson = sharedPreferences.getString(KEY_STUDENT, null);

        if (studentJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(studentJson, Student.class);
        } else {
            return null; // No student data found in SharedPreferences
        }
    }

*/
}

