package ru.mirea.solovieva.mireaproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("файлы").setMessage("что сделать?").setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FilesFragment) getParentFragment()).onOpenClicked();
                        dialog.cancel();
                    }
                })
                .setNeutralButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FilesFragment) getParentFragment()).onSaveClicked();
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}