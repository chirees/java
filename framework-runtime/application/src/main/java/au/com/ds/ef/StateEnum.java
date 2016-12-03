package au.com.ds.ef;

import java.util.List;

/**
 * User: andrey
 * Date: 3/12/2013
 * Time: 9:52 PM
 */
public interface StateEnum {
    String code();
    
    String name();
    
    String trigger();
    
    boolean isStarted();
    
    List<Trigger> triggers();
    
    
}
