package com.zhongrui.util;

import com.zhongrui.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 文件上传
 * 文件删除
 * 文件下载
 * 文件信息获取
 * Storage 信息获取
 * Tracker 信息获取
 */
public class FastDFSUtils {
    //加载 tracker 的原始信息
    static TrackerClient trackerClient;
    static TrackerServer trackerServer;
    static StorageClient storageClient;
    static {
        String fileName = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            ClientGlobal.init(fileName);
            //先去获取 tracker 的连接对象
            trackerClient = new TrackerClient();
            //通过 tracker 注册中心获取可访问的 TrackerServer 服务，就可以获取可用的 storage 信息
            trackerServer = trackerClient.getConnection();
            //获取 storage 信息
            storageClient = new StorageClient(trackerServer, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //文件上传
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        //附加信息
        //获取文件的作者
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", fastDFSFile.getAuthor());

        //完成文件上传
        /**
         * 1.上传文件的字节数组
         * 2.上传的扩展名
         * 3.附加参数 地址
         */
        String[] file = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return file;
    }
    //获取文件信息
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    //实现文件下载
    public static InputStream download(String groupName,String remoteFileName ) throws Exception {
        //完成文件下载
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(bytes);
    }
    //文件的删除
    public static void delete(String groupName,String remoteFileName)throws Exception{
        storageClient.delete_file(groupName,remoteFileName);
    }

    public static void main(String[] args) throws Exception {
        FileInfo fileInfo = getFile("group1", "M00/00/00/wKjIgF-S5jGAcMC9AAGlWQ38toM414.jpg");
        System.out.println(fileInfo.getSourceIpAddr());
        System.out.println(fileInfo.getFileSize());
        /*//文件下载
        InputStream is = download("group1", "M00/00/00/wKjIgF-S5jGAcMC9AAGlWQ38toM414.jpg");
        FileOutputStream fos = new FileOutputStream("a.jpg");
        byte[] bytes = new byte[1024];
        while (is.read(bytes)!= 0) {
            fos.write(bytes);
        }
        fos.close();
        is.close();*/

        //文件删除
        //delete("group1","M00/00/00/wKjIgF-S5jGAcMC9AAGlWQ38toM414.jpg");
    }
}
