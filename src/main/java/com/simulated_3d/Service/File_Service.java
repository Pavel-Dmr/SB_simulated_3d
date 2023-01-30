package com.simulated_3d.Service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class File_Service {

    public String Upload_File(String upload_path,String ori_name,byte[] file_data) throws Exception
    {
        UUID uuid = UUID.randomUUID();
        String extension = ori_name.substring(ori_name.lastIndexOf("."));
        String save_name = uuid.toString() + extension;
        String upload_url = upload_path +"/" + save_name;
        FileOutputStream fos = new FileOutputStream(upload_url);
        fos.write(file_data);
        fos.close();

        return save_name;
    }

    public void Delete_File(String file_path) throws Exception
    {
        File delete_file = new File(file_path);

        if(delete_file.exists())
        {
            delete_file.delete();
            log.info("파일을 삭제합니다.");
        }
        else
        {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
