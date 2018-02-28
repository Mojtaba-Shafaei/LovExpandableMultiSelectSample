package com.mojtaba_shafaei.android.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    List<Job> jobs = new ArrayList<>();
    jobs.add(new Job("1", "یک"));
    jobs.add(new Job("2", "دو"));
    jobs.add(new Job("3", "سه"));
    jobs.add(new Job("4", "چهار"));
    jobs.add(new Job("5", "پنج"));
    jobs.add(new Job("6", "شش"));
    jobs.add(new Job("7", "SEVEN"));
    jobs.add(new Job("8", "EIGHT"));
    jobs.add(new Job("9", "NINE"));
    jobs.add(new Job("10", "TEN"));
    jobs.add(new Job("11", "ELEVEN"));
    jobs.add(new Job("12", "TWELVE"));
    jobs.add(new Job("13", "THIRTEEN"));
    jobs.add(new Job("14", "FOURTEEN"));
    jobs.add(new Job("15", "FIFTEEN"));

    findViewById(R.id.btn_call_lov).setOnClickListener(
        view -> LovMultiSelect.startForResult(MainActivity.this,
            11,
            jobs,
            null));
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

  }
}