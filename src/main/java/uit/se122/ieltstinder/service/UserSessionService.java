package uit.se122.ieltstinder.service;

public interface UserSessionService {
    boolean checkUserSession(String tokenId);
    void removeExpiredSession(String tokenId);
}
