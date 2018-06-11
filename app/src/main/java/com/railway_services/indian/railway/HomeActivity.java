package com.railway_services.indian.railway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryItemClick {
    AutoCompleteTextView pnrEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView recyclerView = findViewById(R.id.category);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new CategoryAdapter(this, HomeActivity.this));
        pnrEditText = findViewById(R.id.pnrEdittext);
        pnrEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {


                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        ) {
                    // Do your action
                    if (!TextUtils.isEmpty(pnrEditText.getText().toString())) {
                        String pnr = pnrEditText.getText().toString();
                        Intent intent = new Intent(HomeActivity.this, PnrActivity.class);
                        intent.putExtra("PNR_NUMBER", pnr);
                        startActivity(intent);
                    }
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(this, CancelledTrainActivity.class));
    }

    @Override
    public void onBackPressed() {
        //
    }
}
