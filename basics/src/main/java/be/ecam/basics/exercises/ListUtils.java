package be.ecam.basics.exercises;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<String> removeShortStrings(List<String> list, int minLen) {
    	List<String> toReturn = new ArrayList<>();
        for (String s : list) {
            if (s.length() >= minLen) {
            	toReturn.add(s);
            }
        }
        list.clear();
        for(String S : toReturn) list.add(S);
        return list;
    }
}
