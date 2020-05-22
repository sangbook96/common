package vn.sangdv.demmo.footer;

public class Footer {
    public String title;
    public Integer icon;

    public Footer(String title, Integer icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Footer{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }
}
