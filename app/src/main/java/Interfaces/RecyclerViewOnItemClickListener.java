package Interfaces;

import android.view.View;
import android.widget.CheckBox;

/**
 * Created by Hypnos on 18/11/2017.
 */

public interface RecyclerViewOnItemClickListener {
    void onClick(View v, int position, CheckBox checkBox);
}
