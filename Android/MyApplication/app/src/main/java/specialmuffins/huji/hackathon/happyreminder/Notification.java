package specialmuffins.huji.hackathon.happyreminder;


public class Notification {
    private int id ;
    private String title, body;

    public Notification(String title, String body, int id) {
        this.title = title;
        this.body = body;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
