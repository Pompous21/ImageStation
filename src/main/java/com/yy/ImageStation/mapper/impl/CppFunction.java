package com.yy.ImageStation.mapper.impl;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class CppFunction {

    public interface ImgDith extends Library {

        // Dll 接口
        ImgDith INSTANCE = Native.loadLibrary("D:\\_Jinan\\graduation_project\\work_space\\DIP\\ImgDith\\x64\\Release\\ImgDith.dll", ImgDith.class);

        public int imgDith(String rawImgPath, String dithedImgPath);


        
    }

}
