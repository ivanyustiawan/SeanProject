package com.example.infosys.seanproject.Widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.example.infosys.seanproject.R;

public class CustomDialog extends Dialog{
    public static CustomDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static CustomDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static CustomDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static CustomDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_progress_bar);
        ((TextView)dialog.getWindow().findViewById(R.id.textView_message)).setText(message);
        try {
            dialog.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dialog;
    }

    public CustomDialog(Context context) {
        super(context);
    }
}
