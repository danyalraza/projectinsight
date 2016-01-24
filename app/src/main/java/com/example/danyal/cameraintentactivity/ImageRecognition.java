package com.example.danyal.cameraintentactivity;

// Standard Java imports
import java.io.IOException;
import java.util.List;
import java.io.File;

// Clarifai image recognition services
import com.clarifai.api.RecognitionResult.StatusCode;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.VideoSegment;
import com.clarifai.api.Tag;

public class ImageRecognition
{
    public static void main(String[] args) throws IOException
    {
        if (args.length == 0)
        {
            System.err.println("Please pass 1 or more filenames or URLs as arguments.");
            return;
        }

        // Construct a client. This constructor reads the app ID and secret from environment variables.
        ClarifaiClient client = new ClarifaiClient();

        // The client can accept publicly-accessible URLs, local files, or image/video bytes.
        RecognitionRequest request;


        if (args[0].toLowerCase().startsWith("http:") || args[0].toLowerCase().startsWith("https:"))
            request = new RecognitionRequest(args);	// Pass the URLs to the recognition API.
        else // Pass the files to the recognition API.
        {
            File[] files = new File[args.length];
            for (int i = 0; i < args.length; i++)
                files[i] = new File(args[i]);
            request = new RecognitionRequest(files);
        }

        // Send the request to the server for recognition.
        List<RecognitionResult> results = client.recognize(request);

        // We now have a result. For this sample, we're just going to print out the tags.
        for (int i = 0; i < results.size(); i++)
        {
            RecognitionResult result = results.get(i);

            // Each result contains a status code indicating whether the recognition succeeded or failed.
            System.out.println("\nResult for " + args[i] + ": " + result.getStatusCode());

            if (result.getStatusCode() == StatusCode.OK)
            {
                List<VideoSegment> segments = result.getVideoSegments();
                if (segments != null)
                {
                    // If the input was a video, we get back a list of VideoSegments.
                    for (VideoSegment segment : segments)
                    {
                        System.out.println(String.format("\n At time %.3f:", segment.getTimestampSeconds()));
                        printTags(segment.getTags());
                    }
                }
                else
                    printTags(result.getTags());
            }
            else
                System.out.println("  Failed: " + result.getStatusMessage());
        }
    }

    private static void printTags(List<Tag> tags)
    {
        // Each tag contains a name and probability assigned to it by the recognition engine.
        for (Tag tag : tags)
            System.out.println(String.format("  %s (%.4f)", tag.getName(), tag.getProbability()));
    }
}