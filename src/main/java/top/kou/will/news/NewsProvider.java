package top.kou.will.news;

/**
 * Created by ZXL on 2017/5/19.
 */
public class NewsProvider {
    private NewsListener listener;
    private NewsPersister persister;

    public NewsProvider(NewsListener listener, NewsPersister persister) {
        this.listener = listener;
        this.persister = persister;
    }

    public void getAndPersistent() {
        listener.getNews();
        persister.persistNews();
    }
}
