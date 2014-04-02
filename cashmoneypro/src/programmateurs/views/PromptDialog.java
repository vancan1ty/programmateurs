package programmateurs.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

/**
 * helper for Prompt-Dialog creation
 */
public abstract class PromptDialog extends AlertDialog.Builder implements
        OnClickListener {
    public final EditText input;

    /**
     * @param context
     */
    public PromptDialog(final Context context, final String title, final String message) {
        super(context);
        setTitle(title);
        setMessage(message);

        input = new EditText(context);
        setView(input);

        setPositiveButton("ok", this);
        setNegativeButton("cancel", this);
    }

    /**
     * will be called when "cancel" pressed. closes the dialog. can be
     * overridden.
     * 
     * @param dialog
     */
    public final void onCancelClicked(final DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public final void onClick(final DialogInterface dialog, final int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (onOkClicked(input.getText().toString())) {
                dialog.dismiss();
            }
        } else {
            onCancelClicked(dialog);
        }
    }

    public final String getText() {
        return input.getText().toString();
    }

    /**
     * called when "ok" pressed.
     * 
     * @param input
     * @return true, if the dialog should be closed. false, if not.
     */
    public abstract boolean onOkClicked(String input);
}
