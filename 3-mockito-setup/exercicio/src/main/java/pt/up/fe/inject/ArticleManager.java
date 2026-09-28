package pt.up.fe.inject;

import java.util.Objects;

public class ArticleManager {

  private User user;

  private ArticleDatabase database;

  public ArticleManager(User user, ArticleDatabase database) {
    Objects.requireNonNull(user);
    Objects.requireNonNull(database);
    this.user = user;
    this.database = database;
  }

  public void initialize() {
    this.database.addListener(new ArticleListener());
    this.database.setUser(this.user);
  }
}
