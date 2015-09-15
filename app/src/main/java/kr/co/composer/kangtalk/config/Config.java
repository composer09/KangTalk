package kr.co.composer.kangtalk.config;

/**
 * Created by composer10 on 2015. 9. 3..
 */
public class Config {
    private String title = null;
    private String text = null;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }
}