package opticvyu.Model;

class ItemProject {
    private String title;
    private int imageRes;
    private boolean checked;

    public ItemProject(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}