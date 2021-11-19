package audio;

import javax.sound.sampled.*;

/** A container for an audio signal backed by a double buffer so as to allow floating point calculation
 * for signal processing and avoid saturation effects. Samples are 16 bit wide in this implementation. */
 public class AudioSignal {
    private double[] sampleBuffer; // floating point representation of audio samples
    private double dBlevel=0; // current signal level




    public void setSampleBuffer(double[] sampleBuffer) {
        this.sampleBuffer = sampleBuffer;
    }
    public double getdBlevel() {
        return dBlevel;
    }




        public void affiche(){
            for (int i=0;i<sampleBuffer.length; i++) {
                System.out.println(sampleBuffer[i]);
            }
        }
        public double[] getSampleBuffer() {
            return sampleBuffer;
        }

         /** Construct an AudioSignal that may contain up to "frameSize" samples.
        * @param frameSize the number of samples in one audio frame */
         public AudioSignal(int frameSize) {
                this.sampleBuffer = new double[frameSize];
         }

         /** Sets the content of this signal from another signal.
            * @param other other.length must not be lower than the length of this signal. */
         public void setFrom(AudioSignal other) {
             this.sampleBuffer = other.getSampleBuffer();
         }

         /** Fills the buffer content from the given input. Byte's are converted on the fly to double's.
            * @return false if at end of stream */
         public boolean recordFrom(TargetDataLine audioInput) {
         byte[] byteBuffer = new byte[sampleBuffer.length*2]; // 16 bit samples
        if (audioInput.read(byteBuffer, 0, byteBuffer.length)==-1) return false;
             for (int i=0; i<sampleBuffer.length; i++)
             sampleBuffer[i] = ((byteBuffer[2*i]<<8)+byteBuffer[2*i+1]) / 32768.0; // big endian
             updatedBLevel();
             return true;
         }

         /** Plays the buffer content to the given output.
            * @return false if at end of stream */
         public boolean playTo(SourceDataLine audioOutput) {
             byte[] byteBuffer = new byte[sampleBuffer.length*2]; // on crée le tableau qui contiendra notre frame sous forme de bits
             for (int i=0; i<sampleBuffer.length; i++) {
                 double q = sampleBuffer[i] * 32768.0 / 256; //le quotient dans la division euclidienne d'un double par 256 correspond aux MSB
                 double r = sampleBuffer[i] * 32768.0 % 256; //le reste dans la division euclidienne d'un double par 256 correspond aux LSB
                 byteBuffer[2 * i] = (byte) q ;
                 byteBuffer[2 * i + 1] = (byte) r;
                 }
             if (audioOutput.write(byteBuffer,0,byteBuffer.length)==-1)return false ;
             return true;
         }




         // your job: add getters and setters ...
          double getSample(int i){
             return this.sampleBuffer[i];
          }

          void setSample(int i, double value){
                sampleBuffer[i]=value;
          }
         double getdBLevel() {
             return this.dBlevel;
         }
          int getFrameSize() {
             return this.sampleBuffer.length;
          }
         // Can be implemented much later: Complex[] computeFFT()


    public static void main(String[] args) {
        AudioFormat audioFormat = new AudioFormat(8000, 16, 1, true, true);
        try {
            TargetDataLine audioimput  = AudioSystem.getTargetDataLine(audioFormat);
            SourceDataLine audiooutput = AudioSystem.getSourceDataLine(audioFormat);
            audioimput.open();
            audioimput.start();
            audiooutput.open();
            audiooutput.start();
            AudioSignal audio = new AudioSignal(20000);
            audio.recordFrom(audioimput);
            audio.affiche();
            audio.playTo(audiooutput);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
        public void updatedBLevel(){
            double rms=0;
             for(double sample : sampleBuffer) {
                rms += sample * sample;
            }

            this.dBlevel= (float)Math.sqrt(rms / sampleBuffer.length);//formule de la puissance d'un signal numérique
        }



         }
