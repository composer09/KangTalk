package kr.co.composer.kangtalk.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import kr.co.composer.kangtalk.R;
import kr.co.composer.kangtalk.chat.ChatMessage;
import kr.co.composer.kangtalk.config.Config;

/**
 * Created by composer10 on 2015. 9. 3..
 */
public class ConfigAdapter extends BaseAdapter {

    private final List<Config> configList;
    private Activity context;

    public ConfigAdapter(Activity context, List<Config> configList) {
        this.context = context;
        this.configList = configList;
    }

    @Override
    public int getCount() {
        if (configList != null) {
            return configList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Config getItem(int position) {
        if (configList != null) {
            return configList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Config config = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.config_item, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.configTitle.setText(config.getTitle());
        holder.configText.setText(config.getText());

        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.configTitle = (TextView) v.findViewById(R.id.config_title);
        holder.configText = (TextView) v.findViewById(R.id.config_text);
        return holder;
    }

    private static class ViewHolder {
        public TextView configTitle;
        public TextView configText;
    }
}
