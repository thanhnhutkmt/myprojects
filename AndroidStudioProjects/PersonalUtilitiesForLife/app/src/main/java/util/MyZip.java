package util;

/**
 * Created by Nhut on 10/18/2016.
 */

import android.util.Log;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.winzipaes.AesZipFileEncrypter;
import util.winzipaes.impl.AESEncrypter;
import util.winzipaes.impl.AESEncrypterBC;

public class MyZip {
    private static final List<String> backupFolderList = Arrays.asList(
        AppConstant.THUMUC_QUANLY_CUOCHEN,
        AppConstant.THUMUC_QUANLY_TINNHANDANHBA,
//        AppConstant.THUMUC_BAOGIA,
        AppConstant.THUMUC_BANDO,
        "databases"
    );

    /**This method is used to create a password protected zip file.
     * @param dirName of type String indicating the name of the directory to be zipped
     * @param zipFileName of type String indicating the name of the zip file to be created
     * @param password of type String indicating the password
     */
    public static void zipDirWithPassword(String dirName , String zipFileName , String password) {
        if( zipFileName == null ) {
            File tempFile = new File(dirName);
            zipFileName = tempFile.getAbsoluteFile().getParent() + File.separator + tempFile.getName() + ".zip";
        }
        AesZipFileEncrypter ze = null;
        try {
            zipDir(dirName, zipFileName);
            final AESEncrypter aesEncrypter = new AESEncrypterBC();
            ze = new AesZipFileEncrypter(new File(zipFileName).getParent() + File.separator + "temp_data.zip", aesEncrypter);
            ze.add(new File(zipFileName).getName(), new FileInputStream(zipFileName), "password");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ze != null) {
                try {
                    ze.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        String tempZipFileName = dirName + File.separator + "tempencrypted.zip";
//        try {
//            AesZipFileEncrypter enc = new AesZipFileEncrypter(tempZipFileName, new AESEncrypterBC());
//            enc.add(new File(zipFileName), password);
//            new File(zipFileName).delete();
//            new File(tempZipFileName).renameTo(new File(zipFileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**This method is used to zip a directory
     * @param dirName of type String indicating the path of the directory to be zipped
     * @param zipFileName of type String indicating the file name for the zip file
     */
    public static void zipDir1( String dirName , String zipFileName )
    {
        if( zipFileName == null )
        {
            File tempFile = new File(dirName);
            zipFileName = tempFile.getAbsoluteFile().getParent()+ File.separator+tempFile.getName()+".zip";
        }

        try
        {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
            compressDir(dirName, zos, new File(dirName).getName()+File.separator);
            zos.close();
        }
        catch( NullPointerException npe )
        {
            npe.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch( IOException ie )
        {
            ie.printStackTrace();
        }
    }

    /**This method is used to zip or compress a directory to create a zip file.
     * @param directory of type String indicating the source directory to be zipped
     * @param zos of type {@link ZipOutputStream}
     * @param path of type String indicating the path
     * @throws IOException
     */
    private static void compressDir(String directory, ZipOutputStream zos, String path) throws IOException {
        File zipDir = new File(directory);
        String[] dirList = zipDir.list();
        byte[] readBuffer = new byte[2156];
        int bytesIn = 0;
        for (int i = 0; i < dirList.length; i++)
        {
            if (dirList[i].startsWith(".")) continue;
            File f = new File(zipDir, dirList[i]);
            if (f.isDirectory())
            {
                String filePath = f.getPath();
                compressDir(filePath, zos, path + f.getName() + "/");
                continue;
            }
            FileInputStream fis = new FileInputStream(f);
            try
            {
                ZipEntry anEntry = new ZipEntry(path + f.getName());
                zos.putNextEntry(anEntry);
                bytesIn = fis.read(readBuffer);
                while (bytesIn != -1)
                {
                    zos.write(readBuffer, 0, bytesIn);
                    bytesIn = fis.read(readBuffer);
                }
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }
            finally
            {
                fis.close();
            }
        }
    }

    public static void zipDir(String dirName, String nameZipFile) throws IOException {
        FileOutputStream fW = new FileOutputStream(nameZipFile);
        ZipOutputStream zip = new ZipOutputStream(fW);
        addFolderToZip("", dirName, zip);
        zip.close();
        fW.close();
    }

    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
        File folder = new File(srcFolder);
        Log.i("MyTag", srcFolder);
        if (folder.list().length == 0) {
            addFileToZip(path , srcFolder, zip, true);
        } else {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    if (!backupFolderList.contains(fileName)) continue;
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                } else {
                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }

    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws IOException {
        File folder = new File(srcFile);
        if (flag) {
            zip.putNextEntry(new ZipEntry(path + "/" +folder.getName() + "/"));
        } else {
            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            } else {
                byte[] buf = new byte[1024];
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            }
        }
    }

    public static final int COMPRESS_STORE = Zip4jConstants.COMP_STORE;
    public static final int COMPRESS_FASTEST = Zip4jConstants.DEFLATE_LEVEL_FASTEST;
    public static final int COMPRESS_FAST = Zip4jConstants.DEFLATE_LEVEL_FAST;
    public static final int COMPRESS_NORMAL = Zip4jConstants.DEFLATE_LEVEL_NORMAL;
    public static final int COMPRESS_MAXIMUM = Zip4jConstants.DEFLATE_LEVEL_MAXIMUM;
    public static final int COMPRESS_ULTRA = Zip4jConstants.DEFLATE_LEVEL_ULTRA;
    public static boolean zipDir(File input, String outputPathWithName, int compress_level) {
        boolean result = true;
        try {
            ZipFile zipFile = new ZipFile(outputPathWithName);
            ZipParameters parameters = new ZipParameters();
            if (compress_level != COMPRESS_STORE) parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
            else parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
            parameters.setCompressionLevel(compress_level);
            zipFile.addFolder(input, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static void extract(File zipFile) {
        byte[] buffer = new byte[1024*1024];
        File rootFolder = zipFile.getParentFile();
//        System.out.println("file unzip : "+ rootFolder.getAbsoluteFile());
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(rootFolder + File.separator + fileName);
                System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) fos.write(buffer, 0, len);
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            System.out.println("Done");
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}

