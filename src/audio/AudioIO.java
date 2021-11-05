package audio;
import javax.sound.sampled.*;
import java.util.Scanner;
import java.util.Arrays;

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
/*
    public static void main(String[] args) {
        printAudioMixers();
        Scanner scan = new Scanner(System.in);
        String mixerinput = scan.nextLine();
        String mixeroutput = scan.nextLine();
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
*/
    public static void main(String[] args) {
        printAudioMixers();
        Scanner scan = new Scanner(System.in);
        String mixerinput = scan.nextLine();
        String mixeroutput = scan.nextLine();
        int samplerate = scan.nextInt();
        int frameSize = scan.nextInt();
        try {
            AudioProcessor as = startAudioProcessing(mixerinput, mixeroutput, samplerate,  frameSize);

            }
        catch (Exception e ) {
            System.out.println("line do not match the audio format");
        }

    }



    public static AudioProcessor startAudioProcessing(String inputMixer, String outputMixer, int sampleRate, int frameSize) throws LineUnavailableException {
        TargetDataLine inLine = obtainAudioInput(inputMixer, sampleRate);
        SourceDataLine outLine = obtainAudioOutput(outputMixer, sampleRate);
        AudioProcessor as = new AudioProcessor(inLine, outLine, frameSize);
        inLine.open(); inLine.start(); outLine.open(); outLine.start();
        new Thread(as).start();
        return as;
    }
    public static void continuet(AudioProcessor as){
        as.run();
    }
    public static void stopAudioProcessing(AudioProcessor as){
        as.terminateAudioThread();
    }
}