package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.model.domain.sets.CollectionType;
import it.dogs.fivenine.model.dto.CollectionDTOs.CollectionDTO;
import it.dogs.fivenine.repository.CollectionRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.repository.MovieRepository;
import it.dogs.fivenine.service.CollectionService;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    public CollectionServiceImpl(CollectionRepository collectionRepository, 
                               UserRepository userRepository,
                               MovieRepository movieRepository,
                               ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCollection(CollectionDTO dto, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Collection collection = modelMapper.map(dto, Collection.class);
            collection.setUser(user.get());
            collection.setMovies(new ArrayList<>());
            if (collection.getVisibility() == null) {
                collection.setVisibility(it.dogs.fivenine.model.domain.enums.CollectionVisibility.PRIVATE);
            }
            collectionRepository.save(collection);
        }
    }

    @Override
    public CollectionDTO getCollectionById(Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);
        return collection.map(c -> modelMapper.map(c, CollectionDTO.class)).orElse(null);
    }

    @Override
    public void updateCollection(Long id, CollectionDTO dto) {
        Optional<Collection> existingCollection = collectionRepository.findById(id);
        if (existingCollection.isPresent()) {
            Collection collection = existingCollection.get();
            collection.setName(dto.getName());
            collection.setType(dto.getType());
            collection.setDescription(dto.getDescription());
            collection.setVisibility(dto.getVisibility());
            collectionRepository.save(collection);
        }
    }

    @Override
    public List<Collection> listAllCollections() {
        return collectionRepository.findAll();
    }

    @Override
    public List<Collection> listUserCollections(Long userId) {
        return collectionRepository.findByUserId(userId);
    }

    @Override
    public List<Collection> getUserCollectionsByType(Long userId, CollectionType type) {
        return collectionRepository.findByUserIdAndType(userId, type);
    }

    @Override
    public void deleteCollection(Long id) {
        collectionRepository.deleteById(id);
    }

    @Override
    public void addMovieToCollection(Long collectionId, Long movieId) {
        Optional<Collection> collection = collectionRepository.findById(collectionId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        
        if (collection.isPresent() && movie.isPresent()) {
            Collection c = collection.get();
            if (!c.getMovies().contains(movie.get())) {
                c.getMovies().add(movie.get());
                collectionRepository.save(c);
            }
        }
    }

    @Override
    public void removeMovieFromCollection(Long collectionId, Long movieId) {
        Optional<Collection> collection = collectionRepository.findById(collectionId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        
        if (collection.isPresent() && movie.isPresent()) {
            Collection c = collection.get();
            c.getMovies().remove(movie.get());
            collectionRepository.save(c);
        }
    }

    @Override
    public void addMovieToWatchlist(Long userId, Long movieId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Collection watchlist = getOrCreateUserCollection(user.get(), CollectionType.watchlist);
            if (!watchlist.getMovies().contains(movie.get())) {
                watchlist.getMovies().add(movie.get());
                collectionRepository.save(watchlist);
            }
        }
    }

    @Override
    public void addMovieToFavourites(Long userId, Long movieId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Collection favourites = getOrCreateUserCollection(user.get(), CollectionType.favourites);
            if (!favourites.getMovies().contains(movie.get())) {
                favourites.getMovies().add(movie.get());
                collectionRepository.save(favourites);
            }
        }
    }

    @Override
    public void removeMovieFromWatchlist(Long userId, Long movieId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Optional<Collection> watchlist = collectionRepository.findByUserAndTypeAndName(user.get(), CollectionType.watchlist, "Watchlist");
            if (watchlist.isPresent()) {
                watchlist.get().getMovies().remove(movie.get());
                collectionRepository.save(watchlist.get());
            }
        }
    }

    @Override
    public void removeMovieFromFavourites(Long userId, Long movieId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Optional<Collection> favourites = collectionRepository.findByUserAndTypeAndName(user.get(), CollectionType.favourites, "Favourites");
            if (favourites.isPresent()) {
                favourites.get().getMovies().remove(movie.get());
                collectionRepository.save(favourites.get());
            }
        }
    }

    private Collection getOrCreateUserCollection(User user, CollectionType type) {
        String collectionName = type == CollectionType.watchlist ? "Watchlist" : "Favourites";
        Optional<Collection> existing = collectionRepository.findByUserAndTypeAndName(user, type, collectionName);
        
        if (existing.isPresent()) {
            return existing.get();
        }
        
        Collection newCollection = new Collection();
        newCollection.setUser(user);
        newCollection.setType(type);
        newCollection.setName(collectionName);
        newCollection.setMovies(new ArrayList<>());
        return collectionRepository.save(newCollection);
    }
}