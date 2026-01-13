package com.example.musicbackend;

import com.example.musicbackend.entity.*;
import com.example.musicbackend.model.Enum.Gender;
import com.example.musicbackend.model.Enum.Role;
import com.example.musicbackend.model.Enum.SongStatus;
import com.example.musicbackend.model.Enum.StatusAccount;
import com.example.musicbackend.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class MusicBackendApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private SongGenreRepository songGenreRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private PlaylistSongRepository playlistSongRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void createFakeData() {
        createGenres();
        createUser();
        createArtists();
        createAlbums();
        createSongs();
        createPlaylists();
        createFavoriteSongsForUsers();

    }

    @Test
    void createGenres() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 10; i++) {
            String name = faker.leagueOfLegends().champion();
            String slug = slugify.slugify(name);
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slug)
                    .description(faker.lorem().sentence(10))
                    .createdAt(LocalDateTime.now())
                    .iconUrl("https://placehold.co/400")
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void createUser() {
        Faker faker = new Faker();
        Random random = new Random();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        for (int i = 0; i < 20; i++) {
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = bCryptPasswordEncoder.encode("123");
            String avatarUrl = "https://placehold.co/400";

            var user = com.example.musicbackend.entity.User.builder()
                    .avatarUrl(avatarUrl)
                    .createdAt(LocalDateTime.now())
                    .dob(faker.date()
                            .birthday()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime())
                    .email(email)
                    .fullName(faker.name().fullName())
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .isPremium(false)
                    .password(password)
                    .phone(faker.phoneNumber().phoneNumber())
                    .role(Role.USER)
                    .statusAccount(StatusAccount.ACTIVE)
                    .updatedAt(LocalDateTime.now())
                    .username(username)
                    .password(password)
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void createArtists() {
        Faker faker = new Faker();
        Random random = new Random();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 15; i++) {
            String name = faker.artist().name();
            String slug = slugify.slugify(name);
            Artist artist = Artist.builder()
                    .name(faker.artist().name())
                    .bio(faker.lorem().paragraph(3))
                    .name(name)
                    .slug(slug)
                    .photoUrl("https://i.pravatar.cc/400?img=" + (i + 20))
                    .totalFollowers(random.nextInt(1000000))
                    .createdAt(LocalDateTime.now())
                    .build();
            artistRepository.save(artist);
        }
    }


    @Test
    void createAlbums() {
        Faker faker = new Faker();
        Random random = new Random();
        List<Artist> artists = artistRepository.findAll();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 20; i++) {
            String name = faker.artist().name();
            String slug = slugify.slugify(name);
            Album album = Album.builder()
                    .title(name)
                    .slug(slug)
                    .description(faker.lorem().paragraph(2))
                    .coverImageUrl("https://picsum.photos/400/400?random=" + i)
                    .releaseDate(LocalDateTime.now().minusDays(random.nextInt(3650)))
                    .totalSongs(random.nextInt(15) + 5)
                    .artist(artists.get(random.nextInt(artists.size())))
                    .createdAt(LocalDateTime.now())
                    .build();
            albumRepository.save(album);
        }
    }

    @Test
    void createSongs() {
        Faker faker = new Faker();
        Random random = new Random();

        List<Artist> artists = artistRepository.findAll();
        List<Album> albums = albumRepository.findAll();
        List<Genre> genres = genreRepository.findAll();

        for (int i = 0; i < 50; i++) {

            Song song = Song.builder()
                    .title(faker.lorem().words(3).stream()
                            .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1))
                            .collect(Collectors.joining(" ")))
                    .duration(random.nextInt(240) + 120)
                    .fileUrl("https://example.com/songs/song_" + i + ".mp3")
                    .coverImageUrl("https://picsum.photos/300/300?random=" + (i + 100))
                    .lyrics(faker.lorem().paragraphs(10)
                            .stream()
                            .collect(Collectors.joining("\n\n")))
                    .playCount(random.nextInt(5_000_000))
                    .releaseDate(LocalDateTime.now().minusDays(random.nextInt(1825)))
                    .artist(artists.get(random.nextInt(artists.size())))
                    .album(random.nextBoolean() ? albums.get(random.nextInt(albums.size())) : null)
                    .createdAt(LocalDateTime.now())
                    .status(SongStatus.ACTIVE)
                    .build();

            songRepository.save(song);

            // 🎯 GÁN GENRE
            int genreCount = random.nextInt(3) + 1; // 1–3 genre
            Set<Genre> selectedGenres = new HashSet<>();

            while (selectedGenres.size() < genreCount) {
                selectedGenres.add(genres.get(random.nextInt(genres.size())));
            }

            for (Genre genre : selectedGenres) {
                SongGenre sg = SongGenre.builder()
                        .song(song)
                        .genre(genre)
                        .genreDate(LocalDateTime.now())
                        .build();

                songGenreRepository.save(sg);
            }
        }
    }


    @Test
    void createPlaylists() {
        Faker faker = new Faker();
        Random random = new Random();
        List<User> users = userRepository.findAll();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 30; i++) {
            String name = faker.lorem().words(2).stream()
                    .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1))
                    .collect(Collectors.joining(" ")) + " Mix";
            String slug = slugify.slugify(name);

            Playlist playlist = Playlist.builder()
                    .name(name)
                    .slug(slug)
                    .description(faker.lorem().sentence(8))
                    .imageUrl("https://picsum.photos/350/350?random=" + (i + 200))
                    .isPublic(random.nextBoolean())
                    .user(users.get(random.nextInt(users.size())))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            playlistRepository.save(playlist);
        }
    }


    /// faker favorite
    @Test
    void createFavoriteSongsForUsers() {
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            User user = userRepository.findById(random.nextInt(10) + 1).orElse(null);
            Song song = songRepository.findById(random.nextInt(50) + 1).orElse(null);

            Favorite favorite = Favorite.builder()
                    .user(user)
                    .song(song)
                    .createdAt(LocalDateTime.now())
                    .build();
            // Lưu favorite vào cơ sở dữ liệu
            favoriteRepository.save(favorite);
        }
    }


    @Test
    void createSongsInPlaylists() {
        Random random = new Random();

        List<Playlist> playlists = playlistRepository.findAll();
        List<Song> songs = songRepository.findAll();

        if (playlists.isEmpty() || songs.isEmpty()) {
            System.out.println("❌ Chưa có playlist hoặc song");
            return;
        }

        for (Playlist playlist : playlists) {

            // Số bài trong playlist (5–15)
            int songCount = random.nextInt(11) + 5;

            // Tránh trùng bài trong cùng playlist
            Collections.shuffle(songs);
            List<Song> selectedSongs = songs.stream()
                    .limit(songCount)
                    .toList();

            int position = 1;

            for (Song song : selectedSongs) {
                PlaylistSong playlistSong = PlaylistSong.builder()
                        .playlist(playlist)
                        .song(song)
                        .position(position++)
                        .addedAt(LocalDateTime.now())
                        .build();

                playlistSongRepository.save(playlistSong);
            }

            // (tuỳ chọn) cập nhật total_songs
            playlist.setTotalSongs(songCount);
            playlistRepository.save(playlist);
        }
    }

}
