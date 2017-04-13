package cornell.sa.rescuer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by chenn on 4/10/2017.
 */

public class Card extends LinearLayout {
    private TextView titleV;
    private TextView contentV;
    private TextView editV;
    private TextView delV;
    public Card(Context context, String title, String content, int id){
        super(context);
        init(title, content, id);
    }

    public Card(Context context, AttributeSet attrs, String title, String content, int id) {
        super(context, attrs);
        init(title, content, id);
    }

    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(String title, String content, int id) {
        inflate(getContext(), R.layout.card, this);
        titleV = (TextView)this.findViewById(R.id.title);
        titleV.setText(title);
        contentV = (TextView)this.findViewById(R.id.content);
        contentV.setText(content);
        editV = (TextView)this.findViewById(R.id.edit);
        delV = (TextView)this.findViewById(R.id.delete);

        LinearLayout control = (LinearLayout)findViewById(R.id.controls);
        control.setId(id);
    }

    public void update(String title, String content){
        titleV = (TextView)this.findViewById(R.id.title);
        titleV.setText(title);
        contentV = (TextView)this.findViewById(R.id.content);
        contentV.setText(content);
    }

    public TextView getEdit(){
        return editV;
    }

    public TextView getDelete(){
        return delV;
    }
}
