package audio;
import java.io.Console;
import javax.sound.sampled.*;
import java.util.Scanner;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/** A collection of static utilities related to the audio system. */
public class AudioIO {

    /**
     * Displays every audio mixer available on the current system.
     */
    public static void printAudioMixers() {
        System.out.println("Mixers:");
        Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> System.out.println("- name=\"" + e.getName() + "\" description=\"" + e.getDescription() + " by " + e.getVendor() + "\""));
    }

    /**
     * @return a Mixer.Info whose name matches the given string.
     * Example of use: getMixerInfo("Macbook default output")
     */
    public static Mixer.Info getMixerInfo(String mixerName) {
        // see how the use of streams is much more compact than for() loops!
        return Arrays.stream(AudioSystem.getMixerInfo()).filter(e -> e.getName().equalsIgnoreCase(mixerName)).findFirst().get();
    }

    /**
     * Return a line that's appropriate for recording sound from a microphone.
     * Example of use:
     * TargetDataLine line = obtainInputLine("USB Audio Device", 8000);
     *
     * @param mixerName a string that matches one of the available mixers.
     * @see AudioSystem.getMixerInfo() which provides a list of all mixers on your system.
     */
    // if no line match the audio format then throws an exception that we have to catch later
   public static TargetDataLine obtainAudioInput(String mixerName, int sampleRate) throws LineUnavailableException {
       Mixer.Info mixerinfo = AudioIO.getMixerInfo(mixerName);
       Mixer mixer = AudioSystem.getMixer(mixerinfo);
       AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, true);
            return AudioSystem.getTargetDataLine(audioFormat, mixerinfo);
       }


    /**
     * Return a line that's appropriate for playing sound to a loudspeaker.
     */
    // if no line match the audio format then throws an exception that we have to catch later
    public static SourceDataLine obtainAudioOutput(String mixerName, int sampleRate) throws LineUnavailableException {
        Mixer.Info mixerinfo = AudioIO.getMixerInfo(mixerName);
        Mixer mixer = AudioSystem.getMixer(mixerinfo);
        AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, true);
        return AudioSystem.getSourceDataLine(audioFormat, mixerinfo);
    }

    public static void main(String[] args) {
        printAudioMixers();
        Scanner scan = new Scanner(System.in);
        String mixerinput = scan.next();
        String mixeroutput =scan.next();
        int samplerate = scan.nextInt();
        try {
            TargetDataLine tar = obtainAudioInput(mixerinput, samplerate);
            try {
                SourceDataLine src = obtainAudioOutput(mixeroutput, samplerate);
                AudioSignal audio= new AudioSignal(20000);
                tar.open();
                tar.start();
                src.open();
                src.start();
                audio.recordFrom(tar);
                audio.playTo(src);
            }
            catch (Exception e){
                System.out.println("line do not match the audio format");
            }
        }
        catch (Exception e ) {
            System.out.println("line do not match the audio format");
        }




    }
}