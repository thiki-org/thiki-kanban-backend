package net.thiki.kanban.domain.entry;

public class Entry {
    
    private Integer id;

    private String title;
    
    /**
     * default constructor for java bean (jackson need it)
     */
    public Entry() {
    }

    public Entry(String title) {
        this.title = title;
    }

    public Entry(Entry other) {
        this.title = other.title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}