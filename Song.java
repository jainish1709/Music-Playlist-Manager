// src/Song.java
public class Song {
    String title;
    String artist;
    int duration; // in seconds
    Song next; // Pointer for the linked list

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.next = null;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Artist: " + artist + ", Duration: " + duration + " seconds";
    }
}
