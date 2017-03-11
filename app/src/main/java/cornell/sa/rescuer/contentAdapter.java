package cornell.sa.rescuer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yiweini on 3/10/17.
 */
public class contentAdapter extends ArrayAdapter<instruction> {

    public contentAdapter(Context context, ArrayList<instruction> ins){
        super(context, 0, ins);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        instruction i = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.instruction_view, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.i_title);
        TextView text = (TextView) convertView.findViewById(R.id.i_text);
        // Populate the data into the template view using the data object
        title.setText(i.Title);
        text.setText(i.content);
        // Return the completed view to render on screen
        return convertView;
    }
}
