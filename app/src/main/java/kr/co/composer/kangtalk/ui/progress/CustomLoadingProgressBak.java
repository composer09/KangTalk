package kr.co.composer.kangtalk.ui.progress;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.composer.kangtalk.R;


public class CustomLoadingProgressBak extends Dialog {
    public CustomLoadingProgressBak(Context context) {
        super(context, R.style.TransparentDialog);
        init(context);
    }

    protected CustomLoadingProgressBak(Context context, boolean cancelable,
                                       OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public CustomLoadingProgressBak(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private static final String color = "#33000000";

    private ImageView imageView;
    private TextView loading;
    private AnimationDrawable animation;
    View view;


    /**
     * Loads the layout and sets the initial set of images
     */
    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.loading_progress_bak, null);
        super.addContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        imageView = (ImageView) view.findViewById(R.id.imgOne);
        imageView.setBackgroundResource(R.anim.loading_progress);
        animation = (AnimationDrawable) imageView.getBackground();

        loading = (TextView) view.findViewById(R.id.loading);


        view.setBackgroundColor(Color.parseColor(color)); //다이아로그 배경
//		loading.setText("로그인 중..");
    }

    public void show() {
        super.show();
        animation.start();
    }

    /**
     * This is called when you want the dialog to be dismissed
     */
    public void dismiss() {
        animation.stop();
        recycleBitmap(imageView);
        super.dismiss();
    }

    private void recycleBitmap(ImageView iv) {
        try {
            Drawable d = iv.getDrawable();
            if (d instanceof BitmapDrawable) {
                Bitmap b = ((BitmapDrawable) d).getBitmap();
                b.recycle();
            }
            d.setCallback(null);
        } catch (Exception e) {
        }
    }

}
