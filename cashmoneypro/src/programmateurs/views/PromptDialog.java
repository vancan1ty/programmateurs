package programmateurs.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

/**
 * helper for Prompt-Dialog creation.
 */
//CHECKSTYLE:OFF
public abstract class PromptDialog extends AlertDialog.Builder implements
		OnClickListener {
	
    public final EditText input;
    //CHECKSTYLE:ON

	/**
	 * Prompt Dialog.
	 * 
	 * @param context context
	 * @param title title
	 * @param message message
	 */
    public PromptDialog(Context context, String title, String message) {
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
	 * @param dialog dialog
	 */
    public void onCancelClicked(DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (onOkClicked(input.getText().toString())) {
                dialog.dismiss();
            }
        }
        else {
            onCancelClicked(dialog);
        }
    }

    /**
     * Get text.
     * 
     * @return text text
     */
    public String getText() {
        return input.getText().toString();
    }

	/**
	 * called when "ok" pressed.
	 * 
	 * @param input input
	 * @return true, if the dialog should be closed. false, if not.
	 */
    //CHECKSTYLE:OFF
    public abstract boolean onOkClicked(String input);
    //CHECKSTYLE:ON
}
