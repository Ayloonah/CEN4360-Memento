package com.luanya.memento;

import android.content.*;
import android.content.res.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.util.*;
import org.json.*;

public class ReadSampleData {

    // Read JSON array helper method
    private static JSONArray readJson(Context context, String data) throws IOException, JSONException {
        JSONArray arr;
        StringBuilder sb = new StringBuilder();

        // Grab the assets
        AssetManager am = context.getAssets();

        // Open the JSON, read it
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(am.open("sample_data.json")))) {
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // Create the JSON array
            JSONObject json = new JSONObject(sb.toString());
            arr = json.getJSONArray(data);

            return arr;
        }
    }

    // Grabs the sample data from JSON and returns a list with it
    public static List<Vignette> getVignettesData(Context context) {
        List<Vignette> vignettes = new ArrayList<>();

        // Open the JSON, read it
        try {
            JSONArray arr = readJson(context, "vignettes");

            // Build a vignette from each JSON object
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                String path = obj.isNull("image_path") ? null
                        : obj.getString("image_path");

                vignettes.add(new Vignette(
                        obj.getInt("vignette_id"),
                        obj.getString("title"),
                        obj.getString("event_time"),
                        obj.getString("location_name"),
                        obj.getString("weather"),
                        obj.getInt("temperature_f"),
                        obj.getString("journal_entry"),
                        obj.getBoolean("is_favorite"),
                        obj.getInt("mood"),
                        path
                ));
            }

            return vignettes;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // Load an image
    public static Bitmap loadImage(Context context, String path) {
        // If no path, return
        if (path == null) {
            return null;
        }

        // Open the required image and return it
        AssetManager am = context.getAssets();
        try (InputStream is = am.open(path)) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    // Grab an album's Vignettes
    public static List<Vignette> getAlbumVignettes(Context context, int album) {
        List<Vignette> albumVignettes = new ArrayList<>();
        List<Vignette> vignettes;
        HashSet<Integer> inAlbum = new HashSet<>();

        // Open the JSON, read it
        try {
            JSONArray arr = readJson(context, "album_vignettes");

            // Find the vignettes belonging to this album and add their ID to the set
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                if (obj.getInt("album_id") == album) {
                    inAlbum.add(obj.getInt("vignette_id"));
                }
            }

            // Grab the vignettes
            vignettes = getVignettesData(context);

            // Add the ones in the set to the albumVignettes array
            for (Vignette v : vignettes) {
                if (inAlbum.contains(v.getId())) {
                    albumVignettes.add(v);
                }
            }

            return albumVignettes;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
