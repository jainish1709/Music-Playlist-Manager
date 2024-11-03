// src/Playlist.java
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextArea;

public class Playlist {
    private Song head;
    private boolean isRepeatMode = false;

    public Playlist() {
        this.head = null;
    }

    // 1. Add a song to the end or a specific position
    public void addSong(String title, String artist, int duration) {
        Song newSong = new Song(title, artist, duration);

        if (head == null) {
            head = newSong;
        } else {
            Song current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newSong;
        }
    }

    // 2. Remove a song by title or position
    public void removeSong(String title) {
        if (head == null) return;

        if (head.title.equals(title)) {
            head = head.next;
            return;
        }

        Song current = head;
        while (current.next != null && !current.next.title.equals(title)) {
            current = current.next;
        }

        if (current.next == null) {
            System.out.println("Song not found.");
        } else {
            current.next = current.next.next;
        }
    }

    public void removeSong(int position) {
        if (head == null || position < 0) return;

        if (position == 0) {
            head = head.next;
            return;
        }

        Song current = head;
        int count = 0;
        while (current != null && count < position - 1) {
            current = current.next;
            count++;
        }

        if (current == null || current.next == null) {
            System.out.println("Position out of bounds.");
        } else {
            current.next = current.next.next;
        }
    }

    // 3. Display the playlist
    public void displayPlaylist(JTextArea displayArea) {
        if (head == null) {
            displayArea.append("The playlist is empty.\n");
            return;
        }

        Song current = head;
        int position = 0;
        int totalDuration = 0;

        do {
            displayArea.append(position + ": " + current + "\n");
            totalDuration += current.duration;
            current = current.next;
            position++;
        } while (current != null && (!isRepeatMode || current != head));

        displayArea.append("Total Playlist Duration: " + totalDuration + " seconds.\n");
    }

    public void displayPlaylist() {
        if (head == null) {
            System.out.println("The playlist is empty.");
            return;
        }

        Song current = head;
        int position = 0;
        int totalDuration = 0;

        do {
            System.out.println(position + ": " + current);
            totalDuration += current.duration;
            current = current.next;
            position++;
        } while (current != null && (!isRepeatMode || current != head));

        System.out.println("Total Playlist Duration: " + totalDuration + " seconds.");
    }   

    // 4. Reorder songs (move a song to a new position)
    public void reorderSong(int fromPosition, int toPosition) {
        if (fromPosition == toPosition || head == null) return;

        Song prevFrom = null, from = head;
        int currentIndex = 0;

        // Locate the song at fromPosition
        while (from != null && currentIndex < fromPosition) {
            prevFrom = from;
            from = from.next;
            currentIndex++;
        }

        // Check if fromPosition is out of bounds
        if (from == null) {
            System.out.println("fromPosition is out of bounds.");
            return;
        }

        // Detach the `from` node
        if (prevFrom != null) {
            prevFrom.next = from.next;
        } else {
            head = from.next; // `from` was the head
        }

        // If moving to the start of the list
        if (toPosition == 0) {
            from.next = head;
            head = from;
            return;
        }

        // Locate the node at toPosition
        Song prevTo = null, to = head;
        currentIndex = 0;
        while (to != null && currentIndex < toPosition) {
            prevTo = to;
            to = to.next;
            currentIndex++;
        }

        // Insert `from` node at `toPosition`
        from.next = to;
        if (prevTo != null) {
            prevTo.next = from;
        } else {
            head = from; // Edge case if `toPosition` was at the start
        }
    }


    // Reverse the entire playlist
    public void reversePlaylist() {
        Song prev = null, current = head, next = null;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        head = prev;
    }

    // 5. Search for a song by title or artist
    public void searchSong(String query, JTextArea displayArea) {
        Song current = head;
        int position = 0;

        while (current != null) {
            if (current.title.equalsIgnoreCase(query) || current.artist.equalsIgnoreCase(query)) {
                System.out.println("Found at position " + position + ": " + current + "\n");
                displayArea.append("Found at position " + position + ": " + current + "\n");
            }
            current = current.next;
            position++;
        }
    }
    public void searchSong(String query) {
        Song current = head;
        int position = 0;

        while (current != null) {
            if (current.title.equalsIgnoreCase(query) || current.artist.equalsIgnoreCase(query)) {
                System.out.println("Found at position " + position + ": " + current);
            }
            current = current.next;
            position++;
        }
    }


    // Shuffle Playlist: Randomly reorder the songs
    public void shufflePlaylist() {
        List<Song> songs = new ArrayList<>();
        Song current = head;

        // Collect all songs in a list
        while (current != null) {
            songs.add(current);
            current = current.next;
        }

        // Shuffle the list
        Collections.shuffle(songs);

        // Rebuild the linked list from the shuffled list
        head = songs.get(0);
        current = head;
        for (int i = 1; i < songs.size(); i++) {
            current.next = songs.get(i);
            current = current.next;
        }
        current.next = null; // Ensure the last song points to null
    }

    // Repeat Mode: Make the playlist circular
    public void enableRepeatMode() {
        if (head == null) return;

        Song current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = head; // Last song points to the first song
        isRepeatMode = true;
    }

    // Disable Repeat Mode: Break the circular link
    public void disableRepeatMode() {
        if (!isRepeatMode || head == null) return;

        Song current = head;
        while (current.next != head) { // Traverse until the last node
            current = current.next;
        }
        current.next = null; // Remove the circular reference
        isRepeatMode = false;
    }

    // Sort Playlist by title or artist
    public void sortPlaylistByTitleOrArtist(boolean sortByTitle) {
        if (head == null || head.next == null) return;

        List<Song> songs = new ArrayList<>();
        Song current = head;

        // Collect all songs in a list
        while (current != null) {
            songs.add(current);
            current = current.next;
        }

        // Sort the list by title or artist
        if (sortByTitle) {
            songs.sort(Comparator.comparing(song -> song.title.toLowerCase()));
        } else {
            songs.sort(Comparator.comparing(song -> song.artist.toLowerCase()));
        }

        // Rebuild the linked list from the sorted list
        head = songs.get(0);
        current = head;
        for (int i = 1; i < songs.size(); i++) {
            current.next = songs.get(i);
            current = current.next;
        }
        current.next = null; // Ensure the last song points to null
    }

    // 6. Save the playlist to a file
    public void savePlaylist(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        Song current = head;
        while (current != null) {
            writer.write(current.title + "," + current.artist + "," + current.duration + "\n");
            current = current.next;
        }

        writer.close();
        System.out.println("Playlist saved to " + filename);
    }

    // 7. Load the playlist from a file
    public void loadPlaylist(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            addSong(data[0], data[1], Integer.parseInt(data[2]));
        }

        reader.close();
        System.out.println("Playlist loaded from " + filename);
    }

    // Method to get user input to add songs interactively
    public void inputSongFromUser(Scanner scanner) {
        System.out.println("Enter song title: ");
        String title = scanner.nextLine();
        System.out.println("Enter artist: ");
        String artist = scanner.nextLine();
        System.out.println("Enter duration (in seconds): ");
        int duration = scanner.nextInt();
        scanner.nextLine();  // consume the newline
        addSong(title, artist, duration);
    }
}
