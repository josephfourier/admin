package com.thinkjoy.oauth.client.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session序列化工具
 * Created by shuzheng on 2017/3/12.
 */
public class SerializableUtil {

    public static String serialize(Session session) {
        if (null == session) {
            return null;
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }

    public static Session deserialize(String sessionStr) {
        if (StringUtils.isBlank(sessionStr)) {
            return null;
        }
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(sessionStr));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Session) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }

    /**
     * protostuff 序列化
     */
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
    private static Objenesis objenesis = new ObjenesisStd(true);

    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> cls){
        Schema<T> schema = (Schema<T>)cachedSchema.get(cls);
        if(schema == null){
            schema = RuntimeSchema.createFrom(cls);
            if(schema != null){
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    /**
     * 返回一个byte[]
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serializer(T obj){
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try{
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }finally{
            buffer.clear();
        }
    }

    /**
     * 返回一个对象
     * @param data
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T deserializer(byte[] data, Class<T>cls){
        try{
            T message = (T)objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * protostuff 序列化
     * 返回一个string
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String protostuff_serialize(T obj) {
        if (null == obj) {
            return null;
        }
        return Base64.encodeToString(serializer(obj));
    }

    /**
     *  protostuff 反序列化
     * 返回一个对象
     * @param sessionStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T protostuff_deserialize(String sessionStr,Class<T> cls) {
        if (StringUtils.isBlank(sessionStr)) {
            return null;
        }
        return deserializer( Base64.decode(sessionStr),cls);
    }

}
