package com.jalja.rpc.register.file;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jalja.rpc.register.IRegister;
import com.jalja.rpc.register.model.RegisterModelDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XL
 * @title: JDKRegister
 * @projectName jalja-rpc
 * @date 2020/7/1 16:41
 * @description: 内存注册中心
 */
public class FileRegister implements IRegister {
    private static Map<String,List<String>> ipsMap=new ConcurrentHashMap<>();
    @Override
    public void register(RegisterModelDTO modelDTO) throws Exception {
        List<String> list=getRegisterValue(modelDTO.getClassName());
        if(CollectionUtils.isEmpty(list)){
            list= Lists.newArrayList(modelDTO.getIp());
        }else if (!list.contains(modelDTO.getIp())){
            list.add(modelDTO.getIp());
        }
        ipsMap.put(modelDTO.getClassName(),list);
    }

    @Override
    public void flush() throws Exception {
        FileOutputStream fos=null;
        OutputStreamWriter ow=null;
        BufferedWriter writer=null;
        try {
            if (MapUtils.isEmpty(ipsMap)){
                return;
            }
            File file=new File("./register.txt");
            if(file.exists()){
               file.delete();
            }
            file.createNewFile();
             fos=new FileOutputStream(file);
             ow=new OutputStreamWriter(fos);
             writer=new BufferedWriter(ow);
            for(Map.Entry<String, List<String>> map: ipsMap.entrySet()){
                String value=map.getKey()+"="+JSON.toJSON(map.getValue());
                writer.append(value);
                writer.newLine();
            }
        }finally {
            if(writer!=null){
                writer.close();
            }
            if(ow!=null){
                ow.close();
            }
            if(fos!=null){
                fos.close();
            }
        }
    }
    @Override
    public List<String> getRegisterValue(String key) throws Exception {
        FileInputStream fis= null;
        InputStreamReader reader= null;
        BufferedReader bufferedReader = null;
        try {
            File file=new File("./register.txt");
            if(!file.exists()){
                return null;
            }
             fis=new FileInputStream(file);
             reader=new InputStreamReader(fis);
             bufferedReader=new BufferedReader(reader);
             List<String> list=Lists.newArrayList();
            if(!bufferedReader.ready()){
                throw new RuntimeException("注册文件内容为空");
            }
            while (bufferedReader.ready()){
                list.add(bufferedReader.readLine());
            }
            for (String str:list){
                String fKey=str.split("=")[0];
                if(StringUtils.equals(key,fKey)){
                    return JSON.parseArray(str.split("=")[1],String.class);
                }
            }
        }finally {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
        }
        return null;
    }
}
