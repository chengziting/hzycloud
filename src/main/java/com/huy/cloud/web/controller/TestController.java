package com.huy.cloud.web.controller;

import com.huy.cloud.HzyCloudApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.ConsoleHandler;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by 10191042 on 2019-08-15.
 */
@RestController
@RequestMapping("/api")
public class TestController {

    public static final String PROJECT_JAR_NAME = "hzy-cloud-0.0.1-SNAPSHOT.jar";

    @RequestMapping("/downloadCode")
    public String downloadCode(HttpServletRequest request,HttpServletResponse response) {
        try {
            URL url = new URL("https://github.com/chengziting/hzycloud/archive/master.zip");
            SocketAddress sa = new InetSocketAddress("proxy1.sg.kodak.com", 81);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, sa);
            URLConnection connection = url.openConnection(proxy);
            InputStream inputStream = connection.getInputStream();
            String filePath = HzyCloudApplication.getHomePath().getPath() + "/data/hzycloud-master.zip";
            FileOutputStream outputStream = new FileOutputStream(filePath);
            byte[] bytes = new byte[2048];
            int length = 0;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.close();
            inputStream.close();
            unzipFile(filePath);
//            compileProject();
            runAntToCompile();
            return "download finish!";
        }catch (Exception ex){
            return "download failed!" + ex.getLocalizedMessage();
        }
    }


    private void unzipFile(String fileName){
        try {
            final int BUFFER = 2048;
            String filePath = HzyCloudApplication.getHomePath().getPath() + "/data/";
            ZipFile zipFile = new ZipFile(fileName);
            Enumeration emu = zipFile.entries();
            int i=0;
            while(emu.hasMoreElements()){
                ZipEntry entry = (ZipEntry)emu.nextElement();
                //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory())
                {
                    new File(filePath + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(filePath + entry.getName());
                //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                //而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if(parent != null && (!parent.exists())){
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);

                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1)
                {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compileProject(){
        final String projectRoot = HzyCloudApplication.getHomePath().getPath() + "/data/hzycloud-master/";
        String mavenHome = System.getenv("MAVEN_HOME");
        if(!mavenHome.endsWith("\\")){
            mavenHome += "/";
        }
        mavenHome += "bin/mvn.cmd";
        ProcessBuilder builder = new ProcessBuilder("C:\\Windows\\System32\\cmd.exe",mavenHome,"-f",projectRoot,"package");
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();


            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String msg = null;
            while ((msg = r.readLine()) != null){
                System.out.println(msg);
                msg = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runAntToCompile() throws IOException {

        String projectRoot = HzyCloudApplication.getHomePath().getPath() + "/data/hzycloud-master/";
//        Runtime.getRuntime().exec(new String[]{"cmd","ant"});
        Runtime runtime = Runtime.getRuntime();
        String cmdStr = String.format("cmd /k cd %s && mvn package",projectRoot);
//        Process process = runtime.exec(new String[] {"cmd", "/K", "Start","mvn",projectRoot+"pom.xml","package"});
        runtime.exec(cmdStr);
        System.out.println();
    }
}
