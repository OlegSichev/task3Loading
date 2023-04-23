import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static GameProgress gameProgress = null;

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static void openZip(String pathFile, String pathDirectory) { //TODO создать поле для указания
        // адреса разархивации (pathDirectory) (поле создал (смотреть ниже), но нужно пробовать, будет ли работать
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathDirectory))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                try (FileOutputStream fout = new FileOutputStream(pathDirectory)) { // по изначальной инструкции
                    // идет имя файла, Я так понимаю, что можно класть и адрес директории (надо пробовать)
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String adressFile) { //TODO вроде должно работать, но не проверялось на деле
        try (FileInputStream fis = new FileInputStream(adressFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }
}