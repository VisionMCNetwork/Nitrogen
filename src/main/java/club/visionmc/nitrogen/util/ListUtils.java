package club.visionmc.nitrogen.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Topu
 * @date 11/27/2022
 */
public class ListUtils {

    public static List reverseArrayList(List list) {
        List revArrayList = Lists.newArrayList();
        for (int i = list.size() - 1; i >= 0; i--) {
            revArrayList.add(list.get(i));
        }
        return revArrayList;
    }

}