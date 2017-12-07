package com.linyuzai.component.ui.view.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linyuzai.component.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 * @author linyuzai
 */
public class MenuView extends PopupWindow {

    private MenuView(MenuContent menuContent) {
        super(menuContent, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        setContentView(menuContent);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setAnimationStyle(R.style.anim_menu);
    }

    public void showFromBottom(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Menu {
        private String text;
        private int textColor;
        private int textSize;
        private int background;
        private int extraHeight;
        private OnMenuItemClickListener listener;

        public Menu(String text, OnMenuItemClickListener listener) {
            if (TextUtils.isEmpty(text))
                throw new IllegalArgumentException("menu text can not be null or empty");
            this.text = text;
            this.listener = listener;
            textColor = Color.BLACK;
            textSize = Builder.TEXT_SIZE_DEFAULT;
            background = Color.WHITE;
            extraHeight = Builder.EXTRA_HEIGHT_DEFAULT;
        }

        public Menu(String text) {
            this(text, null);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public int getBackground() {
            return background;
        }

        public void setBackground(int background) {
            this.background = background;
        }

        public int getExtraHeight() {
            return extraHeight;
        }

        public void setExtraHeight(int extraHeight) {
            this.extraHeight = extraHeight;
        }

        public OnMenuItemClickListener getListener() {
            return listener;
        }

        public void setListener(OnMenuItemClickListener listener) {
            this.listener = listener;
        }
    }

    public static class Divider {

        public static final int COLOR_DEFAULT = Color.GRAY;
        public static final int HEIGHT_DEFAULT = 1;

        private int color;
        private int height;

        public Divider() {
            this(COLOR_DEFAULT, HEIGHT_DEFAULT);
        }

        public Divider(int color, int height) {
            this.color = color;
            this.height = height;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static class Builder {

        public static final int TEXT_SIZE_DEFAULT = 18;
        public static final int EXTRA_HEIGHT_DEFAULT = 25;

        private String title;
        private int titleColor;
        private int titleSize;
        private int background;
        private int menuExtraHeight;
        private int allMenuTextColor;
        private int allMenuTextSize;
        private int allMenuBackground;
        private Orientation orientation;
        private Divider divider;
        private List<Menu> menus;

        public Builder() {
            titleColor = Color.BLACK;
            titleSize = TEXT_SIZE_DEFAULT;
            background = Color.WHITE;
            menuExtraHeight = EXTRA_HEIGHT_DEFAULT;
            allMenuTextColor = Color.BLACK;
            allMenuTextSize = TEXT_SIZE_DEFAULT;
            allMenuBackground = Color.WHITE;
            orientation = Orientation.VERTICAL;
            menus = new ArrayList<>();
            divider = new Divider();
        }

        public MenuView create(Context context) {
            MenuContent menuContent = new MenuContent(context);
            switch (orientation) {
                case VERTICAL:
                    menuContent.setOrientation(LinearLayout.VERTICAL);
                    break;
                case HORIZONTAL:
                    menuContent.setOrientation(LinearLayout.HORIZONTAL);
                    break;
            }
            menuContent.setBackgroundColor(background);
            List<Menu> menus = new ArrayList<>();
            if (!TextUtils.isEmpty(title)) {
                Menu titleMenu = new Menu(title);
                titleMenu.textColor = titleColor;
                titleMenu.textSize = titleSize;
                titleMenu.background = background;
                menus.add(titleMenu);
            }
            for (Menu menu : this.menus) {
                menu.textColor = (menu.textColor == Color.BLACK ? (allMenuTextColor == Color.BLACK ? Color.BLACK : allMenuTextColor) : menu.textColor);
                menu.textSize = (menu.textSize == TEXT_SIZE_DEFAULT ? (allMenuTextSize == TEXT_SIZE_DEFAULT ? TEXT_SIZE_DEFAULT : allMenuTextSize) : menu.textSize);
                menu.background = (menu.background == Color.WHITE ? (allMenuBackground == Color.WHITE ? Color.WHITE : allMenuBackground) : menu.background);
                menu.extraHeight = (menu.extraHeight == EXTRA_HEIGHT_DEFAULT ? (menuExtraHeight == EXTRA_HEIGHT_DEFAULT ? EXTRA_HEIGHT_DEFAULT : menuExtraHeight) : menu.extraHeight);
                menus.add(menu);
            }
            menuContent.setAdapter(new MenuAdapter(menus, divider));
            return new MenuView(menuContent);
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public int getTitleColor() {
            return titleColor;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public int getTitleSize() {
            return titleSize;
        }

        public Builder setTitleSize(int titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public int getBackground() {
            return background;
        }

        public Builder setBackground(int background) {
            this.background = background;
            return this;
        }

        public int getMenuExtraHeight() {
            return menuExtraHeight;
        }

        public Builder setMenuExtraHeight(int menuExtraHeight) {
            this.menuExtraHeight = menuExtraHeight;
            return this;
        }

        public int getAllMenuTextColor() {
            return allMenuTextColor;
        }

        public Builder setAllMenuTextColor(int allMenuTextColor) {
            this.allMenuTextColor = allMenuTextColor;
            return this;
        }

        public int getAllMenuTextSize() {
            return allMenuTextSize;
        }

        public Builder setAllMenuTextSize(int allMenuTextSize) {
            this.allMenuTextSize = allMenuTextSize;
            return this;
        }

        public int getAllMenuBackground() {
            return allMenuBackground;
        }

        public Builder setAllMenuBackground(int allMenuBackground) {
            this.allMenuBackground = allMenuBackground;
            return this;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        public Builder setOrientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public Divider getDivider() {
            return divider;
        }

        public Builder setDivider(Divider divider) {
            this.divider = divider;
            return this;
        }

        public Builder addMenu(Menu menu) {
            menus.add(menu);
            return this;
        }

        public Builder addMenu(String menuName, OnMenuItemClickListener listener) {
            Menu menu = new Menu(menuName, listener);
            addMenu(menu);
            return this;
        }

        public Builder removeMenu(Menu menu) {
            menus.remove(menu);
            return this;
        }

        public Builder removeMenu(int position) {
            menus.remove(position);
            return this;
        }

        public Builder clearMenus() {
            menus.clear();
            return this;
        }
    }

    public enum Orientation {
        VERTICAL(LinearLayout.VERTICAL), HORIZONTAL(LinearLayout.HORIZONTAL);
        private int orientation;

        Orientation(int orientation) {
            this.orientation = orientation;
        }

        public int value() {
            return orientation;
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick();
    }

    static class MenuAdapter extends MenuContent.Adapter {

        private List<Menu> menus;
        private Divider divider;

        public MenuAdapter(List<Menu> menus, Divider divider) {
            this.menus = menus;
            this.divider = divider;
        }

        @Override
        protected int getCount() {
            return menus.size() * 2;
        }

        @Override
        protected View getView(int position, ViewGroup parent) {
            if (position % 2 == 0) {
                View view = new View(parent.getContext());
                view.setBackgroundColor(divider.color);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, divider.height));
                return view;
            } else {
                final Menu menu = menus.get(position / 2);
                TextView textView = new TextView(parent.getContext());
                textView.setPadding(0, menu.extraHeight, 0, menu.extraHeight);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                textView.setText(menu.text);
                textView.setTextColor(menu.textColor);
                textView.setTextSize(menu.textSize);
                textView.setBackgroundColor(menu.background);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (menu.listener != null)
                            menu.listener.onMenuItemClick();
                    }
                });
                return textView;
            }
        }

        @Override
        protected Object getItem(int position) {
            return null;
        }

        @Override
        protected long getItemId(int position) {
            return 0;
        }
    }
}
