package com.suifeng.xposedwork.util;

import android.text.TextUtils;

import com.suifeng.xposedwork.util.exception.ModuleApkPathException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author suifengczc
 * @date 2020/3/24
 */
public class AssetsReader {

    ZipFile zipFile;

    public AssetsReader() throws ModuleApkPathException, IOException {
        String moduleApkPath = Utils.getModuleApkPath();
        if (!TextUtils.isEmpty(moduleApkPath)) {
            zipFile = new ZipFile(moduleApkPath);
        }
    }


    /**
     * 从模块的assets下获取指定路径下文件内容
     * 路径不包含assets/
     *
     * @param assetsPath 读取的assets下的文件目录
     * @param close      是否读取完数据马上关闭流。true：马上关闭。false：手动调用close()方法关闭。
     * @return string
     */
    public String getStringFromAssets(String assetsPath, boolean close) throws IOException {
        String str = "";
        ZipEntry zipEntry = zipFile.getEntry("assets/" + assetsPath);
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        if (inputStream != null) {
            try (
                    InputStreamReader in = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(in)
            ) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                str = sb.toString();
            } catch (IOException e) {
                Utils.printThrowable(e);
            }
        }
        if (close) {
            close();
        }
        return str;
    }

    /**
     * 关闭zipfile
     */
    public void close() {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException e) {
                Utils.printThrowable(e);
            }
        }
    }
}
