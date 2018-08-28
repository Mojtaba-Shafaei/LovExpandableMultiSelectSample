package com.mojtaba_shafaei.android.app;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.mojtaba_shafaei.android.LovExpandableMultiSelect;
import com.mojtaba_shafaei.android.LovExpandableMultiSelect.Item;
import com.mojtaba_shafaei.android.Property;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    List<Job> defaults = new ArrayList<>();
    defaults.add(new Job(1, "یک"));
    defaults.add(new Job(5, "پنجمین آیتم از لیست موجود این میباشد"));
    defaults.add(new Job(9, "NINE"));
    defaults.add(new Job(14, "FOURTEEN"));

    Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

    View btn = findViewById(R.id.btn_call_lov);

    btn.setOnClickListener(
        ignored -> LovExpandableMultiSelect.start(getSupportFragmentManager()
            , typeface
            , typeface
            , Property.newBuilder()
                .withBtnOkText("باشه")
                .withButtonOkTextColorState(ColorStateList.valueOf(0xFFFFFFFF))
                .withButtonOkBackgroundTint(
                    ContextCompat.getColorStateList(this, R.color.colors_btn))
                .withChipBackgroundColor(ColorStateList.valueOf(0xFF673695))
                .withChipStrokeWidth(2)
                .withChipStrokeColor(ColorStateList.valueOf(0xFFD1D1D1))
                .withChipTextColor(ColorStateList.valueOf(0xFFFFFFFF))
                .withChipTextSize(24)
                .withCloseIconTintColor(ColorStateList.valueOf(0xFFFFFFFF))
                .withMinLimit(1)
                .withMaxLimit(3)
                .withCategoryTextColor(ColorStateList.valueOf(0xFF673695))
                .build()

            , new ArrayList<>(new JobFetcher().fetch())
            , new ArrayList<>(defaults)
            , items -> {
              String result = "";
              for (Item dd : items) {
                Log.d("MainActivity", "onActivityResult: " + dd);
                result += dd.toString() + "\n\n";
              }
              ((TextView) findViewById(R.id.tvResult)).setText(result);
            }
            , cancelListener -> {
              Log.d("MainActivity", "cancelled");
            }
            , dismissListener -> {
              Log.d("MainActivity", "dismissed");
            }
        ));
  }
}
