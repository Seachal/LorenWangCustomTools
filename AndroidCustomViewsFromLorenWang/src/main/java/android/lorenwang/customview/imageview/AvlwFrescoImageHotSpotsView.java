package android.lorenwang.customview.imageview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能作用：热区图片控件
 * 创建时间：2020-11-11 3:25 下午
 * 创建人：王亮（Loren）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 *
 * @author 王亮（Loren）
 */
public class AvlwFrescoImageHotSpotsView extends SimpleDraweeView {
    /**
     * 热区数据
     */
    private final Map<Rect, ImageHotSpotsInfo> locationMap = new ConcurrentHashMap<>();

    /**
     * 热区点击
     */
    private HotSpotsOnClick hotSpotsOnClick;

    /**
     * 上一次的点击时间
     */
    private long lastClickTime = 0;

    /**
     * 点击时间间隔
     */
    private final long CLICK_TIME_INTERVAL = 1000L;

    public AvlwFrescoImageHotSpotsView(Context context) {
        super(context);
    }

    public AvlwFrescoImageHotSpotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvlwFrescoImageHotSpotsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (hotSpotsOnClick != null && isEnabled() && System.currentTimeMillis() - lastClickTime > CLICK_TIME_INTERVAL) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float upX = event.getX();
                float upY = event.getY();
                //手拿起
                Iterator<Rect> iterator = locationMap.keySet().iterator();
                Rect rect;
                while (iterator.hasNext()) {
                    rect = iterator.next();
                    if (rect != null && upX > rect.left && upX <= rect.right
                            && upY > rect.top && upY <= rect.bottom) {
                        lastClickTime = System.currentTimeMillis();
                        //查找到点击区域，执行点击回调
                        hotSpotsOnClick.onClick(this, locationMap.get(rect));
                        return true;
                    }
                }
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 设置热区数据
     *
     * @param list            热区数据列表
     * @param hotSpotsOnClick 热区点击事件
     * @param <T>             泛型数据
     */
    public <T> void setHotSpotsData(ArrayList<ImageHotSpotsInfo<T>> list, HotSpotsOnClick<T> hotSpotsOnClick) {
        this.hotSpotsOnClick = hotSpotsOnClick;
        if (list != null) {
            for (ImageHotSpotsInfo item : list) {
                locationMap.put(item.rect, item);
            }
        }
    }

    /**
     * 图片热区所使用的参数
     */
    public static class ImageHotSpotsInfo<T> {
        /**
         * 范围
         */
        private Rect rect;
        /**
         * 数据
         */
        private T data;

        public void setData(T data) {
            this.data = data;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public T getData() {
            return data;
        }

        public Rect getRect() {
            return rect;
        }
    }

    /**
     * 热区点击回调接口
     */
    public interface HotSpotsOnClick<T> {
        /**
         * 点击事件
         *
         * @param hotSpotsView 当前控件
         * @param data         数据
         */
        void onClick(AvlwFrescoImageHotSpotsView hotSpotsView, ImageHotSpotsInfo<T> data);
    }
}

