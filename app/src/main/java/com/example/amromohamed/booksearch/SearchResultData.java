package com.example.amromohamed.booksearch;

class SearchResultData {
    private String title;
    private String subTitile;
    private String authors[];
    private String publisher;
    private String publishedDate;
    private String description;
    private String pageCount;
    private String averagerating;
    private String ratingsCount;
    private String thumbnail;

    public SearchResultData(String title, String subTitile, String[] authors, String publisher,
                            String publishedDate, String description, String pageCount,
                            String averagerating, String ratingsCount, String thumbnail) {
        this.title = title;
        this.subTitile = subTitile;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.averagerating = averagerating;
        this.ratingsCount = ratingsCount;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitile() {
        return subTitile;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getAveragerating() {
        return averagerating;
    }

    public String getRatingsCount() {
        return ratingsCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
