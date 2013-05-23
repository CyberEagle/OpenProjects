package br.com.cybereagle.androidlibrary.util;

import android.widget.ListView;
import br.com.cybereagle.commonlibrary.util.ReflectionUtils;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;

@Singleton
public class HackUtils {


    public int measureHeightOfChildren(ListView listView, int widthMeasureSpec, int startPosition, int endPosition,
                                       final int maxHeight, int disallowPartialChildPosition) {
        try {
            return ReflectionUtils.invokeRestrictedMethod(listView, "measureHeightOfChildren",
                    new Integer[]{widthMeasureSpec, startPosition, endPosition, maxHeight, disallowPartialChildPosition},
                    int.class, int.class, int.class, int.class, int.class);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
