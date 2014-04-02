package programmateurs.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * Helper for creating dialogs that prompt for confirmation. Presents text with
 * "ok" and "cancel" options. (basically stolen content from PromptDialog, but
 * without the EditText)
 * 
 * @author Justin
 * @version 0.1
 */
public abstract class ConfirmDialog extends AlertDialog.Builder implements
        OnClickListener {

    /**
     * Creates new ConfirDialog for given context using provided title and
     * message.
     * @param context
     * @param title
     * @param message
     */
    public ConfirmDialog(final Context context, final String title,
            final String message) {
        super(context);
        setTitle(title);
        setMessage(message);

        setPositiveButton("ok", this);
        setNegativeButton("cancel", this);
    }

    /**
     * will be called when "cancel" pressed. closes the dialog. can be
     * overridden.
     * @param dialog
     */
    public final void onCancelClicked(final DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public final void onClick(final DialogInterface dialog, final int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (onOkClicked("")) {
                dialog.dismiss();
            }
        } else {
            onCancelClicked(dialog);
        }
    }

    /**
     * called when "ok" pressed.
     * @param input
     * @return true, if the dialog should be closed. false, if not.
     */
    public abstract boolean onOkClicked(String input);
}
