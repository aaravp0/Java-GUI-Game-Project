import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class FileUtils {
    public static Clip openClip(String fileName)
    {
        Clip clip = null;
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e)
        {
            System.out.printf("Unable to open audio file %s\n", fileName);
            e.printStackTrace(); 
            System.exit(1);
        }

        return clip;
    }
}
