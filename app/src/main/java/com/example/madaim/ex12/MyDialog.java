package com.example.madaim.ex12;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Kai on 3/31/2017.
 */

public class MyDialog extends DialogFragment {
    private int requestCode;
    public final static int RESET_DIALOG = 1;
    public final static int EXIT_DIALOG = 2;
    private ResultsListener listener;

    public static MyDialog newInstance(int requestCode) {
        Bundle args = new Bundle();
        MyDialog fragment = new MyDialog();
        args.putInt("rc", requestCode);
        fragment.setArguments(args);
        return fragment;
    }

    private AlertDialog.Builder buildResetDialog(){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Reset")
                .setMessage(R.string.reset)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onFinishedDialog(requestCode, "ok");

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }
    private AlertDialog.Builder buildExitDialog(){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Exit")
                .setMessage(R.string.reset)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onFinishedDialog(requestCode, "ok");

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.requestCode = getArguments().getInt("rc");
        if (requestCode == RESET_DIALOG) {
            return buildResetDialog().create();
        }
        else
            return buildExitDialog().create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            this.listener = (ResultsListener)activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException("Hosting activity must implement the interface ResultsListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ResultsListener{
        public void onFinishedDialog(int requestCode, Object results);
    }
}
