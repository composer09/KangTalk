package kr.co.composer.kangtalk.utils;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class FormUtil {
	public static String getEditTextString(Activity act, int itemId) {
		String tmp = ((EditText) act.findViewById(itemId)).getText().toString();
		if (tmp == null) {
			tmp = "";
		}
		
		return tmp;
	}
	
//	public static Integer getEditTextInteger(Activity act, int itemId) {
//		String editTextString = getEditTextString(act, itemId);
//
//		if (StringUtils.isEmpty(editTextString)) {
//			return null;
//		} else {
//			return Integer.parseInt(editTextString);
//		}
//	}
	
	public static int getSelectedRadioButtonId(Activity activity, int radioGroupId) {
		RadioGroup selectedGroup = (RadioGroup) activity.findViewById(radioGroupId);
		return selectedGroup.getCheckedRadioButtonId();
	}
}
