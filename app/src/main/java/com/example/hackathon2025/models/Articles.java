public class Articles {
    private String title;
    private String url;
    private int xp;

    public Articles(String title, String url,int xp) {
        this.title = title;
        this.url = url;
        this.xp = xp;
    }

    //Getters and setters
    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public int getXp() {
        return this.xp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void printArticles() {
        System.out.println("Title name: " + title);
        System.out.println("Url: " + url);
        System.out.println("Article xp: " + xp);
    }







}
