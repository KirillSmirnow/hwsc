package hwsc.service;

public interface AuthService {

    boolean areCredentialsCorrect(String username, String password);

    String getPasswordHash(String username);
}
