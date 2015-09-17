package kr.co.composer.kangtalk.ui.progress;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import kr.co.composer.kangtalk.R;

/**
 * Created by composer10 on 2015. 9. 16..
 */
public class CustomLoading {
    private Dialog m_loadingDialog;
    private Context context;

    public CustomLoading(Context context){
        this.context = context;
    }
    public void show(){

        if (m_loadingDialog == null) {
            //다이얼로그가 없으면 만들고 보이게 하라
            m_loadingDialog = new Dialog(context, R.style.custom_loading);
            //프로그레스를 생성하자
            ProgressBar pb = new ProgressBar(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //프로그래스를 다이얼로그에 포함하자
            m_loadingDialog.setContentView(pb, params);
            m_loadingDialog.setCancelable(false);
            m_loadingDialog.show();
        } else if(!m_loadingDialog.isShowing()){
            //다이얼로그가 있는데 HIDE 상태면 보이게 하라
            m_loadingDialog = null;
            m_loadingDialog = new Dialog(context, R.style.custom_loading);
            ProgressBar pb = new ProgressBar(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            m_loadingDialog.setContentView(pb, params);
            m_loadingDialog.setCancelable(false);
            m_loadingDialog.show();
        }

    }
    public void dismiss() {
        if (m_loadingDialog != null) {
            if(m_loadingDialog.isShowing()){
                //다이얼로그가 있고 보이는 상태면 안보이게 하라
                m_loadingDialog.dismiss();
                m_loadingDialog = null;
            }
        }
    }
}

