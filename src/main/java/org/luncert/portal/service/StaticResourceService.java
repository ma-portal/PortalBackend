package org.luncert.portal.service;

import java.io.InputStream;

import org.luncert.portal.util.IOHelper;
import org.springframework.web.multipart.MultipartFile;

/**
 * 与linx-server做集成
 * uri格式：http://host:port/static-resource/{id}，其中id的格式：{哈希码}.{文件类型}
 */
public interface StaticResourceService {

    /**
     * 
     * @param fileName
     * @param data
     * @return resource uri
     */
    String save(String fileName, byte[] data) throws Exception;

    /**
     * 
     * @param fileName
     * @param in
     * @return resource uri
     * @throws IOException
     */
    default String save(String fileName, InputStream in) throws Exception {
        byte[] data = IOHelper.read(in);
        return save(fileName, data);
    }

    /**
     * 
     * @param file
     * @return resource uri
     */
    default String save(MultipartFile file) throws Exception {
        return save(file.getOriginalFilename(),
            file.getInputStream());
    }

    /**
     * 
     * @param resId
     * @return true if find a resource with specified resId
     */
    boolean exists(String resId);

    /**
     * 
     * @param resId
     * @return output stream
     */
    byte[] get(String resId);

    /**
     * 
     * @param resId
     * @return true if deleting successfully
     */
    void delete(String resId);

}