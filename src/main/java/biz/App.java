package biz;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import java.io.File;
import java.net.URL;
import java.util.Date;

 /**
 * @author: 林源
  * @Author: bravry
 * @Date: 2019/5/7
 * @Description: java实现腾讯云存储服务（COSClient）
 * @REGIONID 区域
 * @KEY  上传上云之后的名字
 * @KEY01 需要删除的文件
 */
public class App {

    private static final String ACCESSKEY = "AKIDd4FBrtMdfMvIUZCev491hX9WfopaJThg";
    private static final String SECRETKEY ="96Xno9PfjaPzJOxMbdQyHMCqK6i6F9nB";
    //private static final String BUCKETNAME = "test1-1259063860";
    //private static final String APPID = "1259063860";
    private static final String REGIONID = "ap-shanghai";
    //private static final String KEY01="MyFile1/1.apk";
    /**
     * 初始化CosClient相关配置， appid、accessKey、secretKey、region
     * @return
     */
    public static COSClient getCosClient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(ACCESSKEY, SECRETKEY);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        ClientConfig clientConfig = new ClientConfig(new Region(REGIONID));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        //String bucketName = BUCKETNAME;
        return cosClient;
    }

    /**
     * 上传文件
     * @return
     * //绝对路径和相对路径都OK
     */
    public static String uploadFile(String KEY,String path,String account) {
        File localFile = new File(path);
        String bucketnewname=account+"-1259063860";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketnewname, KEY, localFile);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia),一般为标准的
        putObjectRequest.setStorageClass(StorageClass.Standard);

        COSClient cc = getCosClient();
        try {
            PutObjectResult putObjectResult = cc.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            System.out.println(etag);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cc.shutdown();
        return null;
    }

    /**
     * 下载文件
     * @param bucketName
     * @param key
     * @return
     */
    public static String downLoadFile(String key,String path,String account) {
        File downFile = new File(path);
        String bucketnewname=account+"-1259063860";
        COSClient cc = getCosClient();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketnewname, key);

        ObjectMetadata downObjectMeta = cc.getObject(getObjectRequest, downFile);
        cc.shutdown();
        String etag = downObjectMeta.getETag();
        return etag;
    }

    /**
     * 删除文件
     * @param bucketName
     * @param key
     */
    public static void deleteFile( String key,String account) {
        COSClient cc = getCosClient();
        String bucketnewname=account+"-1259063860";
        try {
            cc.deleteObject(bucketnewname, key);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cc.shutdown();
        }

    }

    /**
     * 创建桶
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static Bucket createBucket(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        Bucket bucket = null;
        try {
            bucket = cc.createBucket(bucketName+"-1259063860");
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
        }
        return bucket;
    };
    /**
     * 复制另一个桶的文件
     * @param bucketName
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static boolean copyfileotherBucket(String hostaccount,String account,String hostkey,String key)throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        String hostbucketname=hostaccount+"-1259063860";
        String bucketname=account+"-1259063860";
        String hostKey=hostkey;
        String Key=key;
        try {
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(hostbucketname, hostKey, bucketname, Key);
            CopyObjectResult copyObjectResult = cc.copyObject(copyObjectRequest);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cc.shutdown();
        }
        return true;
    };

    /**
     * 删除桶
     * @param bucketName
     * @throws CosClientException
     * @throws CosServiceException
     */
    public void deleteBucket(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        try {
            cc.deleteBucket(bucketName);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
        }
    };

    /**
     * 判断桶是否存在
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static boolean doesBucketExist(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();
        boolean bucketExistFlag = cc.doesBucketExist(bucketName);
        return bucketExistFlag;
    };

    /**
     * 查看桶文件
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static ObjectListing listObjects(String bucketName) throws CosClientException, CosServiceException {
        COSClient cc = getCosClient();

        // 获取 bucket 下成员（设置 delimiter）
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        // 设置 list 的 prefix, 表示 list 出来的文件 key 都是以这个 prefix 开始
        listObjectsRequest.setPrefix("");
        // 设置 delimiter 为/, 即获取的是直接成员，不包含目录下的递归子成员
        listObjectsRequest.setDelimiter("/");
        // 设置 marker, (marker 由上一次 list 获取到, 或者第一次 list marker 为空)
        listObjectsRequest.setMarker("");
        // 设置最多 list 100 个成员,（如果不设置, 默认为 1000 个，最大允许一次 list 1000 个 key）
        listObjectsRequest.setMaxKeys(100);

        ObjectListing objectListing = cc.listObjects(listObjectsRequest);
        return objectListing;
    }

    /**
     *查询一个 Bucket 所在的 Region。
     * @param bucketName
     * @return
     * @throws CosClientException
     * @throws CosServiceException
     */
    public static String getBucketLocation(String bucketName) throws CosClientException , CosServiceException{
        COSClient cosClient = getCosClient();
        // bucket 的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String location = cosClient.getBucketLocation(bucketName);
        return location;
    }

    public static String  getfiledownloadkey(String account,String key,long timelong)throws CosClientException , CosServiceException
    {
        COSClient cc = getCosClient();
        try{
            String	bucketname=account+"-1259063860";
            GeneratePresignedUrlRequest req =new GeneratePresignedUrlRequest(bucketname, key, HttpMethodName.GET);

            Date expirationDate = new Date(System.currentTimeMillis() + timelong*60000L);
            req.setExpiration(expirationDate);
            URL url = cc.generatePresignedUrl(req);

            return url.toString();
        }catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cc.shutdown();

        }
        return null;

    }

}

