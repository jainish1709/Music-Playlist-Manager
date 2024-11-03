// src/PlaylistManagerGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PlaylistManagerGUI extends JFrame {
    private Playlist playlist;
    private JTextField titleField, artistField, durationField, positionField, searchField, removePositionField;
    private JTextArea displayArea;
    private JCheckBox repeatModeCheckbox;

    public PlaylistManagerGUI() {
        playlist = new Playlist();

        // Set up the main frame
        setTitle("Music Playlist Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add main panels
        add(createSongInputPanel(), BorderLayout.NORTH);
        add(createDisplayPanel(), BorderLayout.CENTER);
        add(createControlsPanel(), BorderLayout.SOUTH);
    }

    /**
     * Panel for adding and removing songs with improved alignment.
     */
    private JPanel createSongInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Song Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields for adding songs
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleField = new JTextField(15);
        inputPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Artist:"), gbc);

        gbc.gridx = 1;
        artistField = new JTextField(15);
        inputPanel.add(artistField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Duration (sec):"), gbc);

        gbc.gridx = 1;
        durationField = new JTextField(15);
        inputPanel.add(durationField, gbc);

        // Button for adding songs
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Song");
        addButton.addActionListener(new AddSongListener());
        inputPanel.add(addButton, gbc);

        // Remove by Title
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Remove by Title:"), gbc);

        gbc.gridx = 1;
        JTextField removeTitleField = new JTextField(15);
        inputPanel.add(removeTitleField, gbc);

        JButton removeByTitleButton = new JButton("Remove Song by Title");
        removeByTitleButton.addActionListener(e -> {
            String title = removeTitleField.getText().trim();
            if (!title.isEmpty()) {
                playlist.removeSong(title);
                displayArea.append("Removed song by title: " + title + "\n");
                removeTitleField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter the title of the song to remove.");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        inputPanel.add(removeByTitleButton, gbc);

        // Remove by Position
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Remove by Position:"), gbc);

        gbc.gridx = 1;
        removePositionField = new JTextField(15);
        inputPanel.add(removePositionField, gbc);

        JButton removeByPositionButton = new JButton("Remove Song by Position");
        removeByPositionButton.addActionListener(new RemoveSongByPositionListener());
        gbc.gridx = 1;
        gbc.gridy = 7;
        inputPanel.add(removeByPositionButton, gbc);

        // Move from Position
        gbc.gridx = 0;
        gbc.gridy = 8;
        inputPanel.add(new JLabel("Move from Position:"), gbc);

        gbc.gridx = 1;
        JTextField fromPositionField = new JTextField(15);
        inputPanel.add(fromPositionField, gbc);

        // Move to Position
        gbc.gridx = 0;
        gbc.gridy = 9;
        inputPanel.add(new JLabel("Move to Position:"), gbc);

        gbc.gridx = 1;
        positionField = new JTextField(15);
        inputPanel.add(positionField, gbc);

        JButton moveButton = new JButton("Move Song");
        moveButton.addActionListener(e -> {
            try {
                int fromPosition = Integer.parseInt(fromPositionField.getText().trim());
                int toPosition = Integer.parseInt(positionField.getText().trim());
                playlist.reorderSong(fromPosition, toPosition);
                displayArea.append("Moved song from position " + fromPosition + " to " + toPosition + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid positions.");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 10;
        inputPanel.add(moveButton, gbc);

        return inputPanel;
    }

    /**
     * Panel for displaying the playlist and search functionality.
     */
    private JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout(10, 10));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Playlist Display"));
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        // Search functionality
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search Song");

        // Attach the search button to the listener
        searchButton.addActionListener(new SearchSongListener());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        displayPanel.add(searchPanel, BorderLayout.NORTH);

        return displayPanel;
    }

    /**
     * Panel for all playlist management controls.
     */
    private JPanel createControlsPanel() {
        JPanel controlsPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Playlist Controls"));

        JButton displayButton = new JButton("Display Playlist");
        displayButton.addActionListener(new DisplayPlaylistListener());
        controlsPanel.add(displayButton);

        JButton shuffleButton = new JButton("Shuffle Playlist");
        shuffleButton.addActionListener(new ShuffleListener());
        controlsPanel.add(shuffleButton);

        JButton sortTitleButton = new JButton("Sort by Title");
        sortTitleButton.addActionListener(new SortTitleListener());
        controlsPanel.add(sortTitleButton);

        JButton sortArtistButton = new JButton("Sort by Artist");
        sortArtistButton.addActionListener(new SortArtistListener());
        controlsPanel.add(sortArtistButton);


        JButton reverseButton = new JButton("Reverse Playlist");
        reverseButton.addActionListener(new ReversePlaylistListener());
        controlsPanel.add(reverseButton);

        // Repeat mode checkbox
        repeatModeCheckbox = new JCheckBox("Enable Repeat Mode");
        repeatModeCheckbox.addActionListener(new RepeatModeListener());
        controlsPanel.add(repeatModeCheckbox);

        // File operations
        JButton saveButton = new JButton("Save Playlist");
        saveButton.addActionListener(new SavePlaylistListener());
        controlsPanel.add(saveButton);

        JButton loadButton = new JButton("Load Playlist");
        loadButton.addActionListener(new LoadPlaylistListener());
        controlsPanel.add(loadButton);

        return controlsPanel;
    }

    // Listeners for button actions
    private class AddSongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText().trim();
            String artist = artistField.getText().trim();
            int duration;
            try {
                duration = Integer.parseInt(durationField.getText().trim());
                playlist.addSong(title, artist, duration);
                displayArea.append("Song added: " + title + " by " + artist + "\n");
                titleField.setText("");
                artistField.setText("");
                durationField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid duration in seconds.");
            }
        }
    }

    private class RemoveSongByTitleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText().trim();
            if (!title.isEmpty()) {
                playlist.removeSong(title);
                displayArea.append("Removed song by title: " + title + "\n");
                titleField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter the title of the song to remove.");
            }
        }
    }

    private class RemoveSongByPositionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int position = Integer.parseInt(removePositionField.getText().trim());
                playlist.removeSong(position);
                displayArea.append("Removed song at position: " + position + "\n");
                removePositionField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid position.");
            }
        }
    }

    private class DisplayPlaylistListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayArea.setText("");
            playlist.displayPlaylist(displayArea);
        }
    }

    private class SearchSongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                displayArea.setText("");
                playlist.searchSong(query, displayArea);  // Assuming this method displays search results in the displayArea
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a title or artist to search.");
            }
        }
    }

    private class MoveSongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int fromPosition = Integer.parseInt(removePositionField.getText().trim());
                int toPosition = Integer.parseInt(positionField.getText().trim());
                playlist.reorderSong(fromPosition, toPosition);
                displayArea.append("Moved song from position " + fromPosition + " to " + toPosition + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid positions.");
            }
        }
    }

    private class ReversePlaylistListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playlist.reversePlaylist();
            displayArea.append("Playlist reversed.\n");
        }
    }

    private class ShuffleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playlist.shufflePlaylist();
            displayArea.append("Playlist shuffled.\n");
        }
    }

    private class SortTitleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playlist.sortPlaylistByTitleOrArtist(true);
            displayArea.append("Playlist sorted by title.\n");
        }
    }

    private class SortArtistListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playlist.sortPlaylistByTitleOrArtist(false);
            displayArea.append("Playlist sorted by artist.\n");
        }
    }

    private class RepeatModeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (repeatModeCheckbox.isSelected()) {
                playlist.enableRepeatMode();
                displayArea.append("Repeat mode enabled.\n");
            } else {
                playlist.disableRepeatMode();
                displayArea.append("Repeat mode disabled.\n");
            }
        }
    }

    private class SavePlaylistListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String filename = JOptionPane.showInputDialog("Enter filename to save:");
                playlist.savePlaylist(filename);
                displayArea.append("Playlist saved to file: " + filename + "\n");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving playlist.");
            }
        }
    }

    private class LoadPlaylistListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String filename = JOptionPane.showInputDialog("Enter filename to load:");
                playlist.loadPlaylist(filename);
                displayArea.append("Playlist loaded from file: " + filename + "\n");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error loading playlist.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlaylistManagerGUI frame = new PlaylistManagerGUI();
            frame.setVisible(true);
        });
    }
}
