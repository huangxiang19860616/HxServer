package Hx.HxServer.message.protobuf;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 主要是用来把Protobuf消息转成JSON字符串.
 */
public class JsonHelper {
    
    /*
     * Gson实例是线程安全的:
     * http://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean
     */
    private static final Gson GSON = new GsonBuilder()
            .setExclusionStrategies(new ProtobufExclusionStrategy())
            //.setPrettyPrinting()
            .create();
    
    // 根据protoc编译出来的代码规则跳过字段
    private static class ProtobufExclusionStrategy implements ExclusionStrategy {
        
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            if (f.getName().startsWith("bitField")) {
                return true;
            }

            return !f.getName().endsWith("_");
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
        
    }
    
    /**
     * 把对象转成JSON字符串.
     * @param obj
     * @return 
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }
    
}
