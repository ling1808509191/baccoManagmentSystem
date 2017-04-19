package com.mark.java.controller;

import com.mark.java.staticTool.staticToll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.Map;

/**
 * Created by vcc on 2017/4/13.
 */
@Controller
@RequestMapping("/test")
public class testController {
    @RequestMapping("/test1")
    @ResponseBody
    public String test1(){
        String filename= System.getProperty("evan.webapp")+"img"+File.separatorChar+"psb.jpg";
//        File f = new File(filename);
//        StringBuffer stringBuffer=new StringBuffer();
//        Reader r = null;
//        try {
//            r = new FileReader(f);
//        }catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            int b = 0;
//            while((b=r.read())!=-1) {
//                stringBuffer.append((char)b);
//            }
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            r.close();
//        }catch(IOException e) {
//            e.printStackTrace();
//        }

        String newfilename= System.currentTimeMillis()+"psb.jpg";
        staticToll.string2File(staticToll.file2String(new File(filename),"Unicode"),newfilename);
        System.out.println(staticToll.file2String(new File(filename),"Unicode"));
    return staticToll.file2String(new File(filename),null);

    }
    @RequestMapping("/Md5")
    @ResponseBody
    public String test2(@RequestParam String md5){

        return staticToll.md5Password(md5);
    }
    @RequestMapping("/file")
    @ResponseBody
    public String test3(@RequestBody Map file){
        String filename= System.getProperty("evan.webapp")+"img"+File.separatorChar+System.currentTimeMillis()+"psb.jpg";
        return String.valueOf( staticToll.string2File((String)file.get("file"),filename));
    }

}
