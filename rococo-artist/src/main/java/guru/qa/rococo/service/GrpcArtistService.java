package guru.qa.rococo.service;

import guru.qa.grpc.rococo.grpc.*;
import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.grpc.Status.NOT_FOUND;
import static java.util.UUID.fromString;

@GrpcService
public class GrpcArtistService extends RococoArtistServiceGrpc.RococoArtistServiceImplBase {

    private final ArtistRepository artistRepository;

    @Autowired
    public GrpcArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional
    @Override
    public void getArtist(ArtistRequest request, StreamObserver<ArtistResponse> responseObserver) {
        UUID artistId = fromString(request.getId());

        artistRepository.findById(artistId)
                .ifPresentOrElse(
                        artistEntity -> {
                            ArtistResponse artistResponse = ArtistEntity.toGrpcMessage(artistEntity);
                            responseObserver.onNext(artistResponse);
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(
                                NOT_FOUND.withDescription("Artist not found by id: " + artistId)
                                        .asRuntimeException()
                        )
                );
    }

    @Transactional
    @Override
    public void getAllArtist(AllArtistRequest request, StreamObserver<AllArtistResponse> responseObserver) {
        String name = request.getName();
        int page = request.getPage();
        int size = request.getSize();
        PageRequest pageable = PageRequest.of(page, size);

        Page<ArtistEntity> artistPage = artistRepository.findAllByNameContainsIgnoreCase(name, pageable);

        AllArtistResponse.Builder responseBuilder = AllArtistResponse.newBuilder();
        artistPage.forEach(artistEntity -> {
            ArtistResponse artistResponse = ArtistEntity.toGrpcMessage(artistEntity);
            responseBuilder.addArtists(artistResponse);
        });
        responseBuilder.setTotalCount((int) artistPage.getTotalElements());

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Transactional
    @Override
    public void addArtist(AddArtistRequest addArtistRequest, StreamObserver<ArtistResponse> responseObserver) {
        ArtistEntity entity = artistRepository.save(ArtistEntity.fromAddArtistGrpcMessage(addArtistRequest));
        responseObserver.onNext(ArtistEntity.toGrpcMessage(entity));
        responseObserver.onCompleted();
    }

    @Transactional
    @Override
    public void updateArtist(UpdateArtistRequest request, StreamObserver<ArtistResponse> responseObserver) {
        UUID artistId = fromString(request.getId());

        artistRepository.findById(artistId)
                .ifPresentOrElse(
                        artistEntity -> {
                            artistEntity = ArtistEntity.fromUpdateArtistGrpcMessage(request);
                            artistRepository.save(artistEntity);
                            responseObserver.onNext(ArtistEntity.toGrpcMessage(artistEntity));
                            responseObserver.onCompleted();
                        }, () -> responseObserver.onError(
                                NOT_FOUND.withDescription("Artist not found by id: " + artistId)
                                        .asRuntimeException()
                        )
                );
    }


    @Transactional
    @Override
    public void getArtistByIds(ArtistIdsRequest request, StreamObserver<AllArtistByIdsResponse> responseObserver) {
        Set<UUID> artistIds = request.getIdList().stream()
                .map(byteString -> fromString(byteString))
                .collect(Collectors.toSet());

        List<ArtistEntity> artists = artistRepository.findAllByIdIn(artistIds);

        AllArtistByIdsResponse.Builder responseBuilder = AllArtistByIdsResponse.newBuilder();
        artists.forEach(artistEntity -> {
            ArtistResponse artistResponse = ArtistEntity.toGrpcMessage(artistEntity);
            responseBuilder.addArtist(artistResponse);
        });

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

}
