package tech.lpdev.envelop;

import tech.lpdev.utils.FileUtils;
import tech.lpdev.utils.Logger;
import tech.lpdev.utils.MathUtil;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EnvelopManager {

    private static File dir = null;

    public static Envelope getRandomEnvelop() throws IOException {
        if (dir == null) dir = FileUtils.getFileFromResource("envelopes");

//        EnvelopManager envelopManager = new EnvelopManager();
//        File dir = null;
//        try {
//            dir = envelopManager.getFileFromResource("envelopes");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        assert dir != null;

        File[] temp = dir.listFiles();

        List<File> envelopes = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].isDirectory()) {
                envelopes.add(temp[i]);
            }
        }
        File[] randomEnvelope = envelopes.get(MathUtil.random(0, envelopes.size() - 1)).listFiles();

        int[][] inner = getEnvelopFromFile(randomEnvelope[1]);
        int[][] outer = getEnvelopFromFile(randomEnvelope[0]);

        return new Envelope(inner, outer);
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }
    }

    private static int[][] getEnvelopFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // read the first line of the file as an int
        String line;
        if ((line = reader.readLine()) == null) {
            System.out.println("File is empty");
            return new int[0][0];
        }
        int width = Integer.parseInt(line);
        int height = Integer.parseInt(reader.readLine());
//        Logger.log("ENVMANAGER --> Width: " + width + " Height: " + height);
        int[][] envelop = new int[height][width];

        int count = 0;
        while ((line = reader.readLine()) != null) {
            char[] numbers = line.toCharArray();
            for (int i = 0; i < numbers.length; i++) {
                envelop[count][i] = Integer.valueOf(String.valueOf(numbers[i]));
            }
            count++;
        }
        return envelop;
    }
}
