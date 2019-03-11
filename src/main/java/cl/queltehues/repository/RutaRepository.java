package cl.queltehues.repository;

import cl.ido.ruta.domain.Place;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RutaRepository extends MongoRepository<Place, String> {

    Place findByName(String name);

    List<Place> findByLocation_CoordinatesWithin(Box box);
}
