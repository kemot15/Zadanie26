package pl.javastart.entity;

public enum Category {
    HOME ("obowiązki domowe"),
    WORK ("praca"),
    TRAINING ("szkolenie"),
    OTHER ("inne");

    private String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }

    void showName(){
        System.out.println(categoryName);
    }
}
