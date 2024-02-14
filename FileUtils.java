import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//a class for the music
public class FileUtils
{
    // returns the music clip
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
