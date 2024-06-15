package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.se122.ieltstinder.repository.UserSessionRepository;
import uit.se122.ieltstinder.service.UserSessionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepo;

    @Override
    public boolean checkUserSession(String tokenId) {
        return userSessionRepo.existsByTokenId(tokenId);
    }

    @Override
    public void removeExpiredSession(String tokenId) {
        userSessionRepo.findByTokenId(tokenId).ifPresent(userSessionRepo::delete);
    }

}
