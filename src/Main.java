import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static String text;
    int count = 0;

    public static void main(String[] args) {

        Main main = new Main();
        main.createNewDir("D:\\Games", "src");
        main.createNewDir("D:\\Games", "rec");
        main.createNewDir("D:\\Games", "saveGames");
        main.createNewDir("D:\\Games", "temp");
        main.createNewDir("D:\\Games\\src", "main");
        main.createNewDir("D:\\Games\\src", "test");
        main.createNewFile("D:\\Games\\src\\main", "Main.java");
        main.createNewFile("D:\\Games\\src\\main", "Utils.java");
        main.createNewDir("D:\\Games\\rec", "drawables");
        main.createNewDir("D:\\Games\\rec", "vectors");
        main.createNewDir("D:\\Games\\rec", "icons");
        main.createNewFile("D:\\Games\\temp", "temp.txt");
        text = sb.toString();
        byte[] buffer = text.getBytes();
        try (FileOutputStream out = new FileOutputStream("D:\\Games\\temp\\temp.txt");
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            bos.write(buffer);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        GameProgress progress1 = new GameProgress(100, 1, 10, 800.5);
        GameProgress progress2 = new GameProgress(76, 1, 11, 860.5);
        GameProgress progress3 = new GameProgress(53, 2, 13, 960.5);

        main.saveGame("D:\\Games\\saveGames", progress1);
        main.saveGame("D:\\Games\\saveGames", progress2);
        main.saveGame("D:\\Games\\saveGames", progress3);
        File file = new File("D:\\Games\\saveGames");
        File[] files = file.listFiles();
        main.zipFiles("D:\\Games\\saveGames\\zip.zip", files);


    }

    public void saveGame(String pathName, GameProgress progress) {
        count++;
        String name = "save" + +count + ".dat";
        createNewFile(pathName, name);
        try (FileOutputStream fos = new FileOutputStream(pathName + "\\" + name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void zipFiles(String pathName, File[] files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathName))) {
            for (File file1 : files) {
                try (FileInputStream fis = new FileInputStream(file1)) {
                    ZipEntry entry = new ZipEntry(file1.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (file1.delete()) {
                    System.out.println("файл успешно удален!");
                } else {
                    System.out.println("файл не удален");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void createNewDir(String pathName, String dirName) {
        File newDir = new File(pathName, dirName);
        Date date = new Date();
        if (newDir.mkdir()) {
            sb.append("В директории " + pathName + " успешно создана директория: " + dirName + ". " + date + "\n");
        } else {
            sb.append("Не удалось создать директорию " + dirName + " в директории " + pathName + ". " + date + "\n");
        }
    }

    public void createNewFile(String pathName, String fileName) {
        File newFile = new File(pathName, fileName);
        try {
            Date date = new Date();
            if (newFile.createNewFile()) {
                sb.append("В директории " + pathName + " успешно создан файл: " + fileName + ". " + date + "\n");
            } else {
                sb.append("Не удалось создать файл " + fileName + " в директории " + pathName + ". " + date + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
