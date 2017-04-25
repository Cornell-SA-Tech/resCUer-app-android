package cornell.sa.rescuer;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

/**
 * Created by yiweini on 3/24/17.
 */
public class PoliceDiag extends DialogFragment {
    private final String[] content =
        {"Tompkins Police", "Cornell Police", "Cayuga Medical Center"};

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity paren = (MainActivity)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(paren);
        builder.setTitle(R.string.pickcontact);
        builder.setItems(content, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                paren.callPolice(which);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        final AlertDialog result = builder.create();


        result.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                result.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        return result;
    }
}

