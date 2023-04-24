import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static ArrayList <String> namesGameProgress = new ArrayList<>();
    public static GameProgress gameProgress = null;
    public static final String PATH = "D://javaHomeworksTemp/Games/saveGames/";

    public static void main(String[] args) {
        openZip(PATH + "/saves.zip", PATH + "saves/");
        for (String nameGameProgress : namesGameProgress) {
            openProgress(nameGameProgress);
        }
    }

    public static void openZip(String fileZip, String saveFile) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(fileZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                try (FileOutputStream fout = new FileOutputStream(saveFile)) {
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                    namesGameProgress.add(saveFile + name);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String adressFile) {
        try (FileInputStream fis = new FileInputStream(adressFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }
}