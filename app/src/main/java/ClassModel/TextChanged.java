package ClassModel;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Hypnos on 18/11/2017.
 */

public class TextChanged implements TextWatcher {
    private Context mContext;
    EditText editText;
    String val;

    public TextChanged(Context mContext, EditText editText) {
        this.mContext = mContext;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        val = editText.getText().toString();
        if (val.equals("")){
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }else{
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
