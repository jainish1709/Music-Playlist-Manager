// src/MusicPlaylistManager.java
import java.io.IOException;
import java.util.Scanner;

public class MusicPlaylistManager {
    public static void main(String[] args) throws IOException {
        Playlist playlist = new Playlist();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMusic Playlist Manager:");
            System.out.println("1. Add a song");
            System.out.println("2. Remove a song by title");
            System.out.println("3. Remove a song by position");
            System.out.println("4. Display playlist");
            System.out.println("5. Search for a song");
            System.out.println("6. Move song to a new position");
            System.out.println("7. Reverse playlist");
            System.out.println("8. Shuffle playlist");
            System.out.println("9. Enable repeat mode");
            System.out.println("10. Disable repeat mode");
            System.out.println("11. Sort playlist by title");
            System.out.println("12. Sort playlist by artist");
            System.out.println("13. Save playlist to file");
            System.out.println("14. Load playlist from file");
            System.out.println("15. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    playlist.inputSongFromUser(scanner);
                    break;

                case 2:
                    System.out.println("Enter the song title to remove: ");
                    String titleToRemove = scanner.nextLine();
                    playlist.removeSong(titleToRemove);
                    break;

                case 3:
                    System.out.println("Enter the position of the song to remove: ");
                    int positionToRemove = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    playlist.removeSong(positionToRemove);
                    break;

                case 4:
                    playlist.displayPlaylist();
                    break;

                case 5:
                    System.out.println("Enter song title or artist to search: ");
                    String query = scanner.nextLine();
                    playlist.searchSong(query);
                    break;

                case 6:
                    System.out.println("Enter the current position of the song to move: ");
                    int fromPosition = scanner.nextInt();
                    System.out.println("Enter the new position for the song: ");
                    int toPosition = scanner.nextInt();
                    scanner.nextLine();  // consume the newline
                    playlist.reorderSong(fromPosition, toPosition);
                    break;

                case 7:
                    playlist.reversePlaylist();
                    System.out.println("Playlist reversed.");
                    break;

                case 8:
                    playlist.shufflePlaylist();
                    System.out.println("Playlist shuffled.");
                    break;

                case 9:
                    playlist.enableRepeatMode();
                    System.out.println("Repeat mode enabled.");
                    break;

                case 10:
                    playlist.disableRepeatMode();
                    System.out.println("Repeat mode disabled.");
                    break;

                case 11:
                    playlist.sortPlaylistByTitleOrArtist(true);
                    System.out.println("Playlist sorted by title.");
                    break;

                case 12:
                    playlist.sortPlaylistByTitleOrArtist(false);
                    System.out.println("Playlist sorted by artist.");
                    break;

                case 13:
                    System.out.println("Enter the filename to save the playlist: ");
                    String saveFile = scanner.nextLine();
                    playlist.savePlaylist(saveFile);
                    break;

                case 14:
                    System.out.println("Enter the filename to load the playlist: ");
                    String loadFile = scanner.nextLine();
                    playlist.loadPlaylist(loadFile);
                    break;

                case 15:
                    exit = true;
                    System.out.println("Exiting Music Playlist Manager...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
