package com.example.azizrafsanjani.numericals.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.azizrafsanjani.numericals.R;

public class OperationListDialog extends Dialog {
    public OperationListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_goto_operation);

        //initialize all view items
        onInit();
    }

    private void onInit() {
        String[] operationStringList = getContext().getResources().getStringArray(R.array.main_menu_legacy);
        ListView operationList = findViewById(R.id.operationList);
        ArrayAdapter<String> operationListAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item,
                operationStringList);

        operationList.setAdapter(operationListAdapter);
    }
}
