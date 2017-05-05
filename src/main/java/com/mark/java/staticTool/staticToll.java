package com.mark.java.staticTool;

import com.mark.java.dataBean.NotiationResultBean;
import com.mark.java.dataBean.resultBean;
import com.mark.java.entity.notificationUser;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by vcc on 2017/3/22.
 */
public class staticToll {


    public static String AllSearchHql="";

    private static Map<String,Boolean> userUrlMap;

    private static int DEFAULT_BUFFER_SIZE=1024;
    /* system properties to get separators */
    static final Properties PROPERTIES = new Properties(System.getProperties());

    /**
     * get line separator on current platform
     * @return line separator
     */
    public static String getLineSeparator(){
        return PROPERTIES.getProperty("line.separator");
    }

    /**
     * get path separator on current platform
     * @return path separator
     */
    public static String getPathSeparator(){
        return PROPERTIES.getProperty("path.separator");
    }

    /**
     * md5���ܺ���
     * @param password
     * @return
     */
    public static String md5Password(String password) {
        try {
// �õ�һ����ϢժҪ��
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
// ��ÿһ��byte ��һ�������� 0xff;
            for (byte b : result) {
// ������
                int number = b & 0xff;
                String str = Integer.toHexString(number);
// System.out.println(str);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
// ��׼��md5���ܺ�Ľ��
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String CreateToken(int uid,String password){
        String token=String.valueOf(uid)+String.valueOf(password)+String.valueOf(System.currentTimeMillis());
        System.out.println(" befor md5,token = :"+token);
        token=md5Password(token);
        System.out.println(" after md5,token = :"+token);
        return token;
    }
    public static NotiationResultBean notificationResultAdaptor(List<notificationUser> notificationUserList,int totalNum ){
        NotiationResultBean notiationResultBean=new NotiationResultBean();
        HashMap<String,Object> data = new HashMap<String, Object>();
        List<HashMap<String,Object>> AllnotificationList=new LinkedList<HashMap<String, Object>>();
        for(int i=0;i<notificationUserList.size();i++ ){
            HashMap<String,Object> tempHashMap=new HashMap<String, Object>();
            int categoryId=notificationUserList.get(i).getmNotification().getmCategory().getId();
             String categoryName=notificationUserList.get(i).getmNotification().getmCategory().getName();
            HashMap<String,Object> tempCategoryHashMap=null;
            List<HashMap<String,Object>> categoryList=null;
            for(int j=0;j<AllnotificationList.size();j++){
                if(categoryId==(Integer)AllnotificationList.get(j).get("id")){
                    tempCategoryHashMap=AllnotificationList .get(j);
                    break;
                }

            }
            if(tempCategoryHashMap==null){
                tempCategoryHashMap=new HashMap<String, Object>();
                tempCategoryHashMap.put("id",categoryId);
                tempCategoryHashMap.put("name",categoryName);
                tempCategoryHashMap.put("notifications",new LinkedList<Object>() );
                AllnotificationList.add(tempCategoryHashMap);
            }

            HashMap<String,Object> tempnotificationMap=new HashMap<String, Object>();
            tempnotificationMap.put("id",notificationUserList.get(i).getmNotification().getId());
            tempnotificationMap.put("category_id",categoryId);
            tempnotificationMap.put("title",notificationUserList.get(i).getmNotification().getTitle());
            tempnotificationMap.put("content",notificationUserList.get(i).getmNotification().getContent());
            tempnotificationMap.put("stauts",notificationUserList.get(i).getmNotification().getStatus());
            tempnotificationMap.put("read",notificationUserList.get(i).isRead());
            ((LinkedList)(tempCategoryHashMap.get("notifications"))).add(tempnotificationMap);
        }
        notiationResultBean.setSuccess(1);
        notiationResultBean.setMessage("get notification sucess");
        notiationResultBean.getData().put("all_notifications",AllnotificationList);
        notiationResultBean.getData().put("totalNum",totalNum);
        return notiationResultBean;
    }

    public static boolean string2File(String res, String filename) {
        boolean flag = true;

        filename=System.getProperty("evan.webapp")+"img"+File.separatorChar+filename;
        System.out.println("filename : "+filename);
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filename);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));

            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //�ַ�������
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding != null &&!"".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            //��������д�������
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        //����ת�����
        if (writer != null)
            return writer.toString();
        else return null;
    }
    public static boolean GenerateImage(String imgStr,String filename)
    {   //���ֽ������ַ�������Base64���벢����ͼƬ
        if (imgStr == null) //ͼ������Ϊ��
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64����
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//�����쳣����
                    b[i]+=256;
                }
            }
            //����jpegͼƬ
            String imgFilePath =System.getProperty("evan.webapp")+"img"+File.separatorChar+filename;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public static boolean deleteFile(String fileName) {
        String imgFilePath =System.getProperty("evan.webapp")+"img"+File.separatorChar+fileName;
        File file = new File(imgFilePath);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
                return true;
            } else {
                System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
                return false;
            }
        } else {
            System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
            return false;
        }
    }
//    public static boolean isUserUrl(String url){
//        if(userUrlMap==null){
//            userUrlMap=new HashMap<String, Boolean>();
//            userUrlMap.put("user/addAccount",true);
//            userUrlMap.put("")
//            return
//        }else{
//
//        }
//    }

}
