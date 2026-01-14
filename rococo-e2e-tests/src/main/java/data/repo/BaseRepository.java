package data.repo;


import config.Config;
import data.jpa.EntityManagers;
import data.tpl.XaTransactionTemplate;
import jakarta.persistence.EntityManager;

public class BaseRepository {
    protected final Config CFG = Config.getInstance();
    protected final EntityManager em;
    protected final XaTransactionTemplate tx;

    protected BaseRepository(String jdbcUrl) {
        this.em = EntityManagers.em(jdbcUrl);
        this.tx = new XaTransactionTemplate(jdbcUrl);
    }
}